package com.sts.sinorita.entity.embeddable;

import lombok.Data;

import java.io.Serializable;

@Data
public class IndepProdOrderAttrId implements Serializable {
    private Long orderItemId;
    private Long attrId;
}
