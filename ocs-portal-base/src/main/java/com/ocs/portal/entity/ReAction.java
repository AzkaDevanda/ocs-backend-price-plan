package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "RE_ACTION")
public class ReAction implements Serializable {

    @Id
    @Column(name = "RE_ACTION_ID", nullable = false)
    private Integer reActionId;

    @Column(name = "RE_ACTION_CODE", nullable = false, length = 60)
    private String reActionCode;

    @Column(name = "RE_ACTION_NAME", nullable = false, length = 60)
    private String reActionName;

    @Column(name = "COMMENTS", length = 255)
    private String comments;

    @Column(name = "STATE", nullable = false, length = 1)
    private Character state;

    @Column(name = "SP_ID")
    private Integer spId;
}
