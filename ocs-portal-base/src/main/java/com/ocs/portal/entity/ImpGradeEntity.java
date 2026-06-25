package com.ocs.portal.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "IMP_GRADE", schema = "STS")
public class ImpGradeEntity {

    @Id
    @Column(name = "IMP_GRADE_ID", nullable = false)
    private Integer impGradeId;

    @Column(name = "CUST_TYPE", length = 1)
    private String custType;

    @Column(name = "IMP_GRADE_NAME", nullable = false, length = 60)
    private String impGradeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "SERVICE_RULE_OBJECT")
    private Long serviceRuleObject;

    @Column(name = "SP_ID")
    private Long spId;


}
