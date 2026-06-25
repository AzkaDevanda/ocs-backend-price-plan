package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@Table(name = "RATABLE_RESOURCE")
public class RatableResource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ratable_resource_seq_generator")
    @SequenceGenerator(name = "ratable_resource_seq_generator", sequenceName = "RESOURCE_ID_SEQ", allocationSize = 1)
    @Column(name = "RESOURCE_ID", nullable = false)
    private Integer id;

    @Column(name = "ACM_TYPE", nullable = false)
    private Character acmType;

    @Column(name = "MASK", nullable = false, length = 30)
    private String mask;

    @Column(name = "RESOURCE_NAME", nullable = false, length = 60)
    private String resourceName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

    @Column(name = "OFFSET")
    private Integer offset;

    @Column(name = "TIME_UNIT")
    private Integer timeUnit;

    @Column(name = "BIND_TYPE")
    private Character bindType;

    @Column(name = "UNIT_TYPE_ID")
    private Short unitTypeId;

    @Column(name = "UNIT_PRECISION")
    private Integer unitPrecision;

    @Column(name = "PRECISION")
    private Short precision;

    @ColumnDefault("'1'")
    @Column(name = "ROUND_WAY")
    private Character roundWay;

    @Column(name = "ACCT_RES_ID")
    private Integer acctResId;

    @Column(name = "RUM")
    private Long rum;

    @Column(name = "BILLING_CYCLE_TYPE_ID")
    private Integer billingCycleTypeId;

}