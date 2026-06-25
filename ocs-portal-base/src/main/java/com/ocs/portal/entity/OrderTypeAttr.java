package com.ocs.portal.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDER_TYPE_ATTR", schema = "STS")
public class OrderTypeAttr {

    @Id
    @Column(name = "ORDER_ATTR_ID", nullable = false)
    private Long orderAttrId;

    @Column(name = "ORDER_TYPE", nullable = false, length = 1)
    private String orderType;

    @Column(name = "ATTR_ID", nullable = false)
    private Long attrId;

    @Column(name = "DISP_SEQ")
    private Integer dispSeq;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "SUBS_EVENT_ID")
    private Long subsEventId;
}
