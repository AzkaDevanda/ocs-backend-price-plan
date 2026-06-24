package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.OfferAttrApplyOperId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "OFFER_ATTR_APPLY_OPER", schema = "STS")
@Data
public class OfferAttrApplyOper {

    @EmbeddedId
    private OfferAttrApplyOperId id;

    @Column(name = "DEFAULT_VALUE", length = 4000)
    private String defaultValue;

    @Column(name = "EXCLUDE_FLAG", length = 1)
    private String excludeFlag;

    @Column(name = "SP_ID", precision = 6, scale = 0)
    private Long spId;

    @Column(name = "IS_EDITABLE", length = 1)
    private String isEditable = "Y";
}