package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "VOUCHER_CARD", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherCard {

    @Id
    @Column(name = "VC_ID", nullable = false)
    private Long vcId;

    @Column(name = "VC_STATE", length = 1)
    private String vcState;

    @Column(name = "CARD_NUMBER", length = 60)
    private String cardNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OPERATION_TIME")
    private LocalDateTime operationTime;

    @Column(name = "CARD_TYPE", length = 60)
    private String cardType;

    @Column(name = "FACE_VALUE", length = 60)
    private String faceValue;

    @Column(name = "CARD_STATE", length = 1)
    private String cardState;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "LOCKED_FLAG", length = 1)
    private String lockedFlag;

    @Column(name = "BATCH_NO", length = 60)
    private String batchNo;

    @Column(name = "PIN", length = 60)
    private String pin;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "IS_SOLD")
    private Integer isSold;

    @Column(name = "SP_ID")
    private Long spId;
}
