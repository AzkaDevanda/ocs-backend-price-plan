package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.SpecialGroupEventRuleId;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SPECIAL_GROUP_EVENT_RULE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialGroupEventRule {

    @EmbeddedId
    private SpecialGroupEventRuleId id;

    @Column(name = "SERVICE_RULE_SERVICE", nullable = false, length = 255)
    private String serviceRuleService;

    @Column(name = "SP_ID")
    private Long spId;

}