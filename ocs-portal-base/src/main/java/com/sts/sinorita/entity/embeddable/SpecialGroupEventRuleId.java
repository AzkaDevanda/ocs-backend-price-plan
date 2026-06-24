package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialGroupEventRuleId implements Serializable {

    @Column(name = "EVENT_CODE", nullable = false, length = 30)
    private String eventCode;

    @Column(name = "SEQ", nullable = false)
    private Long seq;
}