package com.ocs.portal.dto.response.priceplan;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QryReAttrByReAttrTypeResponseDto {
    private Integer reAttrId;
    private String reAttrName;
    private String comments;
}
