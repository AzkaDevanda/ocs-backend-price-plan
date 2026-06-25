package com.ocs.portal.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AcctAttrValueDto {

  private Long acctId;
  
  private Long attrId;
  
  private String attrValue;
  
  private LocalDateTime effDate;
  
  private LocalDateTime expDate;
  
  private LocalDateTime updateDate;
  
  private String needUpload;
  
  private Long spId;
  
  private Long routingId;
  
  public String operationType;
  
  private String attrValueMark;

}
