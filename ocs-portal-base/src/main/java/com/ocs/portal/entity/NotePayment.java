package com.ocs.portal.entity;

import java.time.LocalDateTime;

import com.ocs.portal.entity.embeddable.NotePaymentId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "NOTE_PAYMENT", schema = "STS")
public class NotePayment {

    @EmbeddedId
    private NotePaymentId id;

    @Column(name = "CHARGE")
    private Long charge;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PRE_BALANCE")
    private Long preBalance;
}