package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "TAB_DP_COND_GRP")
@NoArgsConstructor
@AllArgsConstructor
public class TabDpCondGrp implements Serializable {
    @Id
    @Column(name = "TAB_DP_COND_GRP_ID", nullable = false)
    private Integer id;

    @Column(name = "TAB_DP_COND_GRP_NAME", nullable = false, length = 60)
    private String tabDpCondGrpName;

    @Column(name = "DP_REF_COND_TYPE", nullable = false)
    private Character dpRefCondType;

    @Column(name = "SP_ID")
    private Integer spId;

}