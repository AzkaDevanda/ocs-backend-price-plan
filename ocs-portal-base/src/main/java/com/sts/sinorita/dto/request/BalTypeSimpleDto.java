package com.sts.sinorita.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalTypeSimpleDto {

    private String stdCode;
    private Integer unitTypeId;
    private String comments;
    private String balTypeName;
    private Integer balType;
    private String refillable;
    private String unitTypeName;
    private String acctResName;
    private Integer acctResId;
    private Character isCurrency;
    private Character paymentForce;
    private Character isFreeUnit;
}
