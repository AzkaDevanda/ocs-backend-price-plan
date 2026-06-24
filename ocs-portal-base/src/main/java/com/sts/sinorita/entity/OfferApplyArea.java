package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.OfferApplyAreaId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OFFER_APPLY_AREA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferApplyArea {
    @EmbeddedId
    private OfferApplyAreaId id;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "EXCLUDE_FLAG", length = 1)
    private Character excludeFlag;
}
