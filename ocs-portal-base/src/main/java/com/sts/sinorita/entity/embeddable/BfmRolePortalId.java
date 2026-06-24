package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class BfmRolePortalId implements Serializable {

    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;

    @Column(name = "PORTAL_ID", nullable = false)
    private Long portalId;
}
