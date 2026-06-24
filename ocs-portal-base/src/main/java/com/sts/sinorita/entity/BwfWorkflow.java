package com.sts.sinorita.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BWF_WORKFLOW", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BwfWorkflow implements Serializable {
    @Id
    @Column(name = "WORKFLOW_ID", nullable = false)
    private Integer id;

    @Column(name = "WORKFLOW_NAME", nullable = false, length = 60)
    private String workflowName;

    @Column(name = "COMMENTS", length = 200)
    private String comments;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "WORKFLOW_TYPE", nullable = false)
    private Character workflowType;

}
