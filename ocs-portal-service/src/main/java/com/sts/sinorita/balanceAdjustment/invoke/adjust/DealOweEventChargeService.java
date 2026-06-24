package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.balanceAdjustment.OweEventChargeManager;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.util.DateUtil;
import com.sts.sinorita.util.StringUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class DealOweEventChargeService {

  private final ConfigItemRepository configItemRepository;

  private final OweEventChargeManager oweEventChargeManager;

  // @DataMapping(mapping = "dateNow")
  private LocalDateTime dateNow;

  // @DataMapping(mapping = "acct")
  private AcctDto acct;

  // @DataMapping(mapping = "acctBookData.acctBookId")
  private Long acctBookId;

  // @DataMapping(mapping = "extendDataMap", parameter = ParameterType.IN_OUT)
  private Map<String, Object> extendDataMap;

  private final DealCreditControlLimit dealCreditControlLimit;

  public Information getInformation() {
    return null;
  }

  public void invoke() {
    try {
      if (this.acct == null) {
        log.debug("acct is null,return");
        return;
      }
      // String str1 = BillingHelper.getStringFromConfig("ACCT.FEE.IS_QRY_OWE_CHARGE",
      // "Y");
      String str1 = configItemRepository.findConfigItem("ACCT", "FEE", "IS_QRY_OWE_CHARGE")
          .map(ConfigItemParamProjection::getParamValue).orElse("Y");
      if ("N".equals(str1))
        return;
      String str2 = DateUtil.date2String(this.dateNow, "yyyyMMddHHmmss");
      // OweEventChargeMgr oweEventChargeMgr = OweEventChargeMgr.getInstance();
      String str3 = oweEventChargeManager.prepareMsg4Acct(this.acct.getAcctId(), this.acctBookId,
          this.acct.getRoutingId(),
          str2);
      // String str4 =
      // BillingHelper.getStringFromConfig("ACCT.FEE.CALL_ZXOS_AT_TRANS_END", "N");
      String str4 = configItemRepository.findConfigItem("ACCT", "FEE", "CALL_ZXOS_AT_TRANS_END")
          .map(ConfigItemParamProjection::getParamValue).orElse(null);
      sendFirstMsg(str3, "Y".equals(str4));
      oweEventChargeManager.prepareAndSend4BalShare(this.acct.getAcctId(), str2);
    } catch (Exception exception) {
      log.warn("Fail to deal owe event charge.", exception);
      // ExceptionHandler.publish("BL-S-ACT-00107", 0);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00107"));
    }
    dealCreditControlLimit.invoke();
  }

  private void sendFirstMsg(String paramString, boolean paramBoolean) {
    if (StringUtil.isEmpty(paramString))
      return;
    if (paramBoolean) {
      if (this.extendDataMap == null)
        this.extendDataMap = new HashMap<>();
      HashMap<Object, Object> hashMap = new HashMap<>();
      hashMap.put("58007-1", paramString);
      this.extendDataMap.put("CALL_ZXOS_AT_LAST_MAP", hashMap);
    } else {
      // ZxosHelper.supplementCharge(paramString);
    }
  }

}
