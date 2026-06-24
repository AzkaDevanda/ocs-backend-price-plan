package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "TAB_DP_COND_GRP_DT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TabDpCondGrpDt implements Serializable {
    @EmbeddedId
    private TabDpCondGrpDtId id;

    @Column(name = "L_DP_REF_COND_ID", nullable = false)
    private Integer lDpRefCondId;

    @Column(name = "L_PARAM1", length = 60)
    private String lParam1;

    @Column(name = "SORT_OPERATOR", nullable = false)
    private Character sortOperator;

    @Column(name = "R_VAL", nullable = false, length = 60)
    private String rVal;

    @Column(name = "SP_ID")
    private Integer spId;
}