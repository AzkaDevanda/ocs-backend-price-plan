package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "TRIGGER_RULE")
public class TriggerRule implements Serializable {

    @Id
    @Column(name = "TRIGGER_ID", nullable = false)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRIGGER_RULE_ID_SEQ_generator")
//    @SequenceGenerator(name = "TRIGGER_RULE_ID_SEQ_generator", sequenceName = "TRIGGER_RULE_ID_SEQ", allocationSize = 1)
    private Integer triggerId;


    @Column(name = "SEQ", nullable = false)
    private Integer seq;

//    @EmbeddedId
//    private TriggerRuleId id;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "OFFER_VER_ID", nullable = false)
    private Integer offerVerId;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "STATE", nullable = false)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Lob
    @Column(name = "RULE_SCRIPT",columnDefinition = "CLOB")
    private String ruleScript;

    @Lob
    @Column(name = "SCRIPT_PAGE",columnDefinition = "BLOB")
    private byte[] scriptPage;

    @Column(name = "SCRIPT_TEMPLET_ID")
    private Integer scriptTempletId;

    @Column(name = "WORKFLOW_ID")
    private Integer workflowId;

//    @PostPersist
//    public void prePersist() {
//        // Set seq to same value as triggerId before persisting
//        this.seq = this.triggerId;
//    }
}