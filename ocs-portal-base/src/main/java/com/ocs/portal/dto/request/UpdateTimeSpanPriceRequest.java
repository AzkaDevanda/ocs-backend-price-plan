package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTimeSpanPriceRequest {

  private Long timeSpanId;
  private String calculationMethod;
  private String price;
  private Long calculateUnit;

}