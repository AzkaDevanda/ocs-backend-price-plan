package com.ocs.portal.entity;

import jakarta.persistence.*;
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

@Table(name = "PRICE_PLAN_PARAM")
public class PricePlanParam implements Serializable {

    @Id
    @Column(name = "price_plan_param_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "offer_ver_id", nullable = false)
    private Integer offerVerId;

    @Column(name = "param_type")
    private Character paramType;

    @Column(name = "simple_param_id")
    private Integer simple_param_id;

    @Column(name = "table_param_id")
    private Integer table_param_id;

    @Column(name = "sp_id")
    private Integer sp_id;

    @Column(name = "label_name")
    private String labelName;

    @Column(name = "visiable_flag")
    private Character visiableFlag;

}