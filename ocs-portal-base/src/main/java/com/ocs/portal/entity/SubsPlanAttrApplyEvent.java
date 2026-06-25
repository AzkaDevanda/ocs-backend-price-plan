package com.ocs.portal.entity;


import com.ocs.portal.entity.embeddable.SubsPlanAttrApplyEventId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "SUBS_PLAN_ATTR_APPLY_EVENT", schema = "STS")
@Data
public class SubsPlanAttrApplyEvent {

    @EmbeddedId
    private SubsPlanAttrApplyEventId id;

    @Column(name = "NULLABLE", length = 1)
    private String nullable;

    @Column(name = "EDITABLE", length = 1)
    private String editable;

    @Column(name = "CSR_VISIBLE", length = 1)
    private String csrVisible;

    @Column(name = "SP_ID", precision = 6, scale = 0)
    private Long spId;
}
