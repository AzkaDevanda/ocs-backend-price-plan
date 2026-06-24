package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class BfmUserPortalId {

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "PORTAL_ID", nullable = false)
    private Long portalId;
}
