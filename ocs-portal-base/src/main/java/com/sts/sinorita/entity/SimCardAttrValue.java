package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.SimCardAttrValueId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "SIM_CARD_ATTR_VALUE")
public class SimCardAttrValue implements Serializable {
    @EmbeddedId
    private SimCardAttrValueId id;

    @Column(name = "ATTR_VALUE")
    private String attrValue;

    @Column(name = "SP_ID")
    private Integer spId;
}
