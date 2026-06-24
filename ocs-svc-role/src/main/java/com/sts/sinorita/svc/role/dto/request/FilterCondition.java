package com.sts.sinorita.svc.role.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterCondition {
  private String column; // Nama kolom di tabel
  private String function; // UPPER, LOWER, NONE
  private String operator; // =, LIKE, IN
  private Object value; // Nilai pencarian
}
