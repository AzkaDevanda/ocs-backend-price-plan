package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SCATTER_SUBS_CATG")
public class ScatterSubsCatg implements Serializable {
    @Id
    @Column(name = "SCATTER_SUBS_CATG_ID", nullable = false)
    private Long scatterSubsCatgId;

    @Column(name = "SUBS_CATG_ID", nullable = false)
    private Long subsCatgId;

    @Column(name = "SUBS_ID", nullable = false)
    private Long subsId;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "PARTY_TYPE", nullable = false, length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 60)
    private String partyCode;

    @Column(name = "SP_ID")
    private Long spId;
}
