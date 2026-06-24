package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PROD_ATTR_VALUE")
public class ProdAttrValue {
  @EmbeddedId
  private ProdAttrValueId id;

  @Size(max = 255)
  @NotNull
  @Column(name = "VALUE", nullable = false)
  private String value;

  @NotNull
  @Column(name = "EFF_DATE", nullable = false)
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @NotNull
  @Column(name = "UPDATE_DATE", nullable = false)
  private LocalDate updateDate;

  @ColumnDefault("'N'")
  @Column(name = "NEED_UPLOAD")
  private Boolean needUpload;

  @Column(name = "ROUTING_ID")
  private Integer routingId;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "UPLOAD_TYPE")
  private Boolean uploadType;

}