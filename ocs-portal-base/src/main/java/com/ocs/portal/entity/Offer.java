package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@Table(name = "OFFER")
@NoArgsConstructor
@AllArgsConstructor
public class Offer implements Serializable {
    @Id
    @Column(name = "OFFER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_seq_generator")
    @SequenceGenerator(name = "offer_seq_generator", sequenceName = "OFFER_ID_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "OFFER_TYPE", length = 1)
    private Character offerType;

    @Column(name = "OFFER_NAME", nullable = false, unique = true, length = 255)
    private String offerName;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "OFFER_CODE", length = 60)
    private String offerCode;

    @Column(name = "SALE_LIST_PRICE")
    private Long saleListPrice;

    @Column(name = "RENT_LIST_PRICE")
    private Long rentListPrice;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDate effDate;

    @Column(name = "EXP_DATE")
    private LocalDate expDate;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Column(name = "STATE", nullable = false, length = 1)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "EFF_TYPE", length = 30)
    private String effType;

    @Column(name = "EXP_OFF")
    private Integer expOff;

    @Column(name = "TIME_UNIT", length = 1)
    private Character timeUnit;

    @Column(name = "AUTO_CONTINUE_FLAG", length = 1)
    private Character autoContinueFlag;

    @Column(name = "CYCLE_QUANTITY")
    private Integer cycleQuantity;

    @Column(name = "DUPLICATE_FLAG", length = 1)
    private Character duplicateFlag;

    @Column(name = "SP_ID")
    @Builder.Default
    private Integer spId = 0;

    @Column(name = "EXP_TIME_UNIT", length = 1)
    private Character expTimeUnit;

    @Column(name = "AGREEMENT_EFF_TYPE", length = 1)
    private Character agreementEffType;

    @Lob
    @Column(name = "SALES_RULE_SCRIPT")
    private String salesRuleScript;

    @Column(name = "SALE_PRICE_GST_TYPE", length = 1)
    private Character salePriceGstType;

    @Column(name = "RENT_PRICE_GST_TYPE", length = 1)
    private Character rentPriceGstType;

    @Column(name = "PROD_TYPE", length = 1)
    private Character prodType;

    @Column(name = "PUBLISH_STATE", length = 1)
    private Character publishState;

    @Column(name = "PUBLISH_STATE_DATE")
    private LocalDate publishStateDate;



//    private List<OfferAttr> offerAttrList;
//
//    private String defaultFlag;
//
//    private Long childOfferGroupId;

    public String getDefaultEffType() {
        String str = "B";
        if (StringUtils.isNotEmpty(this.effType))
            if (this.effType.indexOf("B") > -1) {
                str = "B";
            } else {
                String[] arrayOfString = StringUtils.split(this.effType, "|");
                if (arrayOfString != null && arrayOfString.length > 0)
                    str = arrayOfString[0];
            }
        return str;
    }

}