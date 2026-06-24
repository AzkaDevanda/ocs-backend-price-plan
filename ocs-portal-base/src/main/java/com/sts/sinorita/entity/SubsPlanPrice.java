package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "SUBS_PLAN_PRICE")
public class SubsPlanPrice {
    @EmbeddedId
    private SubsPlanPriceId id;

    @Column(name = "GOODS_SALE_AMOUNT")
    private BigDecimal goodsSaleAmount;

    @Column(name = "GOODS_DISCOUNT_AMOUNT")
    private BigDecimal goodsDiscountAmount;

    @Column(name = "RENT_LIST_PRICE")
    private BigDecimal rentListPrice;

    @Column(name = "TOTAL_REBATE_AMOUNT")
    private BigDecimal totalRebateAmount;

    @Column(name = "REBATE_AMOUNT")
    private BigDecimal rebateAmount;

    @Column(name = "REBATE_COUNT")
    private Integer rebateCount;

    @Column(name = "PENALTY")
    private BigDecimal penalty;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "SP_ID")
    private Integer spId;
}
