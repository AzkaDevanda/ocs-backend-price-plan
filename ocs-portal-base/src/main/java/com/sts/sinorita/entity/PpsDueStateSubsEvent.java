package com.sts.sinorita.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "PPS_DUE_STATE_SUBS_EVENT", schema = "STS")
public class PpsDueStateSubsEvent implements Serializable {

    @Id
    @Column(name = "PPS_DUE_STATE_SUBS_EVENT_ID", nullable = false)
    private Long ppsDueStateSubsEventId;

    @Column(name = "SRC_PROD_STATE", length = 1, nullable = false)
    private String srcProdState;

    @Column(name = "DUE_STATE_CHANGE", length = 1)
    private String dueStateChange;

    @Column(name = "EXPIRE_DATE_CHANGE", length = 1)
    private String expireDateChange;

    @Column(name = "SUBS_EVENT", nullable = false)
    private Long subsEvent;

    @Column(name = "UNSUIT_CHANNEL_LIST", length = 255)
    private String unsuitChannelList;

    @Column(name = "BLOCK_REASON", length = 60)
    private String blockReason;

}
