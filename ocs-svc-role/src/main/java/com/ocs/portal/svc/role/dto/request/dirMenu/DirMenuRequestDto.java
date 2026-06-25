package com.ocs.portal.svc.role.dto.request.dirMenu;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DirMenuRequestDto {
  private Long dirId;
  private Long spId;
  private String state;
  private LocalDate stateDate;

  private List<MenuItemRequestDto> menuList;
}
