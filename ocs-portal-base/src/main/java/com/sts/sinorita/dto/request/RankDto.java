package com.sts.sinorita.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankDto {
    private Integer timeSpanUpId;
    //    private String rate;
    private Integer calculationUnit;
    private Character adjustMethod;
    private String price;
    private Integer rangeEffVal;
    private Integer rangeExpVal;
}
