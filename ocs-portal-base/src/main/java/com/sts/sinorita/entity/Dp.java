package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "DP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dp implements Serializable {
    @Id
    @Column(name = "DP_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dp_id_seq_generator")
    @SequenceGenerator(name = "dp_id_seq_generator", sequenceName = "DP_ID_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "DP_TYPE", nullable = false)
    private Character dpType;

    @Column(name = "BILLING_PLAN_TYPE", nullable = false)
    private Character billingPlanType;

    @Column(name = "OFFER_VER_ID")
    private Integer offerVerId;

    @Column(name = "DP_NAME", nullable = false, length = 60)
    private String dpName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "TAX_ID")
    private Integer taxId;

}