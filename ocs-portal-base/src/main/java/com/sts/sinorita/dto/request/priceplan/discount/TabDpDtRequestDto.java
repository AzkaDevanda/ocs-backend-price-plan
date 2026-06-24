package com.sts.sinorita.dto.request.priceplan.discount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TabDpDtRequestDto {
    @JsonIgnore
    private Integer calcVal;
    @JsonIgnore
    private Integer fVal;
    @JsonIgnore
    private Integer cVal;
    @JsonIgnore
    private Integer offerVerId;
    private Integer seqNo;
    private String sVal;
    private String eVal;
    private Character disctCalcMethod;
    private Integer dpId;
    private RefValueDiscount discountValue;


}
