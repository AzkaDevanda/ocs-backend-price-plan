package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoHomeZoneCatgRlatId implements Serializable {
  @Column(name = "GEO_HOME_ZONE_ID", nullable = false, precision = 6, scale = 0)
  private Long geoHomeZoneId;

  @Column(name = "GEO_HOME_ZONE_CATG_ID", nullable = false, precision = 9, scale = 0)
  private Long geoHomeZoneCatgId;
}
