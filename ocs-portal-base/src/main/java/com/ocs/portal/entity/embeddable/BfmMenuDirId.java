package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class BfmMenuDirId implements Serializable {
  @Column(name = "MENU_ID", nullable = false)
  private Long menuId;

  @Column(name = "DIR_ID", nullable = false)
  private Long dirId;
}
