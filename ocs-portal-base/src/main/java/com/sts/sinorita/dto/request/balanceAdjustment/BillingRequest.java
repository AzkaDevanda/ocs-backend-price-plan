package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BillingRequest extends LoanRequest {
  private static final long serialVersionUID = 8849592808633503317L;
  public String isSubsLimit;
  public Long reverseAdjustId;
  private Long paymentMethodId = Long.valueOf(1L);
  private String partyType = "E";
  private Long charge = Long.valueOf(0L);
  private Long tax = Long.valueOf(0L);
  private String subsCheckMode = "1";
  private String validateFlag = "N";
  private Boolean isLockRetry = Boolean.valueOf(false);
  private String partyCode;
  private String partnerSn;
  private Long rptFlag;
  private Long contactChannelId;
  private Long reconcileLogId;
  private String reconcileState;
  private String password;
  private Long subsId;
  private String prefix;
  private String accNbr;
  private Long acctId;
  private String acctNbr;
  private Long billId;
  private String billNbr;
  private Long acctResId;
  private String acctResCode;
  private Long balId;
  private Long seconds;
  private Long spId;
  private String certNbr;
  private Boolean isSendAdvice;
  private Map<String, Object> extMap;
  private Long[] selectedAcctItemIdList;
  private Long[] settleBillingCycleIdList;
  private Boolean isQryFee;
  private List<BenefitDto> benefitList;
  private List<OnceFeeDto> onceFeeList;
  private IndividualLifeCycleDto individualLifeCycle;
  private String bonusPaymentFlag;
  private String partnerDetail;
  private Boolean isSendBalTriggerAdvice;
}
