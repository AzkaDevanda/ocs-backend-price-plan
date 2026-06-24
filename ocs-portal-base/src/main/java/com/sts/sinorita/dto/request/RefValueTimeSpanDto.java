package com.sts.sinorita.dto.request;

import ch.qos.logback.core.joran.action.NOPAction;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefValueTimeSpanDto {
    private Long priceId;
    @NotNull
    private Integer ratePlanId;
}
