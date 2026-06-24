package com.sts.sinorita.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AsynCallDto {

  private Long asynCalId;
  
  private Long subsId;
  
  private String avp;
  
  private Long eventId;
  
  private String state;
  
  private LocalDateTime stateDate;
  
  private LocalDateTime createdDate;
  
  private String comments;
  
  private Long priority;

}
