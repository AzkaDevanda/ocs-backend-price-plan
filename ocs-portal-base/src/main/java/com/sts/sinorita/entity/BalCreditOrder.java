package com.sts.sinorita.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "BAL_CREDIT_ORDER", schema = "STS")
public class BalCreditOrder implements Serializable {

    @Id
    @Column(name = "BAL_CREDIT_ORDER_ID", nullable = false)
    private Long balCreditOrderId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "ACCT_BOOK_ID")
    private Long acctBookId;

    @Column(name = "BLOCK_REASON", length = 60)
    private String blockReason;

    @Column(name = "SP_ID")
    private Long spId;

}
