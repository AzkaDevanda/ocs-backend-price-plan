package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Data;

@Data
@Embeddable
public class SubsGoodsInstAttrId implements Serializable {
    @Column(name = "SUBS_GOODS_INST_ID", nullable = false)
    private Long subsGoodsInstId;

    @Column(name = "ATTR_ID", nullable = false)
    private Long attrId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubsGoodsInstAttrId that = (SubsGoodsInstAttrId) o;
        return Objects.equals(subsGoodsInstId, that.subsGoodsInstId) && Objects.equals(attrId, that.attrId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subsGoodsInstId, attrId);
    }
}
