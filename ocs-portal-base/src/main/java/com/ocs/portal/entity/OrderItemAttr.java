package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ORDER_ITEM_ATTR")
public class OrderItemAttr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ATTR_ID")
    private Long orderItemAttrId;

    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;

    @Column(name = "ATTR_CODE")
    private String attrCode;

    @Column(name = "ATTR_VALUE")
    private String value;
}
