package com.sts.sinorita.helper;

import com.sts.sinorita.balanceAdjustment.AcctResService;
import com.sts.sinorita.balanceAdjustment.AcctService;
import com.sts.sinorita.balanceAdjustment.SubsQueryService;
import com.sts.sinorita.balanceAdjustment.SubsService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.OnceFeeInstDataDto;
import com.sts.sinorita.dto.request.balanceAdjustment.ReCcInstData;
import com.sts.sinorita.dto.response.accountconfig.BillingCycleDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.entity.Acct;
import com.sts.sinorita.entity.Subs;
import com.sts.sinorita.mapper.accountConfig.BillingCycleMapper;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.BillingCycleRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class BalHelperExt {

  private static Logger logger = LoggerFactory.getLogger(BalHelperExt.class);

  @Autowired
  private ConfigItemRepository configItemRepository;
  @Autowired
  private static SubsQueryService subsQueryService;
  @Autowired
  private static AcctService acctService;
  @Autowired
  private static SubsService subsService;
  @Autowired
  private AcctResService acctResService;
  @Autowired
  private static BillingCycleRepository billingCycleRepository;
  @Autowired
  private static BillingCycleMapper billingCycleMapper;

  private BalHelperExt() {
  }

  public static void validateMaxChgValue(Long charge, Long maxChgValue) {
    if (isAnyNull(charge, maxChgValue))
      return;

    if (Math.abs(charge) > maxChgValue) {
      throwBadRequest("BL-S-ACT-00296");
    }
  }

  public static void validateMaxValue(Long maxValue, Long realBal) {
    logger.debug("Validating max value: maxValue={}, realBal={}", maxValue, realBal);
    if (isAnyNull(maxValue, realBal))
      return;

    if (realBal + maxValue < 0L) {
      throwBadRequest("BL-S-ACT-00297");
    }
  }

  public static void validateMaxExpDate(
      Long maxExpireDate,
      LocalDateTime expDate,
      LocalDateTime now) {

    if (isAnyNull(maxExpireDate, expDate, now)) {
      return;
    }

    // === setara dengan DateUtil.getFullDate(now)
    LocalDate baseDate = now.toLocalDate();

    // === setara dengan DateUtil.offsetDay(...)
    LocalDate maxExpLocalDate = baseDate.plusDays(maxExpireDate);

    // === samakan ke LocalDateTime (awal hari)
    LocalDateTime maxExpDate = maxExpLocalDate.atStartOfDay();

    logger.debug("Comparing expiration: expDate={}, maxExpDate={}", expDate, maxExpDate);

    // === setara dengan DateUtil.differDateInDays(...)
    long diffDays = ChronoUnit.DAYS.between(maxExpDate, expDate);

    if (diffDays > 0) {
      throwBadRequest("BL-S-ACT-00298");
    }
  }

  private static boolean isAnyNull(Object... values) {
    for (Object value : values) {
      if (value == null)
        return true;
    }
    return false;
  }

  private static void throwBadRequest(String messageKey) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage(messageKey));
  }

  public void validateProductLevelBasicBalMaxLimit(Long paramLong, BalDto[] paramArrayOfBal) {
    logger.debug("call validateProductLevelBasicBalMaxLimit and subsId is {}", paramLong);
    Optional<ConfigItemParamProjection> findStr = configItemRepository.findConfigItem("ACCT", "ACCOUNT_PUBLIC",
        "IS_CHECK_PRODUCT_LEVEL_BASIC_BAL_MAX_LIMIT");
    // String str =
    // ConfigItemCache.instance().getString("ACCT.ACCOUNT_PUBLIC.IS_CHECK_PRODUCT_LEVEL_BASIC_BAL_MAX_LIMIT",
    // "N");
    String str = findStr
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse(null);
    Acct acct = null;
    Subs[] arrayOfSubs = null;
    if ("Y".equals(str)) {
      BalDto bal = BalHelper.getDefaultBal(paramArrayOfBal);
      if (bal == null) {
        logger.debug("main bal is null");
        return;
      }
      if (paramLong == null)
        paramLong = qrySubsbyAcctId(bal, acct, arrayOfSubs);
      if (paramLong == null) {
        logger.debug("The subs is null, no need to check.");
        return;
      }
      // DynamicDict dynamicDict = new DynamicDict();<l
      // dynamicDict.setServiceName("QryProdAttrValueByAttrCode");
      // dynamicDict.set("PROD_ID", paramLong);
      // dynamicDict.set("ATTR_CODE", "EXP_MAX_MAIN_BAL");
      // ServiceFlow.callService(dynamicDict);
      // TODO: INI DIA NGAMBIL EXP_MAX_MAIN_BAL DARI MANA
      String str1 = subsQueryService.qryProdAttrValueByAttrCode(paramLong, null);
      logger.debug("EXP_MAX_MAIN_BAL:[{}]", str1);
      if (!str1.isEmpty()) {
        long l = Long.parseLong(str1);
        if (bal.getRealBal().longValue() + l < 0L)
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00297"));
      }
    }
  }

  public static Long qrySubsbyAcctId(BalDto paramBal, Acct paramAcct, Subs[] paramArrayOfSubs) {
    logger.debug("qrySubsbyAcctId ....");
    Long long_1 = null;
    Long long_2 = null;
    // long_1 = RoutingHelper.getRoutingIdByAcctId(paramBal.getAcctId());
    if (long_1 == null) {
      logger.debug("Can not find routingId of acct, acctId:[{}], will use default routingId.", paramBal.getAcctId());
      // long_1 = Long.valueOf(RoutingHelper.getCurrentRoutingId().toString());
    }
    if (long_1 != null)
      // DbRoutingContext.beginDbRoutingEvn(new Object[]{long_1});
      paramAcct = acctService.qryAcctByAcctId(paramBal.getAcctId(), false);
    if (paramAcct != null)
      paramArrayOfSubs = subsService.selectAllSubsListByAcctId(paramAcct.getAcctId());
    if (paramArrayOfSubs == null || paramArrayOfSubs.length == 0) {
      logger.debug("subs is not exist...");
      return null;
    }
    long_2 = paramArrayOfSubs[0].getSubsId();
    return long_2;
  }

  public void checkBalMaxValue(BalDto[] updateBalList, BalDto[] newBalList, String acctBookType) {
    Optional<ConfigItemParamProjection> findIsValidateLimitCharge = configItemRepository.findConfigItem("ACCT",
        "ACCT_BILLING", "IS_VALIDATE_LIMT_CHARGE_BEFORE_DATA_STORE");
    String isValidateLimitCharge = findIsValidateLimitCharge
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse(null);
    // String isValidateLimitCharge =
    // ConfigItemCache.instance().getString("ACCT.ACCT_BILLING.IS_VALIDATE_LIMT_CHARGE_BEFORE_DATA_STORE",
    // "N");
    if ("N".equals(isValidateLimitCharge))
      return;
    LocalDateTime now = LocalDateTime.now();
    List<BalDto> operatorList = BalHelper.mergeAddList(updateBalList);
    if (operatorList.isEmpty())
      return;
    for (BalDto updateBal : operatorList) {
      Long tempAcctResId = updateBal.getAcctResId();
      Long charge = updateBal.getCharge();
      AcctResDto acctResDto = acctResService.getAcctResById(tempAcctResId);
      if (acctResDto == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00074"));
      validateMaxChgValue(charge, acctResDto.getMaxChgValue());
      if ("H".equals(acctBookType))
        validateMaxChgValue(charge, acctResDto.getMaxAdjustValue());
      BalDto newBal = BalHelper.getBalFromListByBalId(newBalList, updateBal.getBalId());
      if (newBal == null)
        continue;
      validateMaxValue(acctResDto.getMaxValue(), newBal.getRealBal());
      validateMaxExpDate(acctResDto.getMaxExpDate(), updateBal.getExpDate(), now);
    }
  }

  public void checkBalDeductLimit(List<? extends ReCcInstData> reCcInstDataList) {
    logger.debug("checkBalDeductLimit start...");
    // if (CommonUtil.isEmpty(reCcInstDataList))
    // return;
    Optional<ConfigItemParamProjection> findIsCheckBalDeductLimit = configItemRepository.findConfigItem("ACCT",
        "ACCT_BILLING", "IS_CHACK_BAL_DEDUCT_LIMIT");
    // String isCheckBalDeductLimit =
    // ConfigItemCache.instance().getString("ACCT.ACCT_BILLING.IS_CHACK_BAL_DEDUCT_LIMIT",
    // "N");
    String isCheckBalDeductLimit = findIsCheckBalDeductLimit
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse("Y");
    if (!"Y".equals(isCheckBalDeductLimit)) {
      logger.debug("don't need checkBalDeductLimit...");
      return;
    }
    for (ReCcInstData reCcInstBLData : reCcInstDataList) {
      OnceFeeInstDataDto[] onceFeeInstDataList = reCcInstBLData.getOnceFeeInstDataList();
      if (CommonUtil.isEmpty((Object[]) onceFeeInstDataList))
        continue;
      for (OnceFeeInstDataDto ignored : onceFeeInstDataList) {
        String dateStr = null;
        // IBalHisDAO balHisDao = (IBalHisDAO)DAOFactory.createModuleDAO("BalHis",
        // "bizcommon.rating.common",
        // JdbcUtil4BC.getDbCache());
        BillingCycleDto billingCycleDto = billingCycleRepository
            .selectCurBillingCycleIDByAcctId(reCcInstBLData.getAcctId())
            .map(billingCycleMapper::selectCurBillingCycleIDByAcctIdToDto)
            .orElse(null);
        // List<DynamicDict> balDtoList = qryBalListByAcctId(onceFeeInstInfo);
        dateStr = DateUtil.date2String(LocalDateTime.now(), "yyyyMMdd");
        // if (CommonUtil.isEmpty(balDtoList)) {
        // logger.debug("don't need checkBalDeductLimit,balDtoList is null...");
        // return;
        // }
        logger.debug("dateStr is {}", dateStr);
        // checkBalHis(balDtoList, billingCycleDto, balHisDao, onceFeeInstInfo,
        // dateStr);
      }
    }
  }

  // private static void checkBalHis(List<DynamicDict> balDtoList, BillingCycleDto
  // billingCycleDto, IBalHisDAO balHisDao, OnceFeeInstData onceFeeInstInfo,
  // String dateStr) {
  // Long acmDailyValue = Long.valueOf(0L);
  // Long acmCeilValue = Long.valueOf(0L);
  // BalHisDto[] dto = null;
  // for (int i = 0; i < balDtoList.size(); i++) {
  // DynamicDict balDict = balDtoList.get(i);
  // Long balId = balDict.getLong("BAL_ID");
  // Long billingCycleIds = billingCycleDto.getBillingCycleId();
  // Date expDate = balDict.getDate("EXP_DATE");
  // if (expDate == null || DateUtil.compare(expDate, DateUtil.GetDBDateTime()) ==
  // 2) {
  // dto = balHisDao.selectBalHisByBalId(balId, dateStr);
  // checkBalHisDaily(dto, acmDailyValue, balDict, onceFeeInstInfo);
  // dto = balHisDao.selectBalHisBybillingCycleId(balId, billingCycleIds);
  // checkBalHisCeil(dto, acmCeilValue, balDict, onceFeeInstInfo);
  // }
  // }
  // }

  // public static List<DynamicDict> qryBalListByAcctId(OnceFeeInstDataDto
  // onceFeeInstInfo) {
  // logger.debug("OnceFeeInstData {}", new Object[] { onceFeeInstInfo });
  // Long routingId = null;
  // List<DynamicDict> balList = null;
  // try {
  // routingId = RoutingHelper.getRoutingIdByAcctId(onceFeeInstInfo.getAcctId());
  // if (routingId == null) {
  // logger.debug("Can not find routingId of acct, acctId:[{}], will use default
  // routingId.", new Object[] { onceFeeInstInfo.getAcctId() });
  // routingId = Long.valueOf(RoutingHelper.getCurrentRoutingId().toString());
  // }
  // if (routingId != null)
  // DbRoutingContext.beginDbRoutingEvn(new Object[] { routingId });
  // DynamicDict balDict = new DynamicDict();
  // balDict.set("ACCT_ID", onceFeeInstInfo.getAcctId());
  // balDict.serviceName = "QryBalListFilterAllExpire";
  // ServiceFlow.callService(balDict);
  // balList = balDict.getList("BAL_LIST");
  // } staticly {
  // if (routingId != null)
  // DbRoutingContext.endCurrentDbRoutingEnv();
  // }
  // return balList;
  // }
}
