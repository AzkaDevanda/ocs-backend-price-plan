package com.ocs.portal.entity;


import com.ocs.portal.entity.embeddable.SubsPlanOfferAttrValueId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "SUBS_PLAN_OFFER_ATTR_VALUE", schema = "STS")
@Data
public class SubsPlanOfferAttrValue {

    @EmbeddedId
    private SubsPlanOfferAttrValueId id;

    @Column(name = "SP_ID", precision = 6, scale = 0)
    private Long spId;
}
