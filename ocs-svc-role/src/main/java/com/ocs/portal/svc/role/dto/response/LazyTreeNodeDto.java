package com.ocs.portal.svc.role.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LazyTreeNodeDto {

  private Long id;
  private Long parentId;
  private String name;
  private String code;
  private String type; // 0 = DIR, 1 = MENU
  private String url;
  private String iconUrl;
  private String isHold;
  private String isAuthorized;

  // penting untuk lazy loading
  private boolean hasChildren;
}