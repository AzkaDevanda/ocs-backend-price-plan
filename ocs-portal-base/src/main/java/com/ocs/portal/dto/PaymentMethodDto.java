package com.ocs.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto {
    private Integer paymentMethodId;
    private String paymentMethodName;
    private Character paymentType;
    private String comments;
    private String paymentMethodCode;
    private Integer spId;
    private Character systemReserved;
    private Character groupType;
}
