package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "BS_TYPE_PTL")
public class BsTypePtl {
    @Id
    @Column(name = "BS_TYPE_PTL_ID", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "BS_TYPE_ID", nullable = false)
    private Long bsTypeId;

    @Column(name = "ACCT_RES_ID")
    private Integer acctResId;

    @NotNull
    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDate updateDate;

    @NotNull
    @Column(name = "PARTY_TYPE", nullable = false)
    private Character partyType;

    @Size(max = 30)
    @NotNull
    @Column(name = "PARTY_CODE", nullable = false, length = 30)
    private String partyCode;

    @NotNull
    @Column(name = "STATE", nullable = false)
    private Character state;

    @NotNull
    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

}