package com.ocs.portal.dto.request.priceplan;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SimpleParamDefineDto {

    public Long simpleParamId;

    public String paramName;

    public String paramCode;

    public String comments;

    public String valueType;

    public String defValue;

    public String dispValue;

    public String dispPrefix;

    public String dispSuffix;

    public Long ratePrecision;

    public String state;

    public LocalDate stateDate;

    public String templateFlag;

    public Long srcParamId;

    public Long spId;

    public LocalDate expDate;

    public LocalDate effDate;

    public Long paramVer;

    public Long quotedPriceType;

    public Long paramValueType;

    public String stdCode;

    public Long measureUnitId;

    public Long quotedUnitId;

    public String isGlobal;

    public String isBenefit;

    public Long editMode;
}
