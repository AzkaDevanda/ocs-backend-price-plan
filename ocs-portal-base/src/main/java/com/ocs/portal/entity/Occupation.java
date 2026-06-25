package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "OCCUPATION")
public class Occupation {

    @Id
    @Column(name = "OCCUPATION_ID")
    private Long occupationId;

    @Column(name = "OCCUPATION_NAME")
    private String occupationName;

    @Column(name = "PARENT_OCCUPATION_ID")
    private Long parentOccupationId;

    @Column(name = "SP_ID")
    private Long spId;
}
