package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.ReserveLimitProdSpecId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "RESERVE_LIMIT_PROD_SPEC")
public class ReserveLimitProdSpec implements Serializable {
    @EmbeddedId
    private ReserveLimitProdSpecId id;

    @Column(name = "BAL_LIMIT")
    private Long balLimit;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    @Column(name = "SP_ID")
    private Integer spId;
}
