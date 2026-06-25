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
@Table(name = "TITLE", schema = "STS")
public class Title {

    @Id
    @Column(name = "TITLE_ID", nullable = false)
    private Integer titleId;

    @Column(name = "TITLE_NAME", nullable = false, length = 60)
    private String titleName;

    @Column(name = "SP_ID")
    private Integer spId;
}
