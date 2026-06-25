package com.ocs.portal.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ocs.portal.entity.embeddable.CcSubsId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CC_SUBS", schema = "STS")
public class CcSubs implements Serializable {

    @EmbeddedId
    private CcSubsId id;

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

    @Column(name = "ROUTING_ID")
    private Long routingId;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

}
