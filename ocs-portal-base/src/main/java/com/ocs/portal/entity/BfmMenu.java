package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "BFM_MENU", schema = "POT")
@Data
public class BfmMenu {
  @Id
  @Column(name = "MENU_ID")
  private Long menuId;

  @Column(name = "ICON_URL", length = 255)
  private String iconUrl;

  @Column(name = "STATE", length = 1)
  private String state;

  @Column(name = "STATE_DATE")
  private LocalDate stateDate;

  @Column(name = "MENU_TYPE", length = 1)
  private String menuType;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "CLIENT_TYPE", length = 8)
  private String clientType;

  @Column(name = "DATA_MASKING", length = 255)
  private String dataMasking;

  @Column(name = "DATA_MASKING_INFO", length = 255)
  private String dataMaskingInfo;

  @Column(name = "FEATURE_CODE", length = 60)
  private String featureCode;
}
