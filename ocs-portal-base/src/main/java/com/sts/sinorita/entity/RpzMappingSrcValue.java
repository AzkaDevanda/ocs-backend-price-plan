package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RPZ_MAPPING_SRC_VALUE")
public class RpzMappingSrcValue {

    @Id
    @Column(name = "MAPPING_SRC_VALUE", nullable = false)
    private String mappingSrcValue;

}
