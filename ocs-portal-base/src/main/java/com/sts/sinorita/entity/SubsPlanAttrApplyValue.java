package com.sts.sinorita.entity;


import com.sts.sinorita.entity.embeddable.SubsPlanAttrApplyValueId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SUBS_PLAN_ATTR_APPLY_VALUE", schema = "STS")
public class SubsPlanAttrApplyValue {

    @EmbeddedId
    private SubsPlanAttrApplyValueId id;

    @Column(name = "SP_ID", precision = 6, scale = 0)
    private Long spId;
}

