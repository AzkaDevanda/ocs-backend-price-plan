package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "PRICE_TAX")
public class PriceTax {
    @Id
    @Column(name = "PRICE_ID")
    private Integer priceId;

    @Column(name = "TAX_ID")
    private Integer taxId;

    @Column(name = "SP_ID")
    private Integer spId;

}
