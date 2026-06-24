package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "PROD_STATE_CHANGE_LOG", schema = "STS")
@Data
public class ProdStateChangeLog {

    @Id
    @Column(name = "PROD_STATE_CHANGE_ID", nullable = false)
    private Long prodStateChangeId;

    @Column(name = "PROD_ID", nullable = false)
    private Long prodId;

    @Column(name = "OLD_PROD_STATE", nullable = false)
    private String oldProdState;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "SUBS_EVENT_ID")
    private Long subsEventId;

    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "NEW_PROD_STATE", nullable = false)
    private String newProdState;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;
}