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
@Table(name = "TAB_DP_DT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TabDpDt implements Serializable {
    @EmbeddedId
    private TabDpDtId id;

    @Column(name = "DISCT_CALC_METHOD")
    private Character disctCalcMethod;

    @Column(name = "CALC_VAL")
    private Integer calcVal;

    @Column(name = "F_VAL")
    private Integer fVal;

    @Column(name = "C_VAL")
    private Integer cVal;

    @Column(name = "S_VAL", nullable = false)
    private Long sVal;

    @Column(name = "E_VAL", nullable = false)
    private Long eVal;

    @Column(name = "SP_ID")
    private Integer spId;
}