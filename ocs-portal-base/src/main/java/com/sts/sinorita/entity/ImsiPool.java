package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "IMSI_POOL")
public class ImsiPool implements Serializable {

    @Id
    @Column(name = "IMSI", nullable = false, length = 60)
    private String imsi;

    @Column(name = "IMSI_STATE", nullable = false, length = 1)
    private String imsiState;
}
