package com.ocs.portal.entity;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class TriggerRuleId implements java.io.Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRIGGER_RULE_ID_SEQ_generator")
//    @SequenceGenerator(name = "TRIGGER_RULE_ID_SEQ_generator", sequenceName = "TRIGGER_RULE_ID_SEQ", allocationSize = 1)
    @Column(name = "TRIGGER_ID", nullable = false)
    private Integer triggerId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq = triggerId;

    public Integer getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(Integer triggerId) {
        this.triggerId = triggerId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TriggerRuleId entity = (TriggerRuleId) o;
        return Objects.equals(this.triggerId, entity.triggerId) &&
                Objects.equals(this.seq, entity.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(triggerId, seq);
    }

//    @PrePersist
//    public void prePersist() {
//        // Set seq to same value as triggerId before persisting
//        this.seq = this.triggerId;
//    }
}