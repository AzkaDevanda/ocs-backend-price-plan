package com.ocs.portal.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BFM_TS_TASK", schema = "STS")
public class BfmTsTask {

    @Id
    @Column(name = "TASK_ID")
    private Long taskId;

    @Column(name = "CALL_TIME", nullable = false)
    private LocalDateTime callTime;

    @Column(name = "BUSINESS_KEY", length = 255)
    private String businessKey;

    @Column(name = "CURR_STATE", nullable = false, length = 1)
    private String currState;

    @Column(name = "PART_ID", nullable = false)
    private Long partId;
}
