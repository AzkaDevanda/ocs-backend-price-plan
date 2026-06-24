package com.sts.sinorita.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PricePlanTypeDto {
    private String id;
    private String pricePlanTypeName;
    private Character applyLevel;

    public PricePlanTypeDto(String id, String pricePlanTypeName, Character applyLevel) {
        this.id = id;
        this.pricePlanTypeName = pricePlanTypeName;
        this.applyLevel = applyLevel;
    }
    public PricePlanTypeDto(Character id, String pricePlanTypeName) {
        this.id = id.toString();
        this.pricePlanTypeName = pricePlanTypeName;
    }
}


