package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;

@Embeddable
@Data
public class AccNbrReserveId {

    @Column(name = "ACC_NBR_ID", nullable = false)
    private Long accNbrId;

    @Column(name = "SEQ", nullable = false)
    private Long seq;
}
