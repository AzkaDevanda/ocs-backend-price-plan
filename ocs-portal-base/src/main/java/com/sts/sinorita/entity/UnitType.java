package com.sts.sinorita.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UNIT_TYPE", schema = "STS")
public class UnitType {

    @Id
    @Column(name = "UNIT_TYPE_ID", nullable = false)
    private Integer id;

    @Column(name = "UNIT_TYPE_NAME",nullable = false)
    private String unitTypeName;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "UNIT_CODE",nullable = false)
    private String unitCode;

    @Column(name = "STATE")
    private Character state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "TRAFFIC_TYPE")
    private Integer trafficType;

}
