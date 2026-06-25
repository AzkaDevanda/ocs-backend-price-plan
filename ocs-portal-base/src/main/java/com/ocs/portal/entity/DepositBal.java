package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DEPOSIT_BAL", schema = "STS")
public class DepositBal {

    @Id
    @Column(name = "SUBS_ID", nullable = false)
    private Long subsId;

    @Column(name = "BAL", nullable = false)
    private Long bal;

    @Column(name = "ROUTING_ID")
    private Integer routingId;

    @Column(name = "SP_ID")
    private Integer spId;
}
