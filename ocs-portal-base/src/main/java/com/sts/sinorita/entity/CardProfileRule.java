package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "CARD_PROFILE_RULE")
public class CardProfileRule {
    @Id
    @Column(name = "CARD_PROFILE_RULE_ID", nullable = false)
    private Integer id;

    @Column(name = "ACCT_RES_ID")
    private Integer acctResId;

    @Size(max = 30)
    @NotNull
    @Column(name = "STD_CODE", nullable = false, length = 30)
    private String stdCode;

    @NotNull
    @Column(name = "NOMINAL_AMOUNT", nullable = false)
    private Long nominalAmount;

    @Column(name = "PROLONG")
    private Long prolong;

    @Column(name = "TAX")
    private Long tax;

    @Column(name = "PROLONG_POLICY")
    private Character prolongPolicy;

    @Column(name = "SP_ID")
    private Integer spId;

    @NotNull
    @ColumnDefault("SYSDATE")
    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @NotNull
    @ColumnDefault("-1")
    @Column(name = "SUBS_PLAN_ID", nullable = false)
    private Integer subsPlanId;

    @Column(name = "PRIORITY")
    private Integer priority;

    @ColumnDefault("'N'")
    @Column(name = "SP_SHARE")
    private Character spShare;

}