package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Table(name = "TAB_DP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TabDp implements Serializable {

    @Id
    @Column(name = "DP_ID", nullable = false)
    private Integer id;

    @Column(name = "TAB_DP_TYPE", nullable = false)
    private Character tabDpType;

    @Column(name = "REF_DISCT_OBJ_ID", nullable = false)
    private Integer refDisctObjId;

    @Column(name = "ACCT_ITEM_TYPE_ID", nullable = false)
    private Integer acctItemTypeId;

    @Column(name = "DISTRIBUTE_METHOD", nullable = false)
    private Character distributeMethod;

    @Column(name = "TAB_DP_COND_GRP_ID", nullable = false)
    private Integer tabDpCondGrpId;

    @Column(name = "CALC_DISCT_OBJ_ID", nullable = false)
    private Integer calcDisctObjId;

    @Column(name = "APPLY_DISCT_OBJ_ID", nullable = false)
    private Integer applyDisctObjId;

    @ColumnDefault("'N'")
    @Column(name = "NEGATIVE_FLAG", nullable = false)
    private Character negativeFlag;

    @Column(name = "SP_ID")
    private Integer spId;
}