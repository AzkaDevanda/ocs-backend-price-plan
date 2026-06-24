package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CUST_PROF_ORDER", schema = "STS")
@SequenceGenerator(
        name = "cust_prof_order_seq_gen",
        sequenceName = "STS.CUST_PROF_ORDER_ID_SEQ",
        allocationSize = 1
)
public class CustProfOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_prof_order_seq_gen")
    @Column(name = "CUST_PROF_ORDER_ID", nullable = false)
    private Long custProfOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "TABLE_NAME", nullable = false, length = 30)
    private String tableName;

    @Column(name = "INST_ID", nullable = false)
    private Long instId;

    @Column(name = "INST_ID2", length = 30)
    private String instId2;

    @Column(name = "ATTR_ID")
    private Long attrId;

    @Column(name = "FIELD_NAME", length = 30)
    private String fieldName;

    @Column(name = "FIELD_VALUE", length = 255)
    private String fieldValue;

    @Column(name = "OLD_FIELD_VALUE", length = 255)
    private String oldFieldValue;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    // Tambahan jika nanti ada kolom tanggal (opsional, disiapkan)
    @Transient
    private LocalDateTime effDate;

    @Transient
    private LocalDateTime expDate;
}
