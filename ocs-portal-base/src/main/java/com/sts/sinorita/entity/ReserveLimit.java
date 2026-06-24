package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.ReserveLimitId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "RESERVE_LIMIT")
public class ReserveLimit implements Serializable {
    @EmbeddedId
    private ReserveLimitId id;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "BAL_LIMIT")
    private Long balLimit;
}
