package com.ocs.portal.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

  private static final String TRACE_ID = "traceId";
  private static final int MAX_PAYLOAD_LENGTH = 5000;
  private static final String MASK_VALUE = "*****";

  private static final Set<String> SENSITIVE_FIELDS = Set.of(
    "password", "passwd", "userPassword", "oldPassword", "newPassword", "confirmPassword", "pin",
                "otp", "token", "accessToken", "refreshToken", "authToken", "authorization", "secret",
                "clientSecret", "apiKey", "cvv", "cardNumber", "creditCard");

  private final ObjectMapper objectMapper;

  @Value("${app.logging.request.enabled:true}")
  private boolean requestLoggingEnabled;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String traceId = getOrCreateTraceId(request);

    MDC.put(TRACE_ID, traceId);
    response.setHeader("X-Trace-Id", traceId);

    if (!requestLoggingEnabled) {
      try {
        filterChain.doFilter(request, response);
      } finally {
        MDC.clear();
      }
      return;
    }

    long start = System.currentTimeMillis();
    ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

    try {
      filterChain.doFilter(requestWrapper, responseWrapper);
    } finally {
      long duration = System.currentTimeMillis() - start;
      log.info("[REQUEST] method={} uri={} query={} clientIp={} payload={} status={} duration={}ms", request.getMethod(), request.getRequestURI(), maskQueryString(request.getQueryString()), getClientIp(request), getRequestBody(requestWrapper), response.getStatus(), duration);

      responseWrapper.copyBodyToResponse();
      MDC.clear();
    }
  }

  private String getOrCreateTraceId(HttpServletRequest request) {
    String traceId = request.getHeader("X-Trace-Id");
    if (traceId != null && !traceId.isBlank()) {
      return traceId;
    }

    return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
  }

  private String getRequestBody(ContentCachingRequestWrapper request) {
    byte[] body = request.getContentAsByteArray();
    if (body.length == 0) {
      return "{}";
    }

    String rawPayload = new String(body, StandardCharsets.UTF_8).trim();
    if (rawPayload.isBlank()) {
      return "{}";
    }

    try {
      Object json = objectMapper.readValue(rawPayload, Object.class);
      maskSensitiveFields(json);

      String compactJson = objectMapper.writeValueAsString(json);
      return truncate(compactJson);
    } catch (Exception e) {
      String fallbackPayload = rawPayload.replaceAll("[\\r\\n\\t]+", " ").replaceAll("\\s+", " ").trim();
      return truncate(maskRawText(fallbackPayload));
    }
  }

  @SuppressWarnings("unchecked")
  private void maskSensitiveFields(Object node) {
    if (node instanceof Map<?, ?> map) {
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        String key = String.valueOf(entry.getKey());
        Object value = entry.getValue();

        if (isSensitiveField(key)) {
          ((Map<String, Object>) map).put(key, MASK_VALUE);
        } else {
          maskSensitiveFields(value);
        }
      }
      return;
    }

    if (node instanceof List<?> list) {
      for (Object item : list) {
        maskSensitiveFields(item);
      }
    }
  }

  private boolean isSensitiveField(String fieldName) {
    return SENSITIVE_FIELDS.stream().anyMatch(sensitive -> sensitive.equalsIgnoreCase(fieldName));
  }

  private String maskQueryString(String queryString) {
    if (queryString == null || queryString.isBlank()) {
      return null;
    }

    String[] params = queryString.split("&");
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < params.length; i++) {
      String param = params[i];
      int equalIndex = param.indexOf("=");
      if (equalIndex < 0) {
        result.append(param);
      } else {
        String key = param.substring(0, equalIndex);
        String value = param.substring(equalIndex + 1);
        result.append(key).append("=").append(isSensitiveField(key) ? MASK_VALUE : value);
      }

      if (i < params.length - 1) {
        result.append("&");
      }
    }

    return result.toString();
  }

  private String maskRawText(String value) {
    String maskedValue = value;
    for (String field : SENSITIVE_FIELDS) {
      maskedValue = maskedValue.replaceAll("(?i)(\"" + field + "\"\\s*:\\s*\")([^\"]*)(\")", "$1" + MASK_VALUE + "$3");
      maskedValue = maskedValue.replaceAll("(?i)(" + field + "\\s*=\\s*)([^&\\s,}]+)", "$1" + MASK_VALUE);
    }

    return maskedValue;
  }

  private String truncate(String value) {
    if (value == null || value.isBlank()) {
      return "{}";
    }

    if (value.length() <= MAX_PAYLOAD_LENGTH) {
      return value;
    }

    return value.substring(0, MAX_PAYLOAD_LENGTH) + "...[TRUNCATED]";
  }

  private String getClientIp(HttpServletRequest request) {
    String forwarded = request.getHeader("X-Forwarded-For");
    if (forwarded != null && !forwarded.isBlank()) {
      return forwarded.split(",")[0].trim();
    }

    return request.getRemoteAddr();
  }
}
