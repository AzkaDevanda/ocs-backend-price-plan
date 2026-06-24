package com.sts.sinorita.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private Integer status;
  private String message;
  private T data;

  public ApiResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public ApiResponse(int status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(200, "Success", data);
  }

  public static <T> ApiResponse<T> success(Integer status) {
    return new ApiResponse<>(status, "Success", null);
  }

  public static <T> ApiResponse<T> error(int status, String message) {
    return new ApiResponse<>(status, message);
  }

  public static <T> ApiResponse<T> error(int status, String message, T data) {
    return new ApiResponse<>(status, message, data);
  }
}
