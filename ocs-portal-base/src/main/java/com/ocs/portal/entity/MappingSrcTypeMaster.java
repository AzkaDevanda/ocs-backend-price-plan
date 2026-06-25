package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MAPPING_SRC_TYPE_MASTER")
public class MappingSrcTypeMaster {

    @Id
    @Column(name = "MAPPING_SRC_TYPE", nullable = false)
    private Character mappingSrcType;

    @Column(name = "MAPPING_SRC_TYPE_NAME", nullable = false)
    private String mappingSrcTypeName;
}
