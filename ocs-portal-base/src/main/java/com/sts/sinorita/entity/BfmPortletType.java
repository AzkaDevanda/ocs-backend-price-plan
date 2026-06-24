package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "BFM_PORTLET_TYPE", schema = "POT")
public class BfmPortletType {

    @Id
    @Column(name = "TYPE_ID", nullable = false)
    private Long typeId;

    @Column(name = "TYPE_NAME", nullable = false, length = 60)
    private String typeName;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "STATE", nullable = false, length = 1)
    private Character state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

}

