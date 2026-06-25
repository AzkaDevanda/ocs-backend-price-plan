package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "RATE_PLAN_CATALOG_ELEMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatePlanCatalogElement implements Serializable {

    @Id
    @Column(name = "CATALOG_ID")
    private Integer id;

    @Column(name = "RATE_PLAN_ID")
    private Integer ratePlanId;

    @Column(name = "SP_ID")
    private Integer spId;

}
