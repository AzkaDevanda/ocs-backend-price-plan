package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.OfferApplyRoleId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OFFER_APPLY_ROLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferApplyRole {
    @EmbeddedId
    private OfferApplyRoleId id;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "EXCLUDE_FLAG", length = 1)
    private Character excludeFlag;
}
