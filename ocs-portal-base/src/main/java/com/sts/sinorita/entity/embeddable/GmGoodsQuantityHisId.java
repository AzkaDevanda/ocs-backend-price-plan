package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Data
@Embeddable
public class GmGoodsQuantityHisId implements Serializable {

    @Column(name = "GOODS_QUANTITY_ID", nullable = false)
    private Long goodsQuantityId;

    @Column(name = "SEQ", nullable = false)
    private Long seq;
}
