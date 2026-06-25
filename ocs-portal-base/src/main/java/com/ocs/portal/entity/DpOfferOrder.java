package com.ocs.portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "DP_OFFER_ORDER", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DpOfferOrder implements Serializable {
    @Id
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dp_order_id_seq")
//  @SequenceGenerator(name = "dp_order_id_seq", sequenceName = "DP_ORDER_ID_SEQ", allocationSize = 1)
    @Column(name = "DP_ORDER_ID", nullable = false)
    private Long dpOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "OFFER_INST_ID")
    private Long offerInstId;

    @Column(name = "OFFER_ID", nullable = false)
    private Long offerId;

    @Column(name = "OFFER_PKG_ID")
    private Long offerPkgId;

    @Column(name = "OLD_OFFER_PKG_ID")
    private Long oldOfferPkgId;

    @Column(name = "EFF_TYPE", length = 1)
    private String effType;

    @Column(name = "ABS_EFF_DATE")
    private LocalDateTime absEffDate;

    @Column(name = "ABS_EXP_DATE")
    private LocalDateTime absExpDate;

    @Column(name = "REL_EFF_OFFSET", precision = 9, scale = 0)
    private BigDecimal relEffOffset;

    @Column(name = "REL_EFF_UNIT", length = 1)
    private String relEffUnit;

    @Column(name = "REL_EXP_OFFSET", precision = 9, scale = 0)
    private BigDecimal relExpOffset;

    @Column(name = "REL_EXP_UNIT", length = 1)
    private String relExpUnit;

    @Column(name = "OPERATION_TYPE", length = 1, nullable = false)
    private String operationType;

    @Column(name = "AGM_EFF_DATE")
    private LocalDate agmEffDate;

    @Column(name = "AGM_EXP_DATE")
    private LocalDate agmExpDate;

    @Column(name = "REL_AGM_UNIT", length = 1)
    private String relAgmUnit;

    @Column(name = "REL_AGM_OFFSET", precision = 9, scale = 0)
    private BigDecimal relAgmOffset;

    @Column(name = "OLD_AGM_EXP_DATE")
    private LocalDate oldAgmExpDate;

    @Column(name = "OLD_EFF_DATE")
    private LocalDate oldEffDate;

    @Column(name = "OLD_EXP_DATE")
    private LocalDateTime oldExpDate;

    @Column(name = "SP_ID", precision = 6, scale = 0)
    private BigDecimal spId;

    @NotNull
    @Column(name = "PART_ID", nullable = false, updatable = false)
    private BigDecimal partId;

//    @Column(name = "PART_ID", precision = 6, scale = 0, nullable = false)
//    private BigDecimal partId;

    @Column(name = "BILLING_START_DATE")
    private LocalDate billingStartDate;

    @Transient
    private List<DpOfferOrderAttr> dpOfferOrderAttrList;


    @Transient
    private Long subsId;

    @PrePersist
    public void prePersist() {

        if (partId == null) {
            partId = BigDecimal.valueOf(LocalDate.now().getMonthValue());
        }

    }
}