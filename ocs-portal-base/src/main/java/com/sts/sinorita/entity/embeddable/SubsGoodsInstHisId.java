package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SubsGoodsInstHisId {

    @Column(name = "SUBS_GOODS_INST_ID", precision = 12)
    private Long subsGoodsInstId;

    @Column(name = "SEQ", precision = 3)
    private Long seq;
}
