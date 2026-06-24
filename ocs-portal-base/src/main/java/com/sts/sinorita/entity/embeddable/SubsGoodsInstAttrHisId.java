package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SubsGoodsInstAttrHisId {
    @Column(name = "SUBS_GOODS_INST_ID", precision = 12)
    private Long subsGoodsInstId;

    @Column(name = "ATTR_ID", precision = 6)
    private Long attrId;

    @Column(name = "SEQ", precision = 3)
    private Long seq;
}
