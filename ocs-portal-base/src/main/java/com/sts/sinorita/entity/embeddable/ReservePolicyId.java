package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ReservePolicyId {
    @Column(name = "RE_ID", nullable = false)
    private Long reId;

    @Column(name = "RE_ATTR", nullable = false)
    private Long reAttr;

    @Column(name = "SEQ", nullable = false)
    private Long seq;

}
