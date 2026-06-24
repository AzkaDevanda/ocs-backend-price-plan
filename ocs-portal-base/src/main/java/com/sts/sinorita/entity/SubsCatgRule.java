package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "SUBS_CATG_RULE")
public class SubsCatgRule implements Serializable {
    @Id
    @Column(name = "SUBS_CATG_RULE_ID", nullable = false)
    private Long subsCatgRuleId;

    @Column(name = "SUBS_CATG_ID", nullable = false)
    private Long subsCatgId;

    @Column(name = "PARENT_SUBS_CATG_RULE_ID")
    private Long parentSubsCatgRuleId;

    @Column(name = "RELATION_TYPE", length = 1)
    private String relationType;

    @Column(name = "SP_ID")
    private Long spId;
}
