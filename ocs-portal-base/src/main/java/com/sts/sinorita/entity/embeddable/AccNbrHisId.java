package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AccNbrHisId {
    @Column(name = "ACC_NBR_ID", nullable = false)
    private Long accNbrId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq;
}
