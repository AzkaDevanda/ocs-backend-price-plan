package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PRICE")
@SequenceGenerator(name = "price_seq", sequenceName = "PRICE_ID_SEQ", allocationSize = 1)
public class Price implements Serializable {
    @Id
    @Column(name = "PRICE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_seq")
    private Long id;

    @Column(name = "PRICE_NAME", nullable = false)
    private String priceName;

    @Column(name = "VALUE", nullable = false)
    private Long value;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

    @Column(name = "PRICE_VER_ID")
    private Integer priceVerId;

    @Column(name = "ACCT_ITEM_TYPE_ID")
    private Integer acctItemTypeId;

    @Column(name = "RUM", nullable = false)
    private Long rum = 1L;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "RATE_PRECISION", nullable = false)
    private Character ratePrecision = '0';

    @Column(name = "CALC_PRECISION", nullable = false)
    private Character calcPrecision = '0';

    @Column(name = "CREDIT_LIMIT")
    private Long creditLimit;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PAY_INDICATOR", length = 30)
    private String payIndicator;

    @Column(name = "CALC_DIS_AIT_ID")
    private Integer calcDisAitId;

    @ColumnDefault("'N'")
    @Column(name = "TEMPLATE_FLAG")
    private Character templateFlag;

    @Column(name = "SRC_PRICE_ID")
    private Integer srcPriceId;

    @Column(name = "PRICE_TYPE")
    private Character priceType;

    @Column(name = "PARENT_PRICE_ID")
    private Integer parentPriceId;

    @Column(name = "SP_ID")
    private Integer spId;

    @ColumnDefault("'N'")
    @Column(name = "SHARE_FLAG")
    private Character shareFlag;

    @ColumnDefault("'N'")
    @Column(name = "PRICE_EXIT")
    private Character priceExit;

    @Column(name = "ACCT_ITEM_TYPE_ID_PARAM")
    private Integer acctItemTypeIdParam;

    @Column(name = "INNER_PRIORITY")
    private Integer innerPriority;

    @Column(name = "LOOP_TYPE")
    private Character loopType;

    @Column(name = "TAX_INCLUSIVE")
    private Character taxInclusive;

    @Column(name = "RUMSTEP")
    private Long rumstep;
}