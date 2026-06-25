package com.ocs.portal.svc.role.dto.request.dirMenu;

import lombok.Data;

@Data
public class MenuRequestDto {
  private Long menuId;
  private String menuName;
  private String menuType;
  private String iconUrl;
  private PrivRequestDto priv;
  private Long spId;
  private String specialCondition;
}
// {
//     "privName": "asdsad",
//     "url": "dasdsadsa",
//     "iconUrl": "iconfont icon-gene-vpn-lock",
//     "menuType": "S",
//     "privCode": "dsadsadsa",
//     "privEl": "sdadsa",
//     "eventType": "Add",
//     "comments": "asddas",
//     "specialCondition": "asdsdadsa",
//     "privType": "M"
// }
