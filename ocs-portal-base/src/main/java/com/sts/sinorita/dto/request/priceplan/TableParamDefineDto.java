package com.sts.sinorita.dto.request.priceplan;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TableParamDefineDto {

    public Long tableParamId;

    public String paramName;

    public String paramCode;

    public String comments;

    public String state;

    public LocalDate stateDate;

    public String templateFlag;

    public Long zoneMapId;

    public Long srcParamId;

    public Long spId;

    public Long paramValueType;

    public LocalDate expDate;

    public Long paramVer;

    public LocalDate effDate;

    public String stdCode;

    public String isGlobal;

    public Long quotationConstraintId;
}
