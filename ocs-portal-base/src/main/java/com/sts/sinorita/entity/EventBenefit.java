package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Table(name = "EVENT_BENEFIT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "event_benefit_seq", sequenceName = "STS.PRICE_ID_SEQ", allocationSize = 1)
public class EventBenefit implements Serializable {
    @Id
    @Column(name = "PRICE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_benefit_seq")
    private Long id;

    @Column(name = "PRICE_NAME", nullable = false)
    private String priceName;

    @Column(name = "VALUE")
    private Integer value;

    @Column(name = "SUB_BAL_TYPE_ID", nullable = false)
    private Integer subBalTypeId;

    @Column(name = "RE_ATTR", nullable = false)
    private Integer reAttr;

    @Column(name = "PRICE_VER_ID")
    private Integer priceVerId;

    @Column(name = "RUM", nullable = false)
    private Long rum;

    @Column(name = "RATE_PRECISION", nullable = false)
    private Integer ratePrecision;

    @Column(name = "CALC_PRECISION", nullable = false)
    private Integer calcPrecision;

    @Lob
    @Column(name = "RULE_SCRIPT")
    private String ruleScript;

    @Column(name = "RULE_COMMENTS", length = 4000)
    private String ruleComments;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "SCRIPT_PAGE")
    private byte[] scriptPage;

    @Column(name = "SCRIPT_TEMPLET_ID")
    private Integer scriptTempletId;

    @Column(name = "REPEAT_CNT")
    private Short repeatCnt;

    @ColumnDefault("'N'")
    @Column(name = "TEMPLATE_FLAG")
    private Character templateFlag;

    @Column(name = "SRC_PRICE_ID")
    private Integer srcPriceId;

    @Column(name = "COMMENTS")
    private String comments;

    @ColumnDefault("'N'")
    @Column(name = "SHARE_FLAG")
    private Character shareFlag;

    @Column(name = "CONFIG_TYPE")
    private Character configType;

    @Column(name = "PRIORITY")
    private Integer priority;
}