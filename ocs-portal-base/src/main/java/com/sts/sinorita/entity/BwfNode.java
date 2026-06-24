package com.sts.sinorita.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BWF_NODE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BwfNode implements Serializable {
    @Id
    @Column(name = "NODE_ID", nullable = false)
    private Integer id;

    @Column(name = "WORKFLOW_ID", nullable = false)
    private Integer workflowId;

    @Column(name = "NODE_NAME", nullable = false, length = 60)
    private String nodeName;

    @Column(name = "FIRST_NODE", nullable = false)
    private Character firstNode;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "CP_SRC_NODE_ID")
    private Integer cpSrcNodeId;
}
