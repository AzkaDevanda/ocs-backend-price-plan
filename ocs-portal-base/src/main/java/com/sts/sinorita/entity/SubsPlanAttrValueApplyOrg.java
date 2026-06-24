package com.sts.sinorita.entity;


import com.sts.sinorita.entity.embeddable.SubsPlanAttrValueApplyOrgId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SUBS_PLAN_ATTR_VALUE_APPLY_ORG", schema = "STS")
public class SubsPlanAttrValueApplyOrg {

    @EmbeddedId
    private SubsPlanAttrValueApplyOrgId id;

    @Column(name = "EXCLUDE_FLAG", length = 1)
    private String excludeFlag;

    @Column(name = "SP_ID", precision = 6, scale = 0)
    private Long spId;
}
