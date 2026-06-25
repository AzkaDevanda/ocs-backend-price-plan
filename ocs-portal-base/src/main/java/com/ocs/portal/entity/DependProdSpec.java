package com.ocs.portal.entity;

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
@Table(name = "DEPEND_PROD_SPEC")
public class DependProdSpec {
    @Id
    @Column(name = "DEPEND_PROD_SPEC_ID", nullable = false)
    private Long dependProdSpecId;

    @Column(name = "SERV_TYPE")
    private Integer servType;

    @Column(name = "IS_PACKAGE")
    private String isPackage;

    @Column(name = "NETWORK_TYPE")
    private String networkType;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "GROUP_TYPE")
    private String groupType;

    @Column(name = "UPPER_LIMIT")
    private Integer upperLimit;

    @Column(name = "LOWER_LIMIT")
    private Integer lowerLimit;

}
