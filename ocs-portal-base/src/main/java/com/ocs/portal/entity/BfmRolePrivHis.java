package com.ocs.portal.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "BFM_ROLE_PRIV_HIS", schema = "POT")
public class BfmRolePrivHis {

    @Id
    @Column(name = "REC_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_BFM_JOB_ROLE_HIS_ID_SEQ_generator")
    @SequenceGenerator(name = "T_BFM_JOB_ROLE_HIS_ID_SEQ_generator", sequenceName = "T_BFM_JOB_ROLE_HIS_ID_SEQ", allocationSize = 1)
    private Long recId;

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "PRIV_ID")
    private Long privId;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PRIV_LEVEL", length = 1)
    private String privLevel;

    @Column(name = "IS_AUTHORIZED", length = 1)
    private String isAuthorized;

    @Column(name = "AUTO_OPEN_MENU", length = 1)
    private String autoOpenMenu;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    @Column(name = "OPERATOR_TYPE", length = 1)
    private String operatorType;

}
