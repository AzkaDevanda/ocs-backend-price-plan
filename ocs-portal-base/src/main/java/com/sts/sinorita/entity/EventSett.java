package com.sts.sinorita.entity;


import com.sts.sinorita.entity.embeddable.EventSettPK;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "EVENT_SETT", schema = "STS")
@IdClass(EventSettPK.class)
public class EventSett {

    @Id
    @Column(name = "EVENT_PAYMENT_ID", nullable = false)
    private Long eventPaymentId;

    @Id
    @Column(name = "ACCT_BOOK_ID", nullable = false)
    private Long acctBookId;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "EVENT_INST_ID")
    private Long eventInstId;

    @Column(name = "PRICE_ID")
    private Long priceId;

    @Column(name = "SEQ")
    private Integer seq;

    @Column(name = "DEDUCT_SEQ")
    private Integer deductSeq;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

}
