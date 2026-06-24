package com.sts.sinorita.entity.pot;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.sts.sinorita.entity.embeddable.BfmMenuDirId;

@Data
@Entity
@Table(name = "BFM_MENU_DIR", schema = "POT")
public class BfmMenuDir {
  @EmbeddedId
  private BfmMenuDirId id;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "STATE_DATE")
  private LocalDateTime stateDate;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "CLIENT_TYPE", length = 8)
  private String clientType;
}
