package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RP_EB_MAPPING_PRICE")
public class ReEbMappingPrice {

    @Id
    @Column(name = "RP_EB_MAPPING_PRICE_ID")
    private Integer id;

    @Column(name = "EB_PRICE_ID")
    private Long ebPriceId;

    @Column(name = "RP_MAPPING_BRANCH_ID")
    private Integer rpMappingBranchId;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE_TYPE")
    private Character priceType;

    @Column(name = "PRICE_UNIT")
    private Character priceUnit;

    @Column(name = "VALUE")
    private Long value;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "DAY_CHARGED_RULE_ID")
    private Integer dayChargedRuleId;

}
