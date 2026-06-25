package com.ocs.portal.entity.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SubsUppInstValueId implements Serializable {

    @Column(name = "SUBS_UPP_INST_ID", nullable = false, precision = 15)
    private Long subsUppInstId;

    @Column(name = "ATTR_ID", nullable = false, precision = 6)
    private Long attrId;
}