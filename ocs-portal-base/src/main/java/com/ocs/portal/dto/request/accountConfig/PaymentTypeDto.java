package com.ocs.portal.dto.request.accountConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypeDto {
    private Character paymentType;
    private String paymentTypeName;
    private String comments;
}
