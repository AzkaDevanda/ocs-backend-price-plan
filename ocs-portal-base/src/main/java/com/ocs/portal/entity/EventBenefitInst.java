package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.EventBenefitId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EVENT_BENEFIT_INST")
public class EventBenefitInst {

    @EmbeddedId
    private EventBenefitId id = new EventBenefitId();

    public void setEventInstId(Long eventInstId) {
        if (this.id == null) {
            this.id = new EventBenefitId();
        }
        this.id.setEventInstId(eventInstId);
    }

    public Long getEventInstId() {
        return id.getEventInstId();
    }

    public void setPriceId(Long priceId) {
        this.id.setPriceId(priceId);
    }

    public Long getPriceId() {
        return id.getPriceId();
    }

//  @NotNull
//  @Column(name = "EVENT_INST_ID", nullable = false)
//  private Long eventInstId;
//
//  @NotNull
//  @Column(name = "PRICE_ID", nullable = false)
//  private Long priceId;

    @NotNull
    @Column(name = "SEQ", nullable = false)
    private Short seq;

    @NotNull
    @Column(name = "ACCT_RES_ID", nullable = false)
    private Integer acctResId;

    @NotNull
    @Column(name = "CHARGE", nullable = false)
    private Long charge;

    @NotNull
    @Column(name = "SECONDS", nullable = false)
    private Long seconds;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @NotNull
    @Column(name = "STATE", nullable = false)
    private Character state;

    @NotNull
    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @NotNull
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Size(max = 4000)
    @Column(name = "REF_ATTR", length = 4000)
    private String refAttr;

    @Column(name = "BAL_ID")
    private Long balId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "EFF_SECONDS")
    private Long effSeconds;

    @NotNull
    @ColumnDefault("TO_NUMBER(TO_CHAR(SYSDATE, 'MM'))")
    @Column(name = "PART_ID", nullable = false)
    private Short partId;

    @Column(name = "ACCT_ID")
    private Long acctId;

//  @Id
//  private Long id;

    @PrePersist
    public void prePersist() {

        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }

        if (stateDate == null) {
            stateDate = LocalDateTime.now();
        }

        if (seconds == null) {
            seconds = System.currentTimeMillis() / 1000;
        }

        if (partId == null) {
            partId = (short) LocalDateTime.now().getMonthValue();
        }

        if (charge == null) {
            charge = 0L;
        }
    }


    @PreUpdate
    public void preUpdate() {

        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }

        // always update state date
        stateDate = LocalDateTime.now();

        // refresh seconds
        seconds = System.currentTimeMillis() / 1000;

        // safety default
        if (charge == null) {
            charge = 0L;
        }

        if (partId == null) {
            partId = (short) LocalDateTime.now().getMonthValue();
        }
    }
}