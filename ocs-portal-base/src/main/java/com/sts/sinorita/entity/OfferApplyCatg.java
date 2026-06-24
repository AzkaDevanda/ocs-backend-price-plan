package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.OfferApplyCatgId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OFFER_APPLY_CATG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferApplyCatg {
    @EmbeddedId
    private OfferApplyCatgId id;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "EXCLUDE_FLAG", length = 1)
    private Character excludeFlag;
}
