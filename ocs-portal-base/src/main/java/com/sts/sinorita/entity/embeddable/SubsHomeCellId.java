package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SubsHomeCellId implements Serializable {

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "SEQ")
    private Long seq;
}
