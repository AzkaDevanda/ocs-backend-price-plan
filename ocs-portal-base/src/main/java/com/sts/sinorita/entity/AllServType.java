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
@Table(name = "ALL_SERV_TYPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllServType implements Serializable {

    @Id
    @Column(name = "SERV_TYPE", nullable = false)
    private Integer servType;

    @Column(name = "NETWORK_TYPE")
    private Character networkType;

    @Column(name = "SERV_TYPE_NAME")
    private String servTypeName;

    @Column(name = "CATG_TYPE")
    private Character catgType;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "PAID_FLAG")
    private Character paidFlag;

    @Column(name = "STD_CODE")
    private String stdCode;

}
