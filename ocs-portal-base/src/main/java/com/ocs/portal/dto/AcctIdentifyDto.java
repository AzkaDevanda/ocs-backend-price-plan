package com.ocs.portal.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AcctIdentifyDto {
    private Long acctIdentifyId;
    private String identifyType;
    private String identifyValue;
    private Long acctId;
    private Date createdDate;
    private Date updateDate;
    private Long spId;
    private String state;
    private Long routingId;
}
