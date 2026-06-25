package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity for VODA_GPTO_INVOICE table.
 * Original: VodaGptoInvoiceDto from SetGptoInvoiceManager
 */
@Data
@Entity
@Table(name = "VODA_GPTO_INVOICE")
public class VodaGptoInvoice {

    @Id
    @Column(name = "INVOICE_NBR", length = 20)
    private String invoiceNbr;

    @Column(name = "INVOICE_ID")
    private Long invoiceId;

    @Column(name = "INVOICE_TYPE", length = 5)
    private String invoiceType;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "TOTAL_DUE")
    private Long totalDue;

    @Column(name = "OPER_TYPE", length = 10)
    private String operType;

    @Column(name = "STATE", length = 5)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "DUE_DATE")
    private LocalDateTime dueDate;

    @Lob
    @Column(name = "INVOICE_CONTENT")
    private String invoiceContent;

    @Column(name = "CUST_ORDER_NBR", length = 60)
    private String custOrderNbr;

    @Column(name = "INI_INVOICE_NBR", length = 20)
    private String iniInvoiceNbr;

    @Column(name = "PAYMENT_DATE")
    private LocalDateTime paymentDate;

    @Column(name = "PAID_AMOUNT")
    private Long paidAmount;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Lob
    @Column(name = "REF_ATTR")
    private byte[] refAttr;

    @Column(name = "NEW_ACCT_ID")
    private Long newAcctId;
}
