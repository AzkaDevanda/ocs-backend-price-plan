package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "BFM_PARAMS", schema = "STS")
public class BfmParams implements Serializable {

    @Id
    @Column(name = "PARAM", nullable = false)
    private Long param;

    @Column(name = "PARAM_NAME", nullable = false, length = 60)
    private String paramName;

    @Column(name = "CURRENT_VALUE", length = 4000)
    private String currentValue;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "MASK", nullable = false, length = 60)
    private String mask;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "PARAM_TYPE", length = 8)
    private String paramType = "T"; // default value, handled in DB

}
