package com.sts.sinorita.dto.response.offer;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "relaType", "relaTypeName" })
public interface GetRelaTypeProjection {

    String getRelaType();

    String getRelaTypeName();
}
