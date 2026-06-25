package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "GEO_HOME_ZONE_CATG", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoHomeZoneCatg {
  @Id
  @Column(name = "GEO_HOME_ZONE_CATG_ID", nullable = false, precision = 9, scale = 0)
  private Long geoHomeZoneCatgId;

  @Column(name = "PARENT_CATG_ID", precision = 9, scale = 0)
  private Long parentCatgId;

  @Column(name = "GEO_HOME_ZONE_CATG_CODE", nullable = false, length = 30)
  private String geoHomeZoneCatgCode;

  @Column(name = "GEO_HOME_ZONE_CATG_NAME", length = 60)
  private String geoHomeZoneCatgName;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "EFF_DATE", nullable = false)
  private LocalDate effDate;

  @Column(name = "EXP_DATE")
  private LocalDate expDate;

  @Column(name = "CREATE_DATE", nullable = false)
  private LocalDate createDate;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;
}