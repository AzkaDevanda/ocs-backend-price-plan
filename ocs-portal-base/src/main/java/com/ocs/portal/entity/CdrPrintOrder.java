package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CDR_PRINT_ORDER", schema = "STS")
public class CdrPrintOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CDR_PRINT_ORDER_SEQ")
    @SequenceGenerator(
            name = "CDR_PRINT_ORDER_SEQ",
            sequenceName = "CDR_PRINT_ORDER_ID_SEQ",
            allocationSize = 1
    )
    @Column(name = "CDR_PRINT_ORDER_ID", nullable = false)
    private Long cdrPrintOrderId;

    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;

    @Column(name = "PRINT_TYPE", nullable = false, length = 30)
    private String printType;

    @Column(name = "EMAIL", length = 60)
    private String email;

    @Column(name = "BILLING_CYCLE", nullable = false, length = 60)
    private String billingCycle;

    @Column(name = "EMAIL_FILE_TYPE", length = 30)
    private String emailFileType;

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    // Optional future-proof fields (if needed)
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;
}
