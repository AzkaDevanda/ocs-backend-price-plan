package com.sts.sinorita.svc.role.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationUtils {
  private final Map<String, String> errors = new LinkedHashMap<>();

  public void isNotNull(Object obj, String fieldName, String message) {
    if (obj == null || (obj instanceof String str && str.trim().isEmpty())) {
      errors.put(fieldName, message);
    }
  }

  public void isNotNullMany(Object[][] fields) {
    for (Object[] field : fields) {
      isNotNull(field[0], field[1].toString(), field[2].toString());
    }
  }

  public void validate() {
    if (!errors.isEmpty()) {
      throw new ValidationException(errors);
    }
  }

  public Map<String, String> getErrors() {
    return errors;
  }
}
