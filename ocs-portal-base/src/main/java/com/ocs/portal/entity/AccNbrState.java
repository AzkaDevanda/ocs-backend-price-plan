package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "ACC_NBR_STATE")
public class AccNbrState implements Serializable {
    @Id
    @Column(name = "ACC_NBR_STATE", nullable = false, length = 1)
    private String accNbrState;

    @Column(name = "ACC_NBR_STATE_NAME", nullable = false, length = 60)
    private String accNbrStateName;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;
}
