package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.OfferApplyStaffId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OFFER_APPLY_STAFF")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferApplyStaff {
    @EmbeddedId
    private OfferApplyStaffId id;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "EXCLUDE_FLAG", length = 1)
    private Character excludeFlag;
}
