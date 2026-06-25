package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRICE_CATALOG_ELEMENT")
public class PriceCatalogElement {
    @Id
    @Column(name = "CATALOG_ID")
    private Integer id;

    @Column(name = "PRICE_ID")
    private Long priceId;

    @Column(name = "SP_ID")
    private Integer spId;
}
