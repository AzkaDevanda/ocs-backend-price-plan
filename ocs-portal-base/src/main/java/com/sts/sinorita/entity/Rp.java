package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "RP")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rp implements Serializable {

    // FK Price
    @Id
    @Column(name = "RP_ID", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "RULE_SCRIPT")
    private String ruleScript;

    @Column(name = "RULE_COMMENTS", length = 4000)
    private String ruleComments;

    @Column(name = "PARAM", length = 4000)
    private String param;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "SCRIPT_PAGE")
    private byte[] scriptPage;

    @Column(name = "SCRIPT_TEMPLET_ID")
    private Integer scriptTempletId;

    @Column(name = "CONFIG_TYPE")
    private Character configType;

}