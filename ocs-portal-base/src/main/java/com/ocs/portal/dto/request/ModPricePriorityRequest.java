package com.ocs.portal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModPricePriorityRequest {

    @NotNull
    private Long priceId;
    @NotNull
    private Integer oldPriority;
    @NotNull
    private Integer newPriority;
    @NotNull
    private Integer priceVerId;

}
