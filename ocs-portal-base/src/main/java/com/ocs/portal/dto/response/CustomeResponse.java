package com.ocs.portal.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomeResponse {
    private Integer status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Long totalRows;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Integer totalPage;

    public CustomeResponse(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
