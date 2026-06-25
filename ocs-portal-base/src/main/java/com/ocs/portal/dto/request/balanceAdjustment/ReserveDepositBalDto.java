package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

import java.util.Date;

@Data
public class ReserveDepositBalDto {
  private Long eventInstId;

  private Long depositItemId;

  private Long reserveBal;

  private Long orderItemId;

  private Date createdDate;

  private String state;

  private Date stateDate;

  private Long cOrderItemId;
}
