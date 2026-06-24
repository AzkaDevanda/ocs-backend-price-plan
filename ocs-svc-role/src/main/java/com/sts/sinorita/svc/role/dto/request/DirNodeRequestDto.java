package com.sts.sinorita.svc.role.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirNodeRequestDto {
  private Long id;
  private Long parentId;
  private String name;
}
