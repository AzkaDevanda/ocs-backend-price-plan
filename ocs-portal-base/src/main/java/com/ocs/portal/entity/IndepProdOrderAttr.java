package com.ocs.portal.entity;


import com.ocs.portal.entity.embeddable.IndepProdOrderAttrId;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "INDEP_PROD_ORDER_ATTR", schema = "STS")
@IdClass(IndepProdOrderAttrId.class)
public class IndepProdOrderAttr implements Serializable {

    @Id
    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Id
    @Column(name = "ATTR_ID", nullable = false)
    private Long attrId;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "VALUE", nullable = false, length = 255)
    private String value;

    @Column(name = "OLD_VALUE", length = 255)
    private String oldValue;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;
}
