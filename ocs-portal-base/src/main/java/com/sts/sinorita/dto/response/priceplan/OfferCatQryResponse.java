package com.sts.sinorita.dto.response.priceplan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class OfferCatQryResponse {

    private Integer offerCatGId;
    private String offerCatGName;
    private Integer seq;
    private String offerCatgCode;
    private Date effDate;
    private Date expDate;
    private String comments;
    private Character offerCatgType;
    private Character offerCatClass;;
    private Character policiFlag;
}
