package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "RATE_PLAN")
@SequenceGenerator(name = "rate_plan_seq", sequenceName = "STS.RATE_PLAN_ID_SEQ", allocationSize = 1)
public class RatePlan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rate_plan_seq")
    @Column(name = "RATE_PLAN_ID", nullable = false)
    private Integer id;

    @Column(name = "RE_ID")
    private Integer reId;

    @Column(name = "OFFER_VER_ID")
    private Integer offerVerId;

    @Column(name = "RATE_PLAN_TYPE", nullable = false)
    private Character ratePlanType;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "RATE_PLAN_NAME", nullable = false)
    private String ratePlanName;

    @ColumnDefault("'N'")
    @Column(name = "TEMPLATE_FLAG")
    private Character templateFlag;

    @Column(name = "COMMENTS")
    private String remarks;

    @Column(name = "SRC_RATE_PLAN_ID")
    private Integer srcRatePlanId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "RATE_PLAN_CODE", length = 60)
    private String ratePlanCode;

    @Column(name = "MAPPING_EXIT")
    private Character mappingExit;
}
