package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.balanceAdjustment.ProdService;
import com.sts.sinorita.balanceAdjustment.SubsQueryService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AllServTypeDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.OfferAttrDto;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.dto.response.IndepProdSpecDto;
import com.sts.sinorita.entity.Prod;
import com.sts.sinorita.entity.Subs;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BillingHelper;
import com.sts.sinorita.mapper.balanceAdjustment.AllServTypeMapper;
import com.sts.sinorita.mapper.pricePlan.IndepProdSpecMapper;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.AllServTypeRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.IndepProdSpecRepository;
import com.sts.sinorita.repository.ProdRepository;
import com.sts.sinorita.repository.SubsRepository;
import com.sts.sinorita.repository.SystemParamRepository;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.DateUtil;
import com.sts.sinorita.util.StringUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class ValidateForBalAdjustService {

  private final BalAdjustDataFillService balAdjustDataFillService;

  private final SubsQueryService subsQueryService;

  private final ProdService prodService;

  private final ProdRepository prodRepository;

  private final SubsRepository subsRepository;

  private final AllServTypeRepository allServTypeRepository;

  private final AllServTypeMapper allServTypeMapper;

  private final IndepProdSpecRepository indepProdSpecRepository;

  private final IndepProdSpecMapper indepProdSpecMapper;

  private final SystemParamRepository systemParamRepository;

  private final ConfigItemRepository configItemRepository;

  private Boolean isLimitMaxCharge;

  private Boolean isLimitMaxDays;

  private Long acctResId;

  private Long subsId;

  private Prod prod;

  private BalDto[] newBalList;

  private BalDto[] updateBalList;

  private AcctDto acct;

  private String password;

  private SubsDto subs;

  private String partyType;

  private String partyCode;

  private Long spId;

  private String stdCode;

  private Long charge;

  private Map<String, Object> extMap;

  public Information getInformation() {
    return null;
  }

  public void invoke() {
    if (this.isLimitMaxCharge == null)
      this.isLimitMaxCharge = Boolean.FALSE;
    if (this.isLimitMaxDays == null)
      this.isLimitMaxDays = Boolean.FALSE;
    validatePrepayFlag();
    validateSubsState();
    validateMaxCharge();
    validateMaxExpire();
    validateStaffMaxAdjustAmount();
    validateMaxAdjustCharge();
    balAdjustDataFillService.invoke();
  }

  public void validateMaxAdjustCharge() {
    log.debug("validateMaxAdjustCharge called. subsId = {}, acctResCode = {}.",
        new Object[] { this.subsId, this.stdCode });
    String subsTypeAndAcctResCodeMaxBalAdjustAmounts = BillingHelper
        .getStringFromConfig("ACCT.BALAJUST.MAX_BAL_ADJUST_AMOUNT_OF_SUBS_TYPE_ACCT_RES_CODE", null);
    Map<String, String> subsTypeAndAcctResCodeMap = CommonUtil
        .analyticComplexAttrValue(subsTypeAndAcctResCodeMaxBalAdjustAmounts, "#", ":");
    if (subsTypeAndAcctResCodeMap.isEmpty())
      return;
    List<Long> subsIds = getSubsIdsForCheck();
    if (subsIds.isEmpty()) {
      log.debug("No subscriber in Account {}.", new Object[] { this.acct.getAcctId() });
      return;
    }
    for (Long subsId : subsIds) {
      String subsType = getSubsType(subsId);
      log.debug("subsType = {}.", new Object[] { subsType });
      if (subsTypeAndAcctResCodeMap.containsKey(subsType)) {
        String acctResCodeMaxBalAdjustAmounts = subsTypeAndAcctResCodeMap.get(subsType);
        log.debug("acctResCodeMaxBalAdjustAmounts is [{}]", new Object[] { acctResCodeMaxBalAdjustAmounts });
        Map<String, String> acctResCodeMaxBalAdjustAmountMap = CommonUtil
            .analyticComplexAttrValue(acctResCodeMaxBalAdjustAmounts, "\\|", ",");
        if (!acctResCodeMaxBalAdjustAmountMap.isEmpty() && acctResCodeMaxBalAdjustAmountMap
            .containsKey(this.stdCode)) {
          String maxBalAdjustAmount = acctResCodeMaxBalAdjustAmountMap.get(this.stdCode);
          if (StringUtil.isNotEmpty(maxBalAdjustAmount)) {
            BalDto[] updateBal = BalHelper.getBalFromListByAcctResId(this.updateBalList, this.acctResId);
            if (updateBal != null && updateBal.length > 0 &&
                Math.abs(updateBal[0].getCharge().longValue()) > Long.parseLong(maxBalAdjustAmount)) {
              log.warn("adjust amount ={},maxRechargeAmount ={}",
                  new Object[] { Long.valueOf(-updateBal[0].getCharge().longValue()), maxBalAdjustAmount });
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00028"));
              // ExceptionHandler.publish("BL-S-ACT-00028", 0);
            }
          }
        }
      }
    }
    log.debug("validateMaxAdjustCharge end.");
  }

  public String getSubsType(Long subsId) {
    log.debug("getSubsType called. subsId = {}", new Object[] { subsId });
    Long indepProdSpecId = null;
    Subs subs = subsRepository.selectSubsBySubsId(subsId);
    if (subs != null) {
      Prod prodDto = prodRepository.selectProdBySubsId(subsId);
      indepProdSpecId = prodDto.getOfferId().longValue();
    }
    AssertUtil.isNotNull(indepProdSpecId, "IndepProdSpecId and subsId should not be null");
    if (isHybridProd(indepProdSpecId))
      return "3";
    String isPrepaidStr = null;
    boolean isPrePaid = isPrePaid(indepProdSpecId);
    if (isPrePaid) {
      isPrepaidStr = "Y";
    } else {
      isPrepaidStr = "N";
    }
    if ("N".equals(isPrepaidStr))
      return "2";
    return "1";
  }

  public boolean isPrePaid(Long indepProdSpecId) {
    boolean ret = false;
    IndepProdSpecDto indepProdSpecDto = indepProdSpecRepository.selectIndepProdSpec(indepProdSpecId)
        .map(indepProdSpecMapper::toIndepProdSpecDtoFromSelectIndepProdSpec).get();
    if (StringUtil.isNotEmpty(indepProdSpecDto.getPaidFlag().toString())) {
      if ("N".equals(indepProdSpecDto.getPaidFlag()))
        ret = true;
    } else {
      AllServTypeDto servTypeDto = allServTypeRepository.selectAllServType(indepProdSpecDto.getServType())
          .map(allServTypeMapper::toAllServTypeDto)
          .orElse(null);
      if (StringUtil.isNotEmpty(servTypeDto.getPaidFlag()) &&
          "1".equals(servTypeDto.getPaidFlag()))
        ret = true;
    }
    return ret;
  }

  public boolean isHybridProd(Long prodSpecId) {
    boolean isHybridProd = false;
    OfferAttrDto attrValue = prodService.getOfferAttrByOfferIdAndAttrCode(prodSpecId.longValue(), "EXP_IS_HYBRID");
    if (attrValue != null)
      isHybridProd = "Y".equals(attrValue.getDefaultValue());
    return isHybridProd;
  }

  private List<Long> getSubsIdsForCheck() {
    log.debug("getSubsIdsForCheck called.");
    List<Long> subsIds = new ArrayList<>();
    if (this.subsId == null) {
      SubsDto[] subsArray = this.acct.getAllSubsList();
      if (subsArray != null && subsArray.length > 0)
        for (SubsDto subs : subsArray)
          subsIds.add(subs.getSubsId());
    } else {
      subsIds.add(this.subsId);
    }
    log.debug("getSubsIdsForCheck end. subsIds = {}.", new Object[] { subsIds });
    return subsIds;
  }

  private void validateStaffMaxAdjustAmount() {
    String isLimitAdjustBal = BillingHelper.getStringFromConfig("ACCT.BALAJUST.IS_LIMIT_ADJUST_BAL_OF_JOB_PRIV", "N");
    if ("N".equals(isLimitAdjustBal))
      return;
    if (!"A".equals(this.partyType))
      return;
    Map<Long, Long> map = new HashMap<>();
    for (int i = 0; i < this.updateBalList.length; i++)
      map.put(this.updateBalList[i].getAcctResId(), this.updateBalList[i].getCharge());
    BillingHelper.checkStaffMaxAdjustAmount(this.partyCode, map);
  }

  public void validatePrepayFlag() {
    if (systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID").equals(this.acctResId)
        && "Y".equals(this.acct.getPostpaid())) {
      log.debug("Account number is [{}].", new Object[] { this.acct.getAcctNbr() });
      if (this.extMap != null && "Y".equals(this.extMap.get("CHECK_DEFUALT_BAL_ONLY"))
          && this.charge.longValue() > 0L) {
        checkDefaultBalOnly();
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00046"));
        // ExceptionHandler.publish("BL-S-ACT-00046", 0);
      }
    }
  }

  private void checkDefaultBalOnly() {
    BalDto defaultBal = BalHelper.getDefaultBal(this.newBalList);
    if (defaultBal == null || defaultBal.getRealBal() == null || defaultBal.getRealBal().longValue() > 0L)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00729"));
    // ExceptionHandler.publish("BL-S-ACT-00729", 0);
  }

  public void validateMaxCharge() {
    if (this.isLimitMaxCharge.booleanValue())
      if (systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID").equals(this.acctResId)) {
        Optional<ConfigItemParamProjection> findIsMaxAdjustAmountCheckStr = configItemRepository
            .findConfigItem("ACCT", "BALAJUST", "IS_MAX_BAL_ADJUST_AMOUNT_CHECK");
        String isMaxAdjustAmountCheckStr = findIsMaxAdjustAmountCheckStr.map(ConfigItemParamProjection::getDefaultValue)
            .orElse("N");
        // String isMaxAdjustAmountCheckStr = ConfigItemCache.instance()
        // .getString("ACCT.BALAJUST.IS_MAX_BAL_ADJUST_AMOUNT_CHECK", "N");
        if ("Y".equalsIgnoreCase(isMaxAdjustAmountCheckStr.trim())) {
          Optional<ConfigItemParamProjection> findMaxAdjustAmountStr = configItemRepository
              .findConfigItem("ACCT", "BALAJUST", "MAX_BAL_ADJUST_AMOUNT");
          String maxAdjustAmountStr = "";
          if (this.subsId != null)
            maxAdjustAmountStr = subsQueryService.qryProdAttrValueByAttrCode(this.subsId, "MAX_BAL_ADJUST_AMOUNT");
          if (StringUtil.isNotEmpty(maxAdjustAmountStr)) {
            long maxAdjustAmount = Long.parseLong(maxAdjustAmountStr);
            BalDto updateBasicBal = BalHelper.getDefaultBal(this.updateBalList);
            if (updateBasicBal != null && updateBasicBal.getCharge() != null && updateBasicBal
                .getCharge().longValue() + maxAdjustAmount < 0L) {
              log.debug("adjust amount = [{}], maxRechargeAmount = [{}]",
                  new Object[] { Long.valueOf(-updateBasicBal.getCharge().longValue()),
                      Long.valueOf(maxAdjustAmount) });
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00049"));
              // ExceptionHandler.publish("BL-S-ACT-00049", 0);
            }
          }
          if (StringUtil.isEmpty(maxAdjustAmountStr))
            maxAdjustAmountStr = findMaxAdjustAmountStr.map(ConfigItemParamProjection::getDefaultValue)
                .orElse("N");
          if (StringUtil.isNotEmpty(maxAdjustAmountStr)) {
            long maxAdjustAmount = Long.parseLong(maxAdjustAmountStr);
            BalDto updateBasicBal = BalHelper.getDefaultBal(this.updateBalList);
            if (updateBasicBal != null && updateBasicBal.getCharge() != null &&
                Math.abs(updateBasicBal.getCharge().longValue()) > Math.abs(maxAdjustAmount)) {
              log.warn("adjust amount ={},maxRechargeAmount ={}",
                  new Object[] { Long.valueOf(-updateBasicBal.getCharge().longValue()),
                      Long.valueOf(maxAdjustAmount) });
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00028"));
              // ExceptionHandler.publish("BL-S-ACT-00028", 0);
            }
          }
        }
      }
  }

  public void validateMaxExpire() {
    if (!this.isLimitMaxDays.booleanValue())
      return;
    if (systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID").equals(this.acctResId)) {
      BalDto newBalDto = BalHelper.getBalFromListByBalId(this.newBalList, this.updateBalList[0].getBalId());
      LocalDateTime newBalExpire = newBalDto.getExpDate();
      Optional<ConfigItemParamProjection> findIsMaxBalExpireCheckStr = configItemRepository
          .findConfigItem("ACCT", "COMMON", "IS_MAX_BAL_EXPIRE_CHECK");
      // String isMaxBalExpireCheckStr =
      // ConfigItemCache.instance().getString("ACCT.COMMON.IS_MAX_BAL_EXPIRE_CHECK",
      // "N");
      String isMaxBalExpireCheckStr = findIsMaxBalExpireCheckStr.map(ConfigItemParamProjection::getDefaultValue)
          .orElse("N");
      if ("Y".equalsIgnoreCase(isMaxBalExpireCheckStr.trim())) {
        String dbMaxBalExpire = "";
        if (this.subsId != null)
          dbMaxBalExpire = subsQueryService.qryProdAttrValueByAttrCode(this.subsId, "EXP_MAX_BAL_EXPIRE");
        if (StringUtil.isNotEmpty(dbMaxBalExpire)) {
          long maxBalExpire = Long.parseLong(dbMaxBalExpire);
          LocalDateTime maxDate = DateUtil.offsetSecond(DateUtil.getFullDate(DateUtil.GetDBDateTime()),
              (maxBalExpire + 1L) * 86400L);
          if (newBalExpire != null && !newBalExpire.isBefore(maxDate)) {
            log.debug("expiry date is [{}], maximum expiry date is [{}].",
                new Object[] { DateUtil.dateTime2String(newBalExpire),
                    DateUtil.dateTime2String(maxDate) });
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00052"));
            // ExceptionHandler.publish("BL-S-ACT-00052", 0);
          }
        }
      }
    }
  }

  // TODO: HARUS BUAT PASWORD MANAGE
  private void validateSubsState() {
    // if (this.subs != null) {
    // boolean isPwdError =
    // SubsInfoHelper.checkManagePassword(this.subs.getPrefix(),
    // this.subs.getAccNbr(), this.subs
    // .getSubsId(), this.password, this.partyType, this.partyCode, this.spId,
    // null).booleanValue();
    // if (isPwdError)
    // ExceptionHandler.publish("BL-S-ACT-00068", 0);
    // }
  }

}
