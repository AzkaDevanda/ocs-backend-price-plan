package com.ocs.portal.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BfmUserPrivId {

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "PRIV_ID", nullable = false)
    private Long privId;
}
