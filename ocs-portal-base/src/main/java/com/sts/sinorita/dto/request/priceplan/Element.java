package com.sts.sinorita.dto.request.priceplan;

import lombok.Data;

@Data
public class Element {
    private String elementType;
    private String operator;
    private RefValueExtendDto refValueExtendDto;
}
