package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.GeoNetZoneId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GEO_NET_ZONE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoNetZone {
  @EmbeddedId
  private GeoNetZoneId id;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;
}
