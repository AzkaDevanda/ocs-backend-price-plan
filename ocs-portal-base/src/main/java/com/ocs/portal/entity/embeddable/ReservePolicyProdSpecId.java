package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ReservePolicyProdSpecId {
    @Column(name = "RE_ID")
    private Long reId;

    @Column(name = "RE_ATTR")
    private Long reAttr;

    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "PROD_SPEC_ID")
    private Long prodSpecId;
}
