package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity for VODA_CHARGE_DET table.
 * Original: VodaChargeDetDto from SetGptoInvoiceManager
 */
@Data
@Entity
@Table(name = "VODA_CHARGE_DET")
public class VodaChargeDet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VODA_CHARGE_DET_SEQ")
    @SequenceGenerator(name = "VODA_CHARGE_DET_SEQ", sequenceName = "VODA_CHARGE_DET_ID_SEQ", allocationSize = 1)
    @Column(name = "CHARGE_DET_ID")
    private Long chargeDetId;

    @Column(name = "INVOICE_NBR", length = 20)
    private String invoiceNbr;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "EVENT_INST_ID")
    private Long eventInstId;

    @Column(name = "PRICE_ID")
    private Long priceId;

    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "DEDUCT_SEQ")
    private Long deductSeq;

    @Column(name = "CHARGE")
    private Long charge;
}
