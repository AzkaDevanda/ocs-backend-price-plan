package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import com.sts.sinorita.balanceAdjustment.AcctService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.DealDebitDto;
import com.sts.sinorita.dto.DebitInputDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.helper.BillingHelper;
import com.sts.sinorita.helper.RoutingHelper;
import com.sts.sinorita.mapper.acct.AcctMapper;
import com.sts.sinorita.mapper.balanceAdjustment.SubsMapper;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.AcctItemRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.SubsRepository;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebitManagerEngine {

    private final BillingHelper billingHelper;
    private final ConfigItemRepository configItemRepository;
    private final AcctItemRepository acctItemRepository;
    private final AcctService acctService;
    private final SubsRepository subsRepository;
    private final SubsMapper subsMapper;
    private final AcctMapper acctMapper;

    private final RoutingHelper routingHelper;

    /**
     * Thread-safe state for trace logging
     * Note: In production, consider using ThreadLocal if multi-threading issues occur
     */
    private boolean traceLogFlag = false;

    /**
     * Debit deal level:
     * - Level 1: Account level processing
     * - Other levels: Subscriber level processing
     */
    private int debitDealLevel = -1;

    public int dealDebit(DealDebitDto dealDebitDto) {
        DebitInputDto debitInputDto = dealDebitDto.getDebitInput();

        // Set SP_ID in the DTO if provided
        if (dealDebitDto.getSpId() != null) {
            debitInputDto.setSpId(dealDebitDto.getSpId());
        }

        // Validation
        if (debitInputDto == null || debitInputDto.getAcctId() == null) {
            log.warn("DebitInputDto or AcctId is null, returning without processing");
            return 0;
        }

        if (log.isDebugEnabled()) {
            log.debug("dealDebit debitInputDto: {}", debitInputDto);
            log.debug("dealDebit spId: {}", dealDebitDto.getSpId());
        }

        // Process debit with the provided SP_ID
        processDebit(debitInputDto, dealDebitDto.getSpId());

        return 0;
    }


    @Transactional(rollbackFor = Exception.class)
    public void processDebit(DebitInputDto debitDto, Long spId) {
        // Input validation
        ValidateUtil.notNull(debitDto, "debitInputDto");
        ValidateUtil.notNull(debitDto.getAcctId(), "AcctId");
        ValidateUtil.notNull(debitDto.getGroupType(), "GroupType");

        Long routingId = null;

        try {
            // Step 1: Get routing ID for the account (for database routing)
            routingId = getRoutingIdSafely(debitDto.getAcctId());

            // Step 2: Fetch account data
            AcctDto acctDto = acctService.selectAcctDtoByAcctId(debitDto.getAcctId(), false).map(acctMapper::toAcctDtoFromSelectAcctDtoByAcctId).orElse(null);
            ValidateUtil.notNull(acctDto, "AcctDto");

            // Step 3: Set service provider ID if not already set
            if (spId == null) {
                spId = acctDto.getSpId();
            }
            debitDto.setAcctDto(acctDto);

            log.debug("DealDebit Start. DebitInputDto=[{}]", debitDto);

            // Step 4: Check if trace logging is enabled for this group type
            this.traceLogFlag = isTraceLoggingEnabled(debitDto.getGroupType());

            if (traceLogFlag) {
                log.debug("DealDebit Start. acctId=[{}], groupType=[{}], partyCode=[{}]",
                        debitDto.getAcctId(), debitDto.getGroupType(), debitDto.getPartyCode());
            }

            // Step 5: Determine debit deal level
            this.debitDealLevel = determineDebitDealLevel(debitDto);
            log.debug("DEBIT_DEAL_LEVEL = {}", debitDealLevel);

            // Step 6: Process based on debit deal level
            if (debitDealLevel == 1) {
                // Account level processing (single transaction for all)
                processDebitForAccount(debitDto, spId);
            } else {
                // Subscriber level processing (separate transactions per subscriber)
                processDebitForSubscribers(debitDto, spId);
            }

            log.debug("DealDebit End.");
            if (traceLogFlag) {
                log.debug("DealDebit End. acctId=[{}]", debitDto.getAcctId());
            }

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to process debit for Account Id = [{}]", debitDto.getAcctId(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00294"), e);
        }
    }


    private Long getRoutingIdSafely(Long acctId) {
        try {
            Long routingId = routingHelper.getRoutingIdByAcctId(acctId);
            if (routingId == null) {
                log.warn("Could not find RoutingId for AcctId: {}", acctId);
            }
            return routingId;
        } catch (Exception e) {
            log.warn("Failed to find RoutingId for AcctId: {}", acctId, e);
            return null;
        }
    }

    private boolean isTraceLoggingEnabled(String groupType) {
        String traceLogGroupType = configItemRepository
                .findConfigItem("ACCT.COLLECTION.DUNNING_TRACE_LOG_GROUP_TYPE", null, null)
                .map(ConfigItemParamProjection::getDefaultValue)
                .orElse(null);

        return traceLogGroupType != null &&
                CommonUtil.isInCommaText(traceLogGroupType, groupType);
    }

    /**
     * Determines the debit deal level from DTO or configuration.
     */
    private int determineDebitDealLevel(DebitInputDto debitDto) {
        if (debitDto.getDebitDealLevel() != null) {
            return debitDto.getDebitDealLevel().intValue();
        }

        // Default from configuration or -1
        return configItemRepository
                .findConfigItem("ACCT.COLLECTION.DEBIT_DEAL_LEVEL", null, null)
                .map(ConfigItemParamProjection::getDefaultValue)
                .map(Integer::parseInt)
                .orElse(-1);
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected void processDebitForAccount(DebitInputDto debitDto, Long spId) {
        try {
            log.debug("Processing debit for account level: acctId={}", debitDto.getAcctId());

            // Execute core debit logic for account level (subsId = null)
            DebitProcessingContext context = createDebitContext(debitDto, null, spId);
            executeDebitOneLogic(context);

        } catch (Exception e) {
            log.error("Failed to process debit for Account Id = [{}]", debitDto.getAcctId(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00294"),
                    e
            );
        }
    }

    private DebitProcessingContext createDebitContext(DebitInputDto debitDto, Long subsId, Long spId) {
        DebitProcessingContext context = new DebitProcessingContext();
        context.setDebitDto(debitDto);
        context.setSubsId(subsId);
        context.setSpId(spId);
        context.setAcctId(debitDto.getAcctId());
        context.setGroupType(debitDto.getGroupType());

        // Initialize element cache for storing calculated values
        context.setElementCache(new HashMap<>());
        context.getElementCache().put("ACCT_ID", debitDto.getAcctId());
        context.getElementCache().put("CUST_ID", debitDto.getAcctDto().getCustId());

        if (subsId != null) {
            context.getElementCache().put("SUBS_ID", subsId);
        }

        return context;
    }


    private void executeDebitOneLogic(DebitProcessingContext context) {
        log.debug("Executing DebitOne logic for acctId={}, subsId={}",
                context.getAcctId(), context.getSubsId());

        try {
            // Step 1: Get debit rule groups to process
            List<Long> debitRuleGroupIds = getDebitRuleGroupIds(context);

            if (CommonUtil.isEmpty(debitRuleGroupIds)) {
                log.debug("No debit rule groups found for groupType: {}", context.getGroupType());
                return;
            }

            // Step 2: Process each debit rule group until one succeeds
            boolean actionExecuted = false;
            for (Long ruleGroupId : debitRuleGroupIds) {
                if (processDebitRuleGroup(ruleGroupId, context)) {
                    actionExecuted = true;
                    break; // Stop after first successful rule group
                }
            }

            if (!actionExecuted) {
                log.debug("DebitOne End. No action was executed for acctId={}", context.getAcctId());
            }

        } catch (Exception e) {
            log.error("Error executing debit logic for acctId={}, subsId={}",
                    context.getAcctId(), context.getSubsId(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00294"),
                    e
            );
        }

        log.debug("DebitOne logic completed for acctId={}, subsId={}",
                context.getAcctId(), context.getSubsId());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    protected void processDebitForSubscribers(DebitInputDto debitDto, Long spId) {
        // Get subscriber list for this account
        List<SubsDto> subsDtoList = getSubscriberList(debitDto);

        if (CommonUtil.isEmpty(subsDtoList)) {
            log.debug("No subscribers found for account: {}", debitDto.getAcctId());
            return;
        }

        log.debug("Processing debit for {} subscribers", subsDtoList.size());

        // Process each subscriber in separate transaction
        for (SubsDto subsDto : subsDtoList) {
            processDebitForSingleSubscriber(subsDto, debitDto, spId);
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    protected void processDebitForSingleSubscriber(SubsDto subsDto, DebitInputDto debitDto, Long spId) {
        try {
            log.debug("Processing debit for subscriber: subsId={}", subsDto.getSubsId());

            // Execute core debit logic for this specific subscriber
            DebitProcessingContext context = createDebitContext(debitDto, subsDto.getSubsId(), spId);
            executeDebitOneLogic(context);

        } catch (Exception e) {
            // Log warning but don't fail the entire batch
            // Transaction will be rolled back for this subscriber only
            log.warn("Failed to process debit for subscriber: subsId={}", subsDto.getSubsId(), e);
        }
    }


    /**
     * Gets debit rule group IDs based on group type.
     * In a real implementation, this would query from cache or database.
     */
    private List<Long> getDebitRuleGroupIds(DebitProcessingContext context) {
        // TODO: Implement actual retrieval from BillingCache or database
        //  ArrayList<DebitGroupDto> debitGroupDtoArrayList =
        //      BillingCache.getDebitGroupByType(groupType, spId);

        // For now, return empty list as placeholder
        // In production, this would be populated from configuration
        log.debug("Getting debit rule groups for groupType: {}", context.getGroupType());
        return new ArrayList<>();
    }

    /**
     * Processes a single debit rule group.
     * Returns true if actions were executed, false otherwise.
     */
    private boolean processDebitRuleGroup(Long ruleGroupId, DebitProcessingContext context) {
        log.debug("Processing debit rule group: {}", ruleGroupId);

        // TODO: Implement actual rule group processing
        // This would include:
        // 1. Load debit rules for this group
        // 2. Execute SQL queries
        // 3. Calculate variables
        // 4. Evaluate conditions
        // 5. Execute actions if conditions pass

        return false;
    }


    private List<SubsDto> getSubscriberList(DebitInputDto debitDto) {
        // If subscribers are already provided in the DTO, use them
        if (debitDto.getSubsDtoList() != null && debitDto.getSubsDtoList().length > 0) {
            return Arrays.asList(debitDto.getSubsDtoList());
        }

        // Query all subscribers for this account (excluding terminated)
        Long acctId = debitDto.getAcctId();
        ValidateUtil.notNull(acctId, "acctId");

        try {
            // Fetch active subscribers from database
            List<SubsDto> subsDtoList = subsRepository.selectSubsByAcctId(acctId)
                    .stream()
                    .map(subsMapper::toSubsDtoFromSelectAllSubsByAcctId)
                    .toList();

            // Check if hybrid offers are supported and should be included
            if (isHybridSupported()) {
                List<SubsDto> hybridSubsList = getHybridSubscribers(acctId);
                if (!CommonUtil.isEmpty(hybridSubsList)) {
                    List<SubsDto> combinedList = new ArrayList<>(subsDtoList);
                    combinedList.addAll(hybridSubsList);
                    subsDtoList = combinedList;
                    log.debug("Added {} hybrid subscribers", hybridSubsList.size());
                }
            }

            // Cache the subscriber list in the DTO for future use
            if (!subsDtoList.isEmpty()) {
                debitDto.setSubsDtoList(subsDtoList.toArray(new SubsDto[0]));
            }

            log.debug("Found {} total subscribers for account {}", subsDtoList.size(), acctId);
            return subsDtoList;

        } catch (Exception e) {
            log.error("Failed to get subscriber list for acctId: {}", acctId, e);
            return List.of();
        }
    }

    private List<SubsDto> getHybridSubscribers(Long acctId) {
        // TODO: Implement hybrid subscriber query if needed
        // This would be similar to subsInfoMgr.qryHybridSubsByAcctId(acctId)
        // For now, return empty list as this is an optional feature
        log.debug("Querying hybrid subscribers for acctId: {} (not implemented)", acctId);
        return List.of();
    }


    private boolean isHybridSupported() {
        String isHybridSupported = configItemRepository
                .findConfigItem("ACCT.ACCOUNT_PUBLIC.IS_SUPPORT_HYBRID", null, null)
                .map(ConfigItemParamProjection::getDefaultValue)
                .orElse("N");

        return "Y".equals(isHybridSupported);
    }


    private static class DebitProcessingContext {
        private DebitInputDto debitDto;
        private Long subsId;
        private Long spId;
        private Long acctId;
        private String groupType;
        private Map<String, Object> elementCache;

        // Getters and setters
        public DebitInputDto getDebitDto() { return debitDto; }
        public void setDebitDto(DebitInputDto debitDto) { this.debitDto = debitDto; }

        public Long getSubsId() { return subsId; }
        public void setSubsId(Long subsId) { this.subsId = subsId; }

        public Long getSpId() { return spId; }
        public void setSpId(Long spId) { this.spId = spId; }

        public Long getAcctId() { return acctId; }
        public void setAcctId(Long acctId) { this.acctId = acctId; }

        public String getGroupType() { return groupType; }
        public void setGroupType(String groupType) { this.groupType = groupType; }

        public Map<String, Object> getElementCache() { return elementCache; }
        public void setElementCache(Map<String, Object> elementCache) {
            this.elementCache = elementCache;
        }
    }
}
