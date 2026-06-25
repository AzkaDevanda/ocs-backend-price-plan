package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "BFM_JOB")
@Data
public class Job {

    @Id
    @Column(name = "JOB_ID", nullable = false)
    private Long jobId;

    @Column(name = "JOB_NAME", length = 255)
    private String jobName;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDate stateDate;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "JOB_CODE", length = 255)
    private String jobCode;

    @Column(name = "PAR_JOB_ID")
    private Long parJobId;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;
}
