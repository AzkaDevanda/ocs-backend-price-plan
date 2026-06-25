package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MAPPING_DES_TYPE_MASTER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappingDesTypeMaster {

    @Id
    @Column(name = "MAPPING_DES_TYPE", nullable = false)
    private Character mappingDesType;

    @Column(name = "MAPPING_DES_TYPE_NAME", nullable = false)
    private String mappingDesTypeName;

}
