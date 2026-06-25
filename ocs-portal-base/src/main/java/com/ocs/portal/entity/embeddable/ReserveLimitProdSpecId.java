package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ReserveLimitProdSpecId {
    @Column(name = "RE_ID")
    private Integer reId;

    @Column(name = "RE_ATTR")
    private Long reAttr;

    @Column(name = "PROD_SPEC_ID")
    private Long prodSpecId;
}
