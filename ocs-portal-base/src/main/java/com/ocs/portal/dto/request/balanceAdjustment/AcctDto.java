package com.ocs.portal.dto.request.balanceAdjustment;

import com.ocs.portal.dto.request.accountConfig.BillingCycleTypeDto;
import com.ocs.portal.projection.configitem.ConfigItemParamProjection;
import com.ocs.portal.repository.ConfigItemRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AcctDto {
  private final ConfigItemRepository configItemRepository;
  private Long acctId;
  private String deliverMethod;
  private Long stdAddrId;
  private String billAddress;
  private Long custId;
  private String acctName;
  private String custName;
  private String acctNbr;
  private Long billingCycleTypeId;
  private String paymentType;
  private Long bankId;
  private String bankAcctNbr;
  private String bankAcctName;
  private Date createdDate;
  private Date updateDate;
  private String state;
  private Date stateDate;
  private String postpaid;
  private Long routingId;
  private Long billFormatId;
  private Long spId;
  private String defaultFlag;
  private Long paymentMethodId;
  private String isLock;
  private String partyType;
  private String partyCode;
  private Date bankAcctExpDate;
  private Long bankCardType;
  private String needUpload;
  private String paymentComments;
  private BalDto[] allBalList;
  private BalDto[] oldBalList;
  private BalDto[] newBalList;
  private BalDto[] updateBalList;
  private SubsDto[] allSubsList;
  private BillingCycleTypeDto billingCycleTypeDto;
  private Long currentBillingCycleId;
  private BillDto bill;
  private Boolean recordBill;
  private String billFlag;

  public Boolean isRecordBill () {
    Boolean modBill = Boolean.TRUE;
    Optional<ConfigItemParamProjection> findIsModBill4Prepay = configItemRepository.findConfigItem("ACCT", "ACCOUNT_PUBLIC", "IS_MOD_BILL_4PREPAY");
    String isModBill4Prepay = findIsModBill4Prepay.map(ConfigItemParamProjection::getDefaultValue).orElse("");
    if (!"Y".equals(this.postpaid) && "N".equals(isModBill4Prepay))
      modBill = Boolean.FALSE;
    return modBill;
  }
}
