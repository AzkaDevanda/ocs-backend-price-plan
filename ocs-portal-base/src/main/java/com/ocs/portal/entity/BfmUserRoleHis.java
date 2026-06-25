package com.ocs.portal.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "BFM_USER_ROLE_HIS", schema = "POT")
public class BfmUserRoleHis {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_BFM_USER_ROLE_HIS_ID_SEQ_generator")
    @SequenceGenerator(name = "T_BFM_USER_ROLE_HIS_ID_SEQ_generator", sequenceName = "T_BFM_USER_ROLE_HIS_ID_SEQ", allocationSize = 1)
    @Column(name = "USER_ROLE_HIS_ID", nullable = false)
    private Long userRoleHisId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;

    @Column(name = "SP_ID", nullable = false)
    private Long spId;

    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ROLE_TIMES")
    private Integer userRoleTimes;

    @Column(name = "STAFF_ROLE_TIMES")
    private Integer staffRoleTimes;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    @Column(name = "OPERATOR_TYPE", length = 1)
    private String operatorType;

}
