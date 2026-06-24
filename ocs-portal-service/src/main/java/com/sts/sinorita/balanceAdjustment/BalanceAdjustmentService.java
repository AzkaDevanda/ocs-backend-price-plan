package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.balanceAdjustment.invoke.add.*;
import com.sts.sinorita.balanceAdjustment.invoke.adjust.BalAdjustInitAndValidateService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.balanceAdjustment.BalAdjustDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingRequest;
import com.sts.sinorita.dto.request.balanceAdjustment.add.AddBalanceAccountRequestDto;
import com.sts.sinorita.dto.request.balanceAdjustment.adjust.AdjustBalanceRequest;
import com.sts.sinorita.dto.request.balanceAdjustment.adjust.BalAdjustRequest;
import com.sts.sinorita.dto.request.common.PagingRequestDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.acct.QryAcctInfoResponseDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.dto.response.balanceAdjustment.BalAcctItemTypeDto;
import com.sts.sinorita.dto.response.balanceAdjustment.QryBalListFilterExpireExceptRefillYResponseDto;
import com.sts.sinorita.dto.response.balanceAdjustment.SelectBalListFilterExpireExceptRefillYResponseDto;
import com.sts.sinorita.entity.Acct;
import com.sts.sinorita.entity.AcctRes;
import com.sts.sinorita.entity.Bal;
import com.sts.sinorita.entity.BalAcctItemType;
import com.sts.sinorita.entity.mdbtt.BalEntity;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.mapper.accountConfig.BalAcctItemTypeMapper;
import com.sts.sinorita.mapper.acct.AcctMapper;
import com.sts.sinorita.mapper.acct.AcctResMapper;
import com.sts.sinorita.mapper.balanceAdjustment.BalMapper;
import com.sts.sinorita.repository.AcctRepository;
import com.sts.sinorita.repository.AcctResRepository;
import com.sts.sinorita.repository.BalAcctItemTypeRepository;
import com.sts.sinorita.repository.BalEntityRepository;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.StringUtil;
import com.sts.sinorita.validation.ValidationHandler;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BalanceAdjustmentService {
  private static final int BATCH_SIZE = 1000;

  private final Logger log = LoggerFactory.getLogger(BalanceAdjustmentService.class);

  private final AcctResRepository acctResRepository;

  private final AcctRepository acctRepository;

  private final BalEntityRepository balRepository;

  private final BalAcctItemTypeRepository balAcctItemTypeRepository;

  private final AcctMapper acctMapper;

  private final AcctResMapper acctResMapper;

  private final BalMapper balMapper;

  private final BalAcctItemTypeMapper balAcctItemTypeMapper;

  private final AddAdjustBalInitAndValidateService addAdjustBalInitAndValidateService;

  private final BalAdjustInitAndValidateService balAdjustInitAndValidateService;

  public ResponseEntity<CustomeResponse> qryAcctInfo(PagingRequestDto pagingRequest, Long acctId, String acctNbr,
      String accNbr, String custName, Long custId, String certNbr, String telNbr, String prefix, Long curAcctId,
      Long spId, String isLock, Long billingCycleTypeId, String routingId, Character postpaid, String custType,
      String custCode) {
    Sort.Direction direction = "DESC".equalsIgnoreCase(pagingRequest.getSortDirection())
        ? Sort.Direction.DESC
        : Sort.Direction.ASC;
    int page = Math.max(pagingRequest.getPage() - 1, 0);
    Sort sort = Sort.by(direction, pagingRequest.getSortBy());
    Pageable pageable = PageRequest.of(page, pagingRequest.getSize(), sort);

    var data = acctRepository.qryAcctInfo(pageable, acctId, acctNbr, accNbr, custName, custId, certNbr, telNbr, prefix,
        curAcctId, spId, isLock, billingCycleTypeId, routingId, postpaid, custType, custCode);
    var result = data.getContent().stream().map(acctMapper::toQryAcctInfoResponseDto);

    return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,
        result, data.getTotalElements(), data.getTotalPages()));
  }

  public ResponseEntity<CustomeResponse> qryBalListFilterExpireExceptRefillY(Long acctId, String acctNbr, Long acctResId, Long routingId) {
    acctId = getValidatedAcctId(acctId, acctNbr);
    QryAcctInfoResponseDto acctInfo = getAcctInfo(acctId);
    if (routingId == null) {
      routingId = acctInfo.getRoutingId();
    }
    List<SelectBalListFilterExpireExceptRefillYResponseDto> balList = getBalanceListWithAllData(acctId, acctResId, routingId);

    if (acctResId != null) {
      validateAcctRes(acctResId);
    }

    QryBalListFilterExpireExceptRefillYResponseDto response = new QryBalListFilterExpireExceptRefillYResponseDto();
    response.setAcctInfo(acctInfo);
    response.setBalList(balList);

    List<QryBalListFilterExpireExceptRefillYResponseDto> responseList = List.of(response);

    log.info("Successfully retrieved balance data for acctId: {}, balances count: {}",
        acctId, balList.size());

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, responseList));
  }

//    public ResponseEntity<CustomeResponse> qryBalListFilterExpireExceptRefillY(Long acctId, String acctNbr, Long acctResId, Long routingId) {
//        try {
//            log.info("Start qryBalListFilterExpireExceptRefillY acctId={}, acctNbr={}", acctId, acctNbr);
//            acctId = getValidatedAcctId(acctId, acctNbr);
//            log.info("Validated acctId={}", acctId);
//
//            QryAcctInfoResponseDto acctInfo = getAcctInfo(acctId);
//            log.info("Got acctInfo={}", acctInfo);
//
//            if (routingId == null) {
//                routingId = acctInfo.getRoutingId();
//            }
//
//            List<SelectBalListFilterExpireExceptRefillYResponseDto> balList = getBalanceListWithAllData(acctId, acctResId, routingId);
//            log.info("Got balList size={}", balList.size());
//
//            if (acctResId != null) {
//                validateAcctRes(acctResId);
//            }
//
//            QryBalListFilterExpireExceptRefillYResponseDto response = new QryBalListFilterExpireExceptRefillYResponseDto();
//            response.setAcctInfo(acctInfo);
//            response.setBalList(balList);
//
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, List.of(response)));
//
//        } catch (Exception e) {
//            log.error("Error qryBalListFilterExpireExceptRefillY", e);
//            throw e;
//        }
//    }

  private List<SelectBalListFilterExpireExceptRefillYResponseDto> getBalanceListWithAllData(Long acctId, Long acctResId, Long routingId) {

    log.debug("Getting balance list with all data for acctId: {}, acctResId: {}",
        acctId, acctResId);

    List<Long> refillableYIds = getRefillableYAcctResIds();

    log.debug("Refillable Y account resource IDs: {}", refillableYIds);

    if (acctResId != null && !refillableYIds.isEmpty() && !refillableYIds.contains(acctResId)) {
      log.warn("Provided acctResId {} is not in refillable Y list: {}", acctResId, refillableYIds);
      // Option 1: Set to null
      // acctResId = null;
      // Option 2: Throw exception
      // throw new ValidationHandler("Invalid acctResId: " + acctResId);
      // Option 3: Just log and continue (current behavior)
    }

    List<BalEntity> balances = balRepository.selectBalListFilterExpireExceptRefillY(
        acctId,
        refillableYIds,
        refillableYIds.isEmpty() ? List.of(-1L) : refillableYIds,
        refillableYIds.isEmpty());

    log.debug("Found {} balance records from database", balances.size());

    List<SelectBalListFilterExpireExceptRefillYResponseDto> balList = balances.stream()
        .map(balMapper::toSelectBalListFilterExpireExceptRefillYResponseDto)
        .toList();

    if (balList.isEmpty()) {
      log.info("No balance records found");
      return balList;
    }

    List<Long> balIdList = balList.stream()
        .map(SelectBalListFilterExpireExceptRefillYResponseDto::getBalId)
        .toList();

    qryBalAcctItemType(balList, balIdList);

      balList.forEach(bal -> {
          if (bal.getAcctResId() != null) {
              AcctResDto acctResDto = acctResRepository.selectAcctResProjection(bal.getAcctResId())
                      .map(acctResMapper::toAcctResDto)
                      .orElse(null);
              bal.setAcctRes(acctResDto);
          }
      });

    log.info("Returned {} balance records with item types", balList.size());

    return balList;
  }

  private List<Long> getRefillableYAcctResIds() {
    log.debug("Querying refillable Y account resources");

    List<AcctRes> acctResList = acctResRepository.findAll();

    if (acctResList.isEmpty()) {
      log.debug("No refillable Y account resources found");
      return Collections.emptyList();
    }

    List<Long> acctResIds = acctResList.stream()
        .map(AcctRes::getAcctResId)
        .toList();

    log.debug("Found {} refillable Y account resources with IDs: {}",
        acctResIds.size(), acctResIds);

    return acctResIds;
  }

  private void qryBalAcctItemType(List<SelectBalListFilterExpireExceptRefillYResponseDto> balDtoList, List<Long> balIdList) {

    if (balIdList == null || balIdList.isEmpty()) {
      return;
    }

    log.debug("Processing balance account item types for {} balance records", balIdList.size());

    for (int i = 0; i < balIdList.size(); i += BATCH_SIZE) {
      int endIndex = Math.min(i + BATCH_SIZE, balIdList.size());
      List<Long> batchIds = balIdList.subList(i, endIndex);

      log.debug("Processing batch {}-{} of {} balance IDs", i + 1, endIndex, balIdList.size());

      setBalAcctItemType(balDtoList, batchIds);
    }

    log.debug("Completed processing balance account item types");
  }

  private void setBalAcctItemType(
      List<SelectBalListFilterExpireExceptRefillYResponseDto> balDtoList,
      List<Long> balIdList) {

    if (balIdList == null || balIdList.isEmpty()) {
      return;
    }

    try {
      List<BalAcctItemType> entities = balAcctItemTypeRepository.findByBalIdInOrderByBalId(balIdList);

      if (entities.isEmpty()) {
        log.debug("No balance account item types found");
        return;
      }

      Map<Long, List<BalAcctItemTypeDto>> groupedByBalId = entities.stream()
          .map(balAcctItemTypeMapper::toDto)
          .collect(Collectors.groupingBy(BalAcctItemTypeDto::getBalId));

      balDtoList.forEach(balDto -> {
        List<BalAcctItemTypeDto> itemTypes = groupedByBalId.get(balDto.getBalId());
        if (itemTypes != null && !itemTypes.isEmpty()) {
          balDto.setBalAcctItemType(itemTypes);
        }
      });

      log.debug("Set balance account item types for {} balance records",
          groupedByBalId.size());

    } catch (Exception e) {
      log.error("Error setting balance account item types", e);
      throw new ValidationHandler("Failed to set balance account item types: " + e.getMessage());
    }
  }

  private Long getValidatedAcctId(Long acctId, String acctNbr) {
    if (acctId == null) {
      if (acctNbr == null || acctNbr.trim().isEmpty()) {
        throw new ValidationHandler("Either acctId or acctNbr must be provided");
      }

      acctId = acctRepository.selectAcctIdByAcctNbr(acctNbr);

      if (acctId == null) {
        throw new ValidationHandler("Account not found for acctNbr: " + acctNbr);
      }

      log.debug("Retrieved acctId: {} for acctNbr: {}", acctId, acctNbr);
    }

    return acctId;
  }

  private QryAcctInfoResponseDto getAcctInfo(Long acctId) {
    return acctRepository.qryAcctInfo(null, acctId, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null)
        .stream()
        .map(acctMapper::toQryAcctInfoResponseDto)
        .findFirst()
        .orElseThrow(() -> new ValidationHandler("Account not found for acctId: " + acctId));
  }

  private void validateAcctRes(Long acctResId) {
    if (!acctResRepository.existsById(acctResId)) {
      log.warn("AcctRes not found for acctResId: {}", acctResId);
    }
  }

  // public ResponseEntity<CustomeResponse>
  // getBalListFilterExpireExceptRefillY(Long acctId, Long acctResId) {
  // List<BalEntity> balEntities =
  // balRepository.selectBalListFilterExpireExceptRefillY(acctId, acctResId);
  // return ResponseEntity.status(HttpStatus.OK)
  // .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,
  // balEntities));
  // }

  // public int adjustBalApplication(AdjustBalanceRequest request) {
  //   BalAdjustRequest balAdjustRequest = new BalAdjustRequest();

  //   // --- BalAdjustRequest own fields ---
  //   balAdjustRequest
  //       .setAdjustReasonId(request.getAdjustReason() != null ? request.getAdjustReason().longValue() : null);
  //   balAdjustRequest.setEffDate(request.getEffDate());
  //   balAdjustRequest.setExpDate(request.getExpDate());
  //   balAdjustRequest.setComments(request.getComments());
  //   balAdjustRequest
  //       .setExpDateExtendFlag(request.getExpiryExtention() != null ? request.getExpiryExtention().longValue() : null);

  //   // --- BillingRequest fields (parent of BalAdjustRequest) ---
  //   balAdjustRequest.setBalId(request.getBalId());
  //   balAdjustRequest.setAcctId(request.getAcctId());
  //   balAdjustRequest.setAcctResId(request.getAcctResId());
  //   balAdjustRequest.setAcctNbr(request.getAcctNbr());
  //   balAdjustRequest.setAccNbr(request.getAccNbr());
  //   balAdjustRequest.setSubsId(request.getSubsId());
  //   balAdjustRequest.setContactChannelId(request.getContactChannelId());
  //   balAdjustRequest.setPartyType(request.getPartyType());
  //   balAdjustRequest.setPartyCode(request.getPartyCode());
  //   balAdjustRequest.setSpId(request.getSpId() != null ? request.getSpId().longValue() : null);
  //   if (request.getCharge() != null) {
  //     balAdjustRequest.setCharge(Long.valueOf(request.getCharge()));
  //   }

  //   BalAdjustDataBus balAdjustDataBus = new BalAdjustDataBus();
  //   balAdjustDataBus.setRequest(balAdjustRequest);
  //   if (request.getAcctLockFlag() != null)
  //     balAdjustDataBus.setAcctLockFlag(request.getAcctLockFlag());
  //   try {
  //     // ApplicationContext.invokeApplication("ad.bl.balance.BalAdjustApp",
  //     // balAdjustDataBus);
  //     adBlBalanceBalAdjustApp(request);
  //   } catch (Exception exception) {
  //     if (CommonUtil.eitherNotEmpty(balAdjustRequest.getAccNbr(), balAdjustRequest.getAcctNbr()))
  //       log.warn("accNbr is [{}], acctNbr [{}]",
  //           new Object[] { balAdjustRequest.getAccNbr(), balAdjustRequest.getAcctNbr() });
  //     // ExceptionHandler.publish("BL-S-ACT-00349", 0, exception);
  //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00349"));
  //   }
  //   BoHelper.dtoToBO(balAdjustDataBus, paramDynamicDict);
  //   paramDynamicDict.set("BAL_ADJUST_DATA_BUS", balAdjustDataBus);
  //   paramDynamicDict.set("ACCT_BOOK_ID", balAdjustDataBus.getAcctBookData().getAcctBookId());
  //   String str = ConfigItemCache.instance().getString("ACCT.LOAN.IS_BAL_ADJUST_TRIG_CLEAR_LOAN", "N");
  //   if (StringUtil.isEmpty(str) || "N".equals(str))
  //     return 0;
  //   DynamicDict dynamicDict = new DynamicDict();
  //   Acct acct = balAdjustDataBus.getAcct();
  //   Bal[] arrayOfBal1 = acct.getOldBalList();
  //   Bal[] arrayOfBal2 = new Bal[arrayOfBal1.length];
  //   for (byte b = 0; b < arrayOfBal1.length; b++)
  //     arrayOfBal2[b] = BalHelper.copyBalInfo(arrayOfBal1[b]);
  //   acct.setUpdateBalList(null);
  //   acct.setAllBalList(null);
  //   dynamicDict.set("OPERATE_TIME", balAdjustDataBus.getDateNow());
  //   dynamicDict.set("ACCT_LOCK_FLAG", balAdjustDataBus.getAcctLockFlag());
  //   dynamicDict.set("ACCT", acct);
  //   dynamicDict.set("SUBS", balAdjustDataBus.getSubs());
  //   dynamicDict.set("SP_ID", paramDynamicDict.getLong("SP_ID"));
  //   dynamicDict.set("RELA_ACCT_BOOK_ID", balAdjustDataBus.getAcctBookData().getAcctBookId());
  //   dynamicDict.set("RECEIVE_AMOUNT", balAdjustDataBus.getBalAdjustAmount());
  //   dynamicDict.set("CONTACT_CHANNEL_ID", balAdjustDataBus.getAcctBookData().getContactChannelId());
  //   if (balAdjustDataBus.getAcctResDto() != null)
  //     dynamicDict.set("ACCT_RES_ID", balAdjustDataBus.getAcctResDto().getAcctResId());
  //   dynamicDict.serviceName = "ClearLoanApplication";
  //   ServiceFlow.callService(dynamicDict, true);
  //   acct.setOldBalList(arrayOfBal2);
  //   Map map = balAdjustDataBus.getExtendDataMap();
  //   if (CommonUtil.isNotEmpty(map) && map.containsKey("CALL_ZXOS_AT_LAST_MAP")) {
  //     Map map1 = (Map) map.get("CALL_ZXOS_AT_LAST_MAP");
  //     ZxosHelper.callAtLast(map1);
  //   }
  //   return 0;
  // }

  private void adBlBalanceBalAdjustApp(AdjustBalanceRequest request) {
    balAdjustInitAndValidateService.invoke(request);
  }

  public ResponseEntity<CustomeResponse>  addAdjustBalApplication(AddBalanceAccountRequestDto request) {
    log.info("addAdjustBalInitAndValidateService invoked.");
    addAdjustBalInitAndValidateService.invoke(request);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
  }

    public ResponseEntity<CustomeResponse>  adjustBalApplication(AdjustBalanceRequest request) {
        try {

            log.info("requestDto={}", request);

            balAdjustInitAndValidateService.invoke(request);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CustomeResponse(
                            200,
                            HttpStatusConstant.SUCCESS_MESSAGE,
                            null));

        } catch (Exception e) {

            log.error("adjustBalApplication failed", e);

            throw e;
        }
    }
}
