package com.ocs.portal.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BFM_AREA", schema = "POT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BfmArea {
    @Id
    @Column(name = "AREA_ID", nullable = false)
    private Integer areaId;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "AREA_NAME", length = 60, nullable = false)
    private String areaName;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "AREA_CODE", length = 60)
    private String areaCode;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "AREA_LEVEL")
    private Integer areaLevel;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    @Column(name = "FULL_AREA_NAME", length = 128)
    private String fullAreaName;

    @Column(name = "DESCRIPTION", length = 2048)
    private String description;

    @Column(name = "ATTACHMENT", length = 2048)
    private String attachment;
}
