package com.sts.sinorita.dto.request.accountConfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingCycleTypeInsertDto {
    private Character timeUnit;

    private String billingCycleTypeName;

    private String comments;

    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date debtDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date runDate;

    private Character operator;

    private Integer spId;

    private String billingCycleTypeCode;

    private Character postPaid;

    private Character custType;

    private Character prodType;
}
