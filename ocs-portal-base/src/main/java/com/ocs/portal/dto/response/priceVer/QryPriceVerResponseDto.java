package com.ocs.portal.dto.response.priceVer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class QryPriceVerResponseDto {
    private Integer priceVerId;
    private Integer ratePlanId;
    private Integer mappingId;
    private LocalDate effDate;
    private LocalDate expDate;
}
