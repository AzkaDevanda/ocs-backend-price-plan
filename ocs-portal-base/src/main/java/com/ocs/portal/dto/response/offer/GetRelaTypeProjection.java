package com.ocs.portal.dto.response.offer;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "relaType", "relaTypeName" })
public interface GetRelaTypeProjection {

    String getRelaType();

    String getRelaTypeName();
}
