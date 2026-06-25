package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "BFM_JOB_ROLE", schema = "POT")
@Data
public class JobRole {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_BFM_JOB_ROLE_ID_SEQ_seq")
        @SequenceGenerator(name = "T_BFM_JOB_ROLE_ID_SEQ_seq", sequenceName = "T_BFM_JOB_ROLE_ID_SEQ", allocationSize = 1)
        @Column(name = "JOB_ROLE_ID", nullable = false)
        private Long jobRoleId;

        @Column(name = "JOB_ID")
        private Long jobId;

        @Column(name = "ROLE_ID")
        private Long roleId;

        @Column(name = "STATE", length = 1)
        private String state;

        @Column(name = "STATE_DATE")
        private LocalDate stateDate;

        @Column(name = "SP_ID")
        private Long spId;

        @Column(name = "CREATED_DATE")
        private LocalDate createdDate;

        @Column(name = "UPDATE_DATE")
        private LocalDate updateDate;

}
