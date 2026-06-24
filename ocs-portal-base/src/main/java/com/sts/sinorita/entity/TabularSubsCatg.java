package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "TABULAR_SUBS_CATG")
public class TabularSubsCatg implements Serializable {
    @Id
    @Column(name = "SUBS_CATG_ID", nullable = false)
    private Long subsCatgId;

    @Column(name = "SP_ID")
    private Long spId;
}
