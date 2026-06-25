package com.ocs.portal.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BFM_TS_TASK_HANDLER", schema = "STS")
public class BfmTsTaskHandler {

    @Id
    @Column(name = "TASK_ID")
    private Long taskId;

    @Column(name = "CLASS_NAME", nullable = false, length = 128)
    private String className;

    @Lob
    @Column(name = "PARAM")
    private String param;

    @Column(name = "IS_NEW_TRANS", length = 1)
    private String isNewTrans;

    @Column(name = "PART_ID", nullable = false)
    private Long partId;
}
