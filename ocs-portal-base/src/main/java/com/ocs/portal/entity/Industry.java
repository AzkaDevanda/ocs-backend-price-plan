package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INDUSTRY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Industry {
    @Id
    @Column(name = "INDUSTRY_ID", nullable = false)
    private Integer industryId;

    @Column(name = "INDUSTRY_NAME", nullable = false, length = 255)
    private String industryName;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "INDUSTRY_CODE", length = 60)
    private String industryCode;
}
