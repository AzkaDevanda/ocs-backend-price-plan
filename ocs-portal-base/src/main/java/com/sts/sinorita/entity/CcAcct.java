package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.CcAcctId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CC_ACCT", schema = "STS")
public class CcAcct implements Serializable {

    @EmbeddedId
    private CcAcctId id;

    @Column(name = "CC_TYPE", nullable = false)
    private Long ccType;

    @Column(name = "CREDIT_LIMIT", nullable = false)
    private Long creditLimit;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "SP_ID")
    private Long spId;

}
