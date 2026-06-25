package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "ZONE_MAP_TYPE")
public class ZoneMapType implements Serializable {
    @Id
    @Column(name = "ZONE_MAP_TYPE", nullable = false)
    private Long id;

    @Column(name = "TYPE_NAME", nullable = false, length = 60)
    private String typeName;
}