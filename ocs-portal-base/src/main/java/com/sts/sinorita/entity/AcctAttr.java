package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "ACCT_ATTR")
public class AcctAttr implements Serializable {
    @Id
    @Column(name = "ATTR_ID", nullable = false)
    private Integer attrId;

    @Column(name = "DISP_ORDER")
    private Integer dispOrder;

    @Column(name = "ATTR_VALUE", length = 120)
    private String attrValue;

    @Column(name = "SP_ID")
    private Integer spId;

}
