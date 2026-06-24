package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRICE_PLAN_TYPE")
public class PricePlanType implements Serializable {

    @Id
    @Column(name = "PRICE_PLAN_TYPE", nullable = false)
    private Character id;

    @Column(name = "PRICE_PLAN_TYPE_NAME")
    private String pricePlanTypeName;

    @Column(name = "COMMENTS")
    private String comments;
}
