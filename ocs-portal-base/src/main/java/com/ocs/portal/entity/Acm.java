package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACM")
@SequenceGenerator(name = "acm_seq", sequenceName = "PRICE_ID_SEQ", allocationSize = 1)
public class Acm implements Serializable {

    // FK ke PriceVer
    @Id
    @Column(name = "PRICE_VER_ID", nullable = false)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acm_seq")
    private Integer id;

    @Column(name = "RE_ATTR")
    private Integer reAttr;

    @Column(name = "RESOURCE_ID")
    private Integer resourceId;

    @Column(name = "VALUE")
    private Integer value;

    @Column(name = "RUM")
    private Long rum;

    @Column(name = "SP_ID")
    private Integer spId;

    @ColumnDefault("'N'")
    @Column(name = "TEMPLATE_FLAG")
    private Character templateFlag;

    @Column(name = "PRICE_ID")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_seq")
    private Long priceId;

    @Column(name = "SRC_PRICE_ID")
    private Integer srcPriceId;

    @Column(name = "ACM_NAME")
    private String acmName;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "REF_VALUE_ID")
    private Integer refValueId;

    @ColumnDefault("'N'")
    @Column(name = "SHARE_FLAG")
    private Character shareFlag;

}