package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "UP_RULE")
public class UpRule implements Serializable {
    @Id
    @SequenceGenerator(name = "UP_RULE_ID_SEQ", sequenceName = "UP_RULE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UP_RULE_ID_SEQ")
    @Column(name = "UP_RULE_ID", nullable = false)
    private Long upRuleId;

    @Column(name = "UP_ID", nullable = false)
    private Long upId;

    @Column(name = "PRIORITY", precision = 6)
    private Integer priority;

    @Lob
    @Column(name = "RULE_SCRIPT", nullable = false)
    private String ruleScript;

    @Column(name = "RULE_COMMENTS", length = 4000)
    private String ruleComments;

    @Column(name = "SP_ID", precision = 6)
    private Integer spId;

    @Lob
    @Column(name = "SCRIPT_PAGE")
    private byte[] scriptPage;

    @Column(name = "SCRIPT_TEMPLET_ID")
    private Integer scriptTempletId;

}
