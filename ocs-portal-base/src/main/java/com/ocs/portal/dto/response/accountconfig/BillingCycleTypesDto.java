package com.ocs.portal.dto.response.accountconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BillingCycleTypesDto {
    private Integer billingCycleTypeId;
    private Integer spId;
    private Character timeUnit;
    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date runDate;
}
