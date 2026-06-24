package com.sts.sinorita.helper;

import com.sts.sinorita.balanceAdjustment.AcctResService;
import com.sts.sinorita.balanceAdjustment.invoke.adjust.BasicInfoInquiryService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.AcctItemTypeDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.mapper.acct.AcctItemTypeMapper;
import com.sts.sinorita.projection.balanceAdjustment.QryDataPrivByDataPrivCodeProjection;
import com.sts.sinorita.projection.balanceAdjustment.QryRoleDataPrivValueUnderStaffJobProjection;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.engine.internal.CacheHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Service
public class BillingHelper {
  static Logger logger = LoggerFactory.getLogger(BillingHelper.class);

  // ============= REPOSITORY ==============
  private static BfmDataPrivRepository bfmDataPrivRepository;
  private static BfmRoleDataPrivRepository BfmRoleDataPrivRepository;
  private static ConfigItemRepository configItemRepository;
  private static AcctResRepository acctResRepository;
  private static AcctItemTypeRepository acctItemTypeRepository;

  // ============= SERVICE ===================
  private static BasicInfoInquiryService basicInfoInquiryService;
  private static AcctResService acctResService;
  private static AcctItemTypeMapper acctItemTypeMapper;

  public static void checkStaffMaxAdjustAmount(String partyCode, Map<Long, Long> chargeMap) {
    if (chargeMap == null || chargeMap.isEmpty())
      return;
    // DynamicDict qryDataPriv = new DynamicDict();
    // qryDataPriv.setServiceName("QryDataPrivByDataPrivCode");
    // qryDataPriv.set("DATA_PRIV_CODE", "ADJUST_AMOUNT_LIMIT");
    // ServiceFlow.callService(qryDataPriv);
    Optional<QryDataPrivByDataPrivCodeProjection> qryDataPriv = bfmDataPrivRepository.qryDataPrivByDataPrivCode(null);
    Long dataPrivId = qryDataPriv.map(QryDataPrivByDataPrivCodeProjection::getDataPrivId)
        .orElse(null);
    if (dataPrivId == null)
      return;
    // DynamicDict qryDataPrivValue = new DynamicDict();
    // qryDataPrivValue.set("DATA_PRIV_ID", dataPrivId);
    // qryDataPrivValue.set("STAFF_JOB_ID", partyCode);
    // qryDataPrivValue.serviceName = "QryRoleDataPrivValueUnderStaffJob";
    // ServiceFlow.callService(qryDataPrivValue);
    // List<DynamicDict> list = qryDataPrivValue.getList("ROLE_PRIV_LIST");
    List<QryRoleDataPrivValueUnderStaffJobProjection> qryDataPrivValue = BfmRoleDataPrivRepository
        .qryRoleDataPrivValueUnderStaffJob(partyCode, dataPrivId);
    if (qryDataPrivValue == null || qryDataPrivValue.isEmpty())
      return;
    QryRoleDataPrivValueUnderStaffJobProjection tmp = qryDataPrivValue.get(0);
    String privValue = tmp.getPrivValue();
    String allAdjustLimitValue = "";
    Map<String, Long> map = new HashMap<>();
    if (!privValue.isEmpty()) {
      String[] privValueStrs = privValue.split("\\|");
      if (privValueStrs.length > 1)
        allAdjustLimitValue = privValueStrs[1];
      if (!privValueStrs[0].isEmpty()) {
        String[] split = privValueStrs[0].split(";");
        for (String s : split) {
          String[] split2 = s.split(":");
          if (split2.length >= 2 && !split2[0].isEmpty() && !split2[1].isEmpty()) {
            String[] split3 = split2[1].split(",");
            for (String string : split3) {
              if (!string.isEmpty())
                map.put(string, Long.valueOf(split2[0]));
            }
          }
        }
      }
    }
    Set<Map.Entry<Long, Long>> entrySet = chargeMap.entrySet();
    Long balance = Long.valueOf(0L);
    for (Map.Entry<Long, Long> entry : entrySet) {
      Long balAdjustAmount = entry.getValue();
      balance = Long.valueOf(balance.longValue() + balAdjustAmount.longValue());
      Long absBalAdjustAmount = Long.valueOf(Math.abs(entry.getValue().longValue()));
      AcctResDto acctRes = acctResService.getAcctResById(entry.getKey());
      String acctResCode = "";
      if (acctRes != null)
        acctResCode = acctRes.getStdCode();
      if (map.containsKey(acctResCode)) {
        Long value = map.get(acctResCode);
        if (absBalAdjustAmount.longValue() > value.longValue()) {
          logger.debug("acctResCode= [{}], limit value= [{}], absBalAdjustAmount= [{}].", acctResCode, value,
              absBalAdjustAmount);
          // ExceptionHandler.publish("BL-S-ACT-00294", 0);
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00294"));
        }
      }
    }
    if (!allAdjustLimitValue.isEmpty() &&
        Math.abs(balance.longValue()) > Long.parseLong(allAdjustLimitValue)) {
      logger.debug("balance = [{}], allAdjustLimitValue=[{}]", balance, allAdjustLimitValue);
      // ExceptionHandler.publish("BL-S-ACT-00294", 0);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00294"));
    }
  }

  public static String getStringFromConfig(String key, String defaultValue) {
    int firstDot = key.indexOf(".");
    if (firstDot == -1 || firstDot == key.length() - 1)
      return defaultValue;
    // String retValue = ConfigItemCache.instance().getString(key);
    // if (StringUtil.isEmpty(retValue))
    // retValue = defaultValue;
    // logger.debug("The value of config item[{}] is [{}]", new Object[] { key,
    // retValue });
    logger.debug("The default value is [{}]", defaultValue);
    return defaultValue;
  }

  public static Boolean isQryBalShareHisByAcctRoutingId() {
    Boolean isQryBalShareHis = Boolean.TRUE;
    Optional<ConfigItemParamProjection> findIsQryBalShareHisByAcctRoutingId = configItemRepository
        .findConfigItem("ACCT", "FEE", "USE_ACCT_ROUTING_ID_4_QRY_BAL_SHARE_HIS");
    // String isQryBalShareHisByAcctRoutingId =
    // ConfigItemCache.instance().getString("ACCT.FEE.USE_ACCT_ROUTING_ID_4_QRY_BAL_SHARE_HIS",
    // null);
    String isQryBalShareHisByAcctRoutingId = findIsQryBalShareHisByAcctRoutingId
        .map(ConfigItemParamProjection::getDefaultValue).orElse(null);
    if ("N".equals(isQryBalShareHisByAcctRoutingId))
      isQryBalShareHis = Boolean.FALSE;
    return isQryBalShareHis;
  }

  public static Boolean getBooleanFromConfig(String key, String defaultValue) {
    int firstDot = key.indexOf(".");
    if (firstDot == -1 || firstDot == key.length() - 1)
      return Boolean.valueOf("Y".equals(defaultValue));
    // String valueStr = ConfigItemCache.instance().getString(key);
    String valueStr = "";
    if (StringUtil.isEmpty(valueStr))
      valueStr = defaultValue;
    logger.debug("The value of config item[{}] is [{}]", new Object[] { key, valueStr });
    logger.debug("The default value is [{}]", new Object[] { defaultValue });
    return Boolean.valueOf("Y".equals(valueStr));
  }

  public static String getKeepUniqueAcctRes() {
    logger.debug("start getKeepUniqueAcctRes");
    String keepUniqueAcctResIds = "";
    AcctResDto[] acctResDtoArr = acctResRepository.selectAcctResByRefillable(List.of('Y', 'M'))
        .toArray(new AcctResDto[0]);
    if (acctResDtoArr != null && acctResDtoArr.length > 0) {
      ArrayList<Long> idList = new ArrayList<>();
      for (int i = 0; i < acctResDtoArr.length; i++)
        idList.add(acctResDtoArr[i].getAcctResId());
      keepUniqueAcctResIds = StringUtil.getStringDivideByCommaFromList(idList);
    }
    logger.debug("end getKeepUniqueAcctRes keepUniqueAcctResIds={}", new Object[] { keepUniqueAcctResIds });
    return keepUniqueAcctResIds;
  }

  public static void setBfmDataPrivRepository(BfmDataPrivRepository bfmDataPrivRepository) {
    BillingHelper.bfmDataPrivRepository = bfmDataPrivRepository;
  }

  public static void setBasicInfoInquiryService(BasicInfoInquiryService basicInfoInquiryService) {
    BillingHelper.basicInfoInquiryService = basicInfoInquiryService;
  }

  public static void setAcctResService(AcctResService acctResService) {
    BillingHelper.acctResService = acctResService;
  }

  public static void setConfigItemRepository(ConfigItemRepository configItemRepository) {
    BillingHelper.configItemRepository = configItemRepository;
  }



    public static Long getOverDueItemId()  {
        Long overdueItemTypeId = null;
        String overdueCode = configItemRepository.findConfigItem("ACCT","ACCT_ITEM_TYPE","ACCT_ITEM_TYPE_CODE_OVERDUE").map(ConfigItemParamProjection::getParamValue).orElse("N");
        AcctItemTypeDto acctItemTypeDto = acctItemTypeRepository.selectAcctItemTypeByCode(overdueCode).map(acctItemTypeMapper::toAcctItemType).orElse(null);
        if (acctItemTypeDto != null)
            overdueItemTypeId = Long.valueOf(acctItemTypeDto.getId());
        return overdueItemTypeId;
    }
}
