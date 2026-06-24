package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.GeoHomeZoneCatgRlatId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GEO_HOME_ZONE_CATG_RLAT", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoHomeZoneCatgRlat {
  @EmbeddedId
  private GeoHomeZoneCatgRlatId id;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;
}
