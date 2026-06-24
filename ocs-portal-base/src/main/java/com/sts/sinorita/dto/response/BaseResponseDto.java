package com.sts.sinorita.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDto {
    private String message;
    private String code;
    private int status;
    private Object data;

    public BaseResponseDto() {
    }

    public BaseResponseDto(String message, String code, Object data, int status) {
        this.message = message;
        this.code = code;
        this.data = data;
        if (code != null) {
            this.status = Integer.parseInt(code);
        } else {
            this.status = status;
        }
    }

    public BaseResponseDto(String message, int status, Object data) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        if (this.code != null) {
            this.status = Integer.parseInt(code);
        }

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
