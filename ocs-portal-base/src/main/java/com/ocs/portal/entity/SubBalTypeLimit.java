package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "SUB_BAL_TYPE_LIMIT")
public class SubBalTypeLimit implements Serializable {

    @EmbeddedId
    private SubBalTypeLimitId id;

    @Column(name = "SP_ID")
    private Integer spId;

}