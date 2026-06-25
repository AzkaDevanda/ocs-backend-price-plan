package com.ocs.portal.entity;

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
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DEF_RE_ATTR")
public class DefReAttr implements Serializable {
    @Id
    @Column(name = "RE_ATTR", nullable = false)
    private Integer reAttr;

    @Column(name = "DEF_RE_ATTR", nullable = false)
    private Integer defReAttr;

    @Column(name = "SP_ID")
    private Integer spId;
}
