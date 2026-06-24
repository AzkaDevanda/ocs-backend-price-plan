package com.sts.sinorita.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "GEO_HOME_ZONE", schema = "STS")
public class GeoHomeZone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "GEO_HOME_ZONE_ID", nullable = false)
    private Long geoHomeZoneId;

    @Column(name = "GEO_HOME_ZONE_NAME", nullable = false, length = 60)
    private String geoHomeZoneName;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "GEO_HOME_ZONE_CODE", length = 60)
    private String geoHomeZoneCode;

}
