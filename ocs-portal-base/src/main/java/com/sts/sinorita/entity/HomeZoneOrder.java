package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "HOME_ZONE_ORDER", schema = "STS")
public class HomeZoneOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOME_ZONE_ORDER_SEQ")
    @SequenceGenerator(
            name = "HOME_ZONE_ORDER_SEQ",
            sequenceName = "HOME_ZONE_ORDER_ID_SEQ",
            allocationSize = 1
    )
    @Column(name = "HOME_ZONE_ORDER_ID", nullable = false)
    private Long homeZoneOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "GEO_HOME_ZONE_ID", nullable = false)
    private Integer geoHomeZoneId;

    @Column(name = "OLD_GEO_HOME_ZONE_ID")
    private Integer oldGeoHomeZoneId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;
}
