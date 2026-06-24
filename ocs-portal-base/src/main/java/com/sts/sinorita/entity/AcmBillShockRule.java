package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACM_BILL_SHOCK_RULE")
public class AcmBillShockRule {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BILL_SHOCK_RULE_ID_SEQ_generator")
//    @SequenceGenerator(name = "BILL_SHOCK_RULE_ID_SEQ_generator", sequenceName = "BILL_SHOCK_RULE_ID_SEQ", allocationSize = 1)
    @Column(name = "BILL_SHOCK_RULE_ID")
    private Long billShockRuleId;

    @Column(name = "ACM_THRESHOLD_ID", nullable = false)
    private Long acmThresholdId;

    @Column(name = "SP_ID", nullable = false)
    private Long spId;
}
