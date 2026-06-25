package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.Data;

@Data
public class SubsAcmProdData {
  Long subsId;

  Long resourceId;

  Long prodId;

  Long value;
}
