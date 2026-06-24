package com.sts.sinorita.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OWE_ONCE_FEE", schema = "STS")
public class OweOnceFee {

    @Id
    @Column(name = "EVENT_INST_ID")
    private Long eventInstId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "SUBS_ID", nullable = false)
    private Long subsId;

    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Column(name = "CHARGE", nullable = false)
    private BigDecimal charge;

    @Column(name = "STATE", nullable = false)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "OFFER_ID", nullable = false)
    private Integer offerId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "ACCT_BOOK_ID")
    private Long acctBookId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;
}
