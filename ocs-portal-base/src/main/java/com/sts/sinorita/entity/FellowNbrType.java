package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "FELLOW_NBR_TYPE", schema = "STS")
public class FellowNbrType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FELLOW_NBR_TYPE_ID", nullable = false)
    private Long fellowNbrTypeId;

    @Column(name = "FELLOW_NBR_TYPE_NAME", nullable = false, length = 60)
    private String fellowNbrTypeName;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;

    @Column(name = "IS_FA", length = 1)
    private String isFa;

    @Column(name = "STD_CODE", length = 30)
    private String stdCode;

    @Column(name = "SERVICE_RULE")
    private Long serviceRule;

    @Column(name = "BIDIRECTIONAL", length = 1)
    private String bidirectional;

    @Column(name = "MEM_LIMIT")
    private Long memLimit;

    @Column(name = "SERV_TYPE", length = 30)
    private String servType;

    @Column(name = "IS_AUTO_PROVISION", length = 1)
    private String isAutoProvision;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "RULE_SCRIPT", length = 4000)
    private String ruleScript;

    @Column(name = "OFFER_CODE", length = 60)
    private String offerCode;

}
