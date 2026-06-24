package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.AcctDeliverMethodId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ACCT_DELIVER_METHOD")
public class AcctDeliverMethod implements Serializable {
    @EmbeddedId
    private AcctDeliverMethodId id;

    @Column(name = "EFF_DATE")
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "SP_ID")
    private Long spId;
}
