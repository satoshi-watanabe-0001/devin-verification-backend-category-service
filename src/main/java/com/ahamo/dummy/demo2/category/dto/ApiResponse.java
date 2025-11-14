package com.ahamo.dummy.demo2.category.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(boolean success, T data, ErrorInfo error, List<WarningInfo> warnings) {

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(true, data, null, null);
  }

  public static <T> ApiResponse<T> successWithWarnings(T data, List<WarningInfo> warnings) {
    return new ApiResponse<>(true, data, null, warnings);
  }

  public static <T> ApiResponse<T> error(ErrorInfo error) {
    return new ApiResponse<>(false, null, error, null);
  }

  public record ErrorInfo(String code, String message, String traceId, List<String> details) {}

  public record WarningInfo(String code, String message) {}
}
