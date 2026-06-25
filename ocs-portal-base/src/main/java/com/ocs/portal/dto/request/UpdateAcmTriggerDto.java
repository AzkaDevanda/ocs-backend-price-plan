package com.ocs.portal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAcmTriggerDto {

    @NotNull
    private LocalDateTime effectiveDate;
    private LocalDateTime expiryDate;
    @NotNull
    private Integer AccumulationType;
    @NotNull
    private Character triggerType;
    private Character destination;

}
