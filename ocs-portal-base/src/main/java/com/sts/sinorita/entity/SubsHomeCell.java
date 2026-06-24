package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.SubsHomeCellId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "SUBS_HOME_CELL")
@Data
@Entity
public class SubsHomeCell implements Serializable {

    @EmbeddedId
    private SubsHomeCellId id;

    @Column(name = "HOME_ZONE_ID")
    private Long homeZoneId;

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
}
