package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.SubsPlanAttrApplyOperId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "SUBS_PLAN_ATTR_APPLY_OPER", schema = "STS")
@Data
public class SubsPlanAttrApplyOper {


    @EmbeddedId
    private SubsPlanAttrApplyOperId id;   // Composite PK (SUBS_PLAN_OFFER_ATTR_ID + OPERATION_TYPE)

    @Column(name = "DEFAULT_VALUE", length = 4000)
    private String defaultValue;

    @Column(name = "SP_ID", precision = 6, scale = 0)
    private Long spId;

    @Column(name = "EXCLUDE_FLAG", length = 1)
    private String excludeFlag;

    @Column(name = "IS_EDITABLE", length = 1)
    private String isEditable = "Y";

}
