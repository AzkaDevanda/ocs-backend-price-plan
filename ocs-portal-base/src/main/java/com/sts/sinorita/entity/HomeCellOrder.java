package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "HOME_CELL_ORDER", schema = "STS")
public class HomeCellOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOME_CELL_ORDER_SEQ_GEN")
    @SequenceGenerator(
            name = "HOME_CELL_ORDER_SEQ_GEN",
            sequenceName = "STS.HOME_CELL_ORDER_ID_SEQ",
            allocationSize = 1
    )
    @Column(name = "HOME_CELL_ORDER_ID", nullable = false)
    private Long homeCellOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "HOME_ZONE_ID", nullable = false)
    private Long homeZoneId;

    @Column(name = "OLD_HOME_ZONE_ID")
    private Long oldHomeZoneId;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    /** Optional: jika nanti ada kolom tanggal dibuat manual (misal waktu pembuatan atau update) **/
    @Transient
    private LocalDateTime createdDate;

    @Transient
    private LocalDateTime updatedDate;
}
