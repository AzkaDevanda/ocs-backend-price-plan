package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class BfmRolePrivId implements Serializable {

    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;

    @Column(name = "PRIV_ID", nullable = false)
    private Long privId;
}
