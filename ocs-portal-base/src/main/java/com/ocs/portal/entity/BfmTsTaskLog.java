package com.ocs.portal.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BFM_TS_TASK_LOG", schema = "STS")
public class BfmTsTaskLog {

    @Id
    @Column(name = "LOG_ID")
    private Long logId;

    @Column(name = "LOG_TIME", nullable = false)
    private LocalDateTime logTime;

    @Column(name = "TASK_ID", nullable = false)
    private Long taskId;

    @Column(name = "TASK_STATE", nullable = false, length = 1)
    private String taskState;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "PART_ID")
    private Long partId;
}
