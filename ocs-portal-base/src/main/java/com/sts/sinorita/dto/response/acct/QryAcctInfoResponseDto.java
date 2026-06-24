package com.sts.sinorita.dto.response.acct;

import lombok.Data;

import java.util.Date;

@Data
public class QryAcctInfoResponseDto {
  private Long acctId;
  private Long custId;
  private String acctNbr;
  private String acctName;
  private Long billingCycleTypeId;
  private Long billFormatId;
  private String postpaid;
  private Long stdAddrId;
  private String billAddress;
  private String paymentType;
  private Long paymentMethodId;
  private Long bankId;
  private String bankAcctNbr;
  private String bankAcctName;
  private Date createdDate;
  private Date updateDate;
  private String state;
  private Date stateDate;
  private String billingCycleTypeName;
  private String defaultFlag;
  private String paymentTypeName;
  private String bankName;
  private String custName;
  private String certNbr;
  private String certTypeName;
  private String address;
  private String isProject; // always 'N'
  private Long routingId;
  private String paymentMethodName;
  private String paymentComments;
  private String bankCardType;
  private String isLock;
  private Long spId;
  private Date allowModStateDate;
  private String custType;
}
