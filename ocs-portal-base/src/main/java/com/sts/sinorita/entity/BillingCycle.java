package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BILLING_CYCLE")
public class BillingCycle implements Serializable {
    @Id
    @Column(name = "BILLING_CYCLE_ID", nullable = false)
    private Integer billingCycleId;

    @Column(name = "BILLING_CYCLE_TYPE_ID", nullable = false)
    private Integer billingCycleTypeId;

    @Column(name = "CYCLE_BEGIN_DATE", nullable = false)
    private Date cycleBeginDate;

    @Column(name = "CYCLE_END_DATE", nullable = false)
    private Date cycleEndDate;

    @Column(name = "STATE", nullable = false, length = 1)
    private Character state;

    @Column(name = "DEBT_DATE", nullable = false)
    private Date debtDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "RUN_DATE")
    private Date runDate;

    @Column(name = "DOCUMENT_DATE")
    private Date documentDate;

    @Column(name = "POSTING_DATE")
    private Date postingDate;

    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;

    @Column(name = "ORIGIN_DATE")
    private Date originDate;

    @Column(name = "NOTIFICATION_DATE")
    private Date notificationDate;
}
