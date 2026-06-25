package com.ocs.portal.dto.request.accountConfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingCycleTypeDto {
    private Integer billingCycleTypeId;
    private Character timeUnit;
    private String billingCycleTypeName;
    private String comments;
    private Integer quantity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date debtDate;
    private String timeUnitName;
    private Character operator;
    private String billingCycleTypeCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date runDate;
    private Character prodType;
    private Character postpaid;
    private Character custType;
    private String custTypeName;
}
