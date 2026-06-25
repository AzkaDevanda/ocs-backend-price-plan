package com.ocs.portal.dto.request.priceplan;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RefValueDto {
    public Long refValueId;

    public String refValueType;

    public String valueString;

    public Long simpleParamId;

    public Long tableParamId;

    public Long colId;

    public Long reAttr;

    public String paramValueType;

    public String comments;

    public Long staffId;

    public LocalDate createdDate;

    public String state;

    public LocalDate stateDate;

    public Long spId;

    public Long costValueType;

    public Long ratePrecision;

    public Long priceId;

    public Long ratePlanId;

    public Long offerVerId;

    public Long promoDiscAcctItemTypeId;
}
