package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "EXCHANGE_RATE")
public class ExchangeRate {
    @EmbeddedId
    private ExchangeRateId id;

    @NotNull
    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @NotNull
    @Column(name = "SRC_RATE", nullable = false)
    private Long srcRate;

    @NotNull
    @Column(name = "OBJ_RATE", nullable = false)
    private Long objRate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PRECISION")
    private Long precision;

    @NotNull
    @ColumnDefault("'B'")
    @Column(name = "RATE_FLAG", nullable = false)
    private String rateFlag;

}