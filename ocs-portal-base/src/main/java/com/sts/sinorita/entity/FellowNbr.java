package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.sts.sinorita.entity.embeddable.FellowNbrId;

@Entity
@Table(name = "FELLOW_NBR", schema = "STS", indexes = {
    @Index(name = "IDX_FELLOW_NBR_NBR", columnList = "FELLOW_NBR, STATE")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FellowNbr implements Serializable {

  private static final long serialVersionUID = 1L;

  // =========================
  // PRIMARY KEY (COMPOSITE)
  // =========================
  @EmbeddedId
  private FellowNbrId id;

  // =========================
  // COLUMNS
  // =========================
  @Column(name = "FELLOW_NBR_TYPE_ID", nullable = false, precision = 6, scale = 0)
  private Long fellowNbrTypeId;

  @Column(name = "FELLOW_NBR", nullable = false, length = 30)
  private String fellowNbr;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDateTime stateDate;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDateTime effDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDateTime updateDate;

  @Column(name = "SHORT_NBR", length = 30)
  private String shortNbr;

  @Column(name = "PROD_ID", precision = 12, scale = 0)
  private Long prodId;

  @Column(name = "ROUTING_ID", precision = 6, scale = 0)
  private Long routingId;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;

  @Column(name = "EXT1", precision = 12, scale = 0)
  private Long ext1;

  @Column(name = "EXT2", length = 255)
  private String ext2;

  @Column(name = "UPLOAD_TYPE", length = 1)
  private String uploadType;
}