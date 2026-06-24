package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Data
@Embeddable
public class GmGoodsInstHisId implements Serializable {

    @Column(name = "GOODS_ID", nullable = false)
    private Long goodsId;

    @Column(name = "SEQ", nullable = false)
    private Integer seq;
}
