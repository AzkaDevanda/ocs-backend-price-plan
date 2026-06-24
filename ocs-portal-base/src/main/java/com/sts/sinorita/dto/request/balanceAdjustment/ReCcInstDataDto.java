package com.sts.sinorita.dto.request.balanceAdjustment;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ReCcInstDataDto {
  private Long offerId;

  private Long orderItemId;

  private Long subOrderId;

  private String orderNumber;

  private Long eventInstId;

  private Long acctId;

  private String reType;

  private Long reId;

  private Date eventTime;

  private Date stateDate;

  private Long billingCycleId;

  private Long instalmentTypeId;

  private Long promotionPlanId;

  private Long cancelEventInstId;

  private Long discountCharge;

  private String refAttr;

  private Long subsId;

  private Long contactChannelId;

  private Long custId;

  private String partyType;

  private String partyCode;

  private Long spId;

  private Long partId;

  private Long subsEventId;

  private Long pricePlanId;

//  private QryFeeParam qryFeeParam;

  private String calcFeeResponseParam;

  private Map<String, String>[] reponseParamSplitResult;

  //  private FeeAdviceData[] feeAdviceDataList;
//
//  private PresentFeeSpecificationData[] presentFeeSpecDataList;
//
//  private PresentFeeStatData presentFeeStatData;
//
  private PresentFeeInstData[] presentFeeInstDataList;
//
//  private OnceFeeSpecificationData[] onceFeeSpecDataList;
//
//  private OnceFeeStatData onceFeeStatData;

  private OnceFeeInstDataDto[] onceFeeInstDataList;

  //  `private OverdueSpecificationData[] overdueSpecDataList;
//
//  private OverdueStatData overdueStatData;
//
//  private OverdueInstData[] overdueInstDataList;
//
//  private ActionSpecificationData[] actionSpecificationDataList;
//
//  private ActionInstData[] actionInstDataList;
//
//  private AcmSpecificationData[] acmSpecificationDataList;
//
  private AcmInstData[] acmInstDataList;
  //
  private DepositInstData[] depositInstDataList;
//
//  private DepositSpecificationData[] depositSpecDataList;
//
//  private InstalmentData instalmentData;`

  private List<BenefitDto> benefitList;

  private List<OnceFeeDto> onceFeeList;

  private List<String> extProjectedParamList;

//  private List<AsynCallDto> asynCallList;

  private Long feeSubsId;

  private Long feeAcctId;

  private Long presentAcctId;

  private Long presentSubsId;

  private Boolean hasResult;

  private Long pricePlanIdTypeE;

  private String pricePlanIdTypeEList;

  private Long pricePlanIdTypeSD;

  private String pricePlanIdTypeSDList;

  private String comments;
}
