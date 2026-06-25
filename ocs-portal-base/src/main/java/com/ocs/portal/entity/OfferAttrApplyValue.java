package com.ocs.portal.entity;


import com.ocs.portal.entity.embeddable.OfferAttrApplyValueId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "OFFER_ATTR_APPLY_VALUE", schema = "STS")
public class OfferAttrApplyValue {

    @EmbeddedId
    private OfferAttrApplyValueId id;

    @Column(name = "SP_ID", precision = 6, scale = 0)
    private Long spId;
}
