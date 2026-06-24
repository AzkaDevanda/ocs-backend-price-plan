package com.sts.sinorita.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INSTALMENT_TYPE")
public class InstalmentType {
    @Id
    @Column(name = "INSTALMENT_TYPE_ID", nullable = false)
    private Long id;

    @Size(max = 60)
    @NotNull
    @Column(name = "INSTALMENT_TYPE_NAME", nullable = false, length = 60)
    private String instalmentTypeName;

    @NotNull
    @Column(name = "FIRST_PAY", nullable = false)
    private Integer firstPay;

    @NotNull
    @Column(name = "STATE", nullable = false)
    private Character state;

    @NotNull
    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Size(max = 120)
    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "FEE_PERCENT")
    private Integer feePercent;

    


}