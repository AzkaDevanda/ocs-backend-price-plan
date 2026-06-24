package com.sts.sinorita.dto.request.accountConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto {
    private Integer paymentMethodId;
    private String paymentMethodName;
    private String comments;
    private String paymentType;
    private String paymentTypeName;
    private Integer spId;
    private String paymentMethodCode;
    private Character systemReserved;
    private String groupType;
}
