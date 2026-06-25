package com.ocs.portal.entity.embeddable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CcSubsId implements Serializable {

    @Column(name = "SUBS_ID", nullable = false)
    private Long subsId;

    @Column(name = "SEQ", nullable = false)
    private Long seq;

}