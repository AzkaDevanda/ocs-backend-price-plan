package com.sts.sinorita.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePlanPriorityRequest {
    private Integer pricePlanId;
    private Integer newPriority;
}
