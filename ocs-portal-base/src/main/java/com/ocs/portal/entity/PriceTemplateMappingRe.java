package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRICE_TEMPLATE_MAPPING_RE")
public class PriceTemplateMappingRe implements Serializable {

    @Id
    @Column(name = "MAPPING_ID", nullable = false)
    private Integer id;

    @Column(name = "RE_ID")
    private Integer reId;

    @Column(name = "PRICE_ID")
    private Long priceId;

    @Column(name = "SP_ID")
    private Integer spId;

}
