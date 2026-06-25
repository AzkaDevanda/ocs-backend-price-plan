package com.ocs.portal.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "FELLOW_NBR_ORDER", schema = "STS")
public class FellowNbrOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FELLOW_NBR_ORDER_ID_SEQ_G")
    @SequenceGenerator(name = "FELLOW_NBR_ORDER_ID_SEQ_G", sequenceName = "FELLOW_NBR_ORDER_ID_SEQ", allocationSize = 1)
    @Column(name = "FELLOW_NBR_ORDER_ID", nullable = false)
    private Long fellowNbrOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "FELLOW_NBR_TYPE_ID", nullable = false)
    private Long fellowNbrTypeId;

    @Column(name = "FELLOW_NBR", nullable = false, length = 30)
    private String fellowNbr;

    @Column(name = "SHORT_NBR", length = 30)
    private String shortNbr;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "OLD_FELLOW_NBR", length = 30)
    private String oldFellowNbr;

    @Column(name = "OLD_SHORT_NBR", length = 30)
    private String oldShortNbr;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    @Column(name = "EXT1")
    private Long ext1;

    @Column(name = "EXT2", length = 255)
    private String ext2;

    /**
     * Mengatur default PART_ID seperti di Oracle (to_number(to_char(sysdate,'mm'))).
     */
    @PrePersist
    public void prePersist() {
        if (partId == null) {
            partId = Integer.parseInt(new java.text.SimpleDateFormat("MM").format(new java.util.Date()));
        }
    }
}