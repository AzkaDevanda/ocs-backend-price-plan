package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HOME_ZONE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeZone {

  @Id
  @Column(name = "HOME_ZONE_ID", nullable = false, precision = 6, scale = 0)
  private Long homeZoneId;

  @Column(name = "HOME_ZONE_NAME", nullable = false, length = 60)
  private String homeZoneName;

  @Column(name = "HOME_ZONE_CODE", nullable = false, length = 60)
  private String homeZoneCode;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;
}
