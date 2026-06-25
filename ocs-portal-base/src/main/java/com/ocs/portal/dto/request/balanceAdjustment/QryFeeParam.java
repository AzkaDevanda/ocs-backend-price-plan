package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;

@Data
public class QryFeeParam {
  private Long acctId;

  private Long subsId;

  private Long subsEventId;

  private Boolean isAllowNullId;

  private Long reId;

  private Long pricePlanId;

  private Date eventTime;

  private String refAttr;

  private String subsPricePlanIdList;

  private String acctPricePlanIdList;

  private Long billingCycleTypeId;

  private Long billingCycleId;

  private Long routingId;

  private String indepProdAttr;

  private Long instalmentTypeId;

  private String partyType;

  private String partyCode;

  private Long contactChannelId;

  private Long offerId;

  private Long orgId;

  private Long staffId;

  private Long discountCharge;

  private Long discountRate;

  private HashMap<Object, Object> extendFeeRequestMap;

//  AcctItemData acctItemData;

  private Long spId;

  private String feeConfirmFlag;

  private Long acctItemTypeId;

  private String feeLevel;

  private String confirm;

  private Boolean isOverDeductForOweEventCharge;

  private String chargeFlag;

  private String orderItemId;

  private String subOrderId;

  private boolean isVirtualInstantPayment;

  private Boolean isPricePlanIdTypeSD;

//  private CustParamData custParamData;

  private Long subsPlanId;

  private String comments;
}
