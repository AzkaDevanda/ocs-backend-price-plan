package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SIM_CARD_INFO_ORDER", schema = "STS")
public class SimCardInfoOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIM_CARD_INFO_ORDER_SEQ_GEN")
    @SequenceGenerator(
            name = "SIM_CARD_INFO_ORDER_SEQ_GEN",
            sequenceName = "STS.SIM_CARD_INFO_ORDER_ID_SEQ",
            allocationSize = 1
    )
    @Column(name = "SIM_CARD_INFO_ORDER_ID", nullable = false)
    private Long simCardInfoOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "SIM_CARD_ID")
    private Long simCardId;

    @Column(name = "ICCID", length = 60)
    private String iccid;

    @Column(name = "ESN", length = 60)
    private String esn;

    @Column(name = "KI", length = 120)
    private String ki;

    @Column(name = "IMSI", length = 30)
    private String imsi;

    @Column(name = "CHECK_SUM", length = 120)
    private String checkSum;

    @Column(name = "INJECT_FLAG", length = 1)
    private String injectFlag;

    @Column(name = "OLD_SIM_CARD_ID")
    private Long oldSimCardId;

    @Column(name = "OLD_CHECK_SUM", length = 120)
    private String oldCheckSum;

    @Column(name = "OLD_ESN", length = 60)
    private String oldEsn;

    @Column(name = "OLD_IMSI", length = 30)
    private String oldImsi;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    /**
     * Optional field: tidak ada di tabel, hanya untuk timestamp
     * bisa diisi manual atau otomatis lewat service
     */
    @Transient
    private LocalDateTime createdDate;

    @Transient
    private LocalDateTime updatedDate;
}
