package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "BWF_STEP", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BwfStep implements Serializable {
    @Id
    @Column(name = "STEP_ID", nullable = false)
    private Integer id;

    @Column(name = "NODE_ID", nullable = false)
    private Integer nodeId;

    @Column(name = "OUTPUT_NODE_ID")
    private Integer outputNodeId;

    @Column(name = "SORT_RULE_NAME", nullable = false, length = 60)
    private String sortRuleName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "EXEC_ORDER", nullable = false)
    private Integer execOrder;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "SP_ID")
    private Integer spId;
}
