package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "OFFER_PUBLISH_LOG")
public class OfferPublishLog {
    @Id
    @Column(name = "OFFER_PUBLISH_LOG_ID", nullable = false)
    private Long offerPublishLogId;

    @Column(name = "ACTION_TYPE", nullable = false, length = 3)
    private String actionType;

    @Column(name = "OFFER_ID", nullable = false)
    private Integer offerId;

    @Column(name = "OFFER_VER_ID")
    private Integer offerVerId;

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "CREATE_STAFF_ID", nullable = false)
    private Integer createStaffId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PRE_ACTION_TYPE", length = 3)
    private String preActionType;
}
