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
public class ReserveLimitId {
    @Column(name = "RE_ID")
    private Long reId;

    @Column(name = "RE_ATTR")
    private Long reAttr;
}
