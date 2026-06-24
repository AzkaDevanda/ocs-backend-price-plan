package com.sts.sinorita.svc.role.dto.request.dirMenu;

import lombok.Data;

@Data
public class DirRequestDto {
  private Long dirId;
  private String dirName;
  private Long parentId;
  private String iconUrl;
  private Long spId;
  private String comments;
}