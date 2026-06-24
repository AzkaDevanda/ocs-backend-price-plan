package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.SubsHomeZoneId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "SUBS_HOME_ZONE")
@Data
@Entity
public class SubsHomeZone implements Serializable {
    @EmbeddedId
    private SubsHomeZoneId id;

    @Column(name = "GEO_HOME_ZONE_ID")
    private Long geoHomeZoneId;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "STATE")
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "ROUTING_ID")
    private Long routingId;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

}
