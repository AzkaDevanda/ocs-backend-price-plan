package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateThresholdDto {

  private String triggerBy;
  private Integer interval;
  private Integer ratio;
  private Long value;
  private Integer feature;
  private Integer onOffAttr;

}
