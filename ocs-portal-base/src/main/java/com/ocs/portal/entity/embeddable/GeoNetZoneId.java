package com.ocs.portal.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoNetZoneId implements Serializable {
  @Column(name = "GEO_HOME_ZONE_ID", nullable = false, precision = 6, scale = 0)
  private Long geoHomeZoneId;

  @Column(name = "HOME_ZONE_ID", nullable = false, precision = 6, scale = 0)
  private Long homeZoneId;
}