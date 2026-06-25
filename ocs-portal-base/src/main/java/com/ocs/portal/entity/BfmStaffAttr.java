package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.BfmStaffAttrId;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "BFM_STAFF_ATTR", schema = "POT")
@IdClass(BfmStaffAttrId.class)
public class BfmStaffAttr implements Serializable {

    @Id
    @Column(name = "STAFF_ID")
    private Long staffId;

    @Id
    @Column(name = "ATTR_CODE", length = 128)
    private String attrCode;

    @Column(name = "ATTR_NAME", length = 128)
    private String attrName;

    @Column(name = "ATTR_VALUE", length = 256)
    private String attrValue;

}
