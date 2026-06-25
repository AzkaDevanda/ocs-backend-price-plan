package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROD_EVENT_DIAGRAM")
@Data
@NoArgsConstructor
public class ProdEventDiagram {
    @Id
    @Column(name = "NODE_ID")
    private Long nodeId;

    @Column(name = "SRC_PROD_STATE")
    private String srcProdState;

    @Column(name = "SUBS_EVENT_ID")
    private Long subsEventId;

    @Column(name = "OBJ_PROD_STATE")
    private String objProdState;

    @Column(name = "LIFECYCLE_TYPE")
    private Integer lifecycleType;

    @Column(name = "TIMER")
    private Integer timer;

    @Column(name = "BC_ID")
    private Long bcId;

    @Column(name = "SP_ID")
    private Long spId;
}