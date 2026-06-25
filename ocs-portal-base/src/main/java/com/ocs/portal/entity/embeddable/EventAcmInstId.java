package com.ocs.portal.entity.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class EventAcmInstId implements Serializable {

    @Column(name = "EVENT_INST_ID")
    private Long eventInstId;

    @Column(name = "SEQ")
    private Integer seq;
}
