package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.AccNbrHisId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ACC_NBR_HIS")
public class AccNbrHis implements Serializable {
    @EmbeddedId
    private AccNbrHisId id;

    @Column(name = "PREFIX", nullable = false, length = 60)
    private String prefix;

    @Column(name = "ACC_NBR", nullable = false, length = 60)
    private String accNbr;

    @Column(name = "STAFF_ID")
    private Long staffId;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "ACC_NBR_CLASS_ID")
    private Integer accNbrClassId;

    @Column(name = "ACC_NBR_TYPE_ID")
    private Integer accNbrTypeId;

    @Column(name = "ACC_NBR_STATE", nullable = false, length = 1)
    private String accNbrState;

    @Column(name = "ACC_NBR_RULE_TYPE")
    private Long accNbrRuleType;

    @Column(name = "NE_INFO", length = 255)
    private String neInfo;

    @Column(name = "HLR_ID")
    private Long hlrId;

    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "NBR_CLASS_JUDGE_ID")
    private Long nbrClassJudgeId;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDate stateDate;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "PPS_PWD", length = 120)
    private String ppsPwd;

    @Column(name = "PRE_CHARGING")
    private Long preCharging;

    @Column(name = "PEER_OPERATOR_CODE", length = 60)
    private String peerOperatorCode;

    @Column(name = "NP_AUTH_CODE", length = 60)
    private String npAuthCode;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "PARTY_TYPE", length = 1)
    private String partyType;

    @Column(name = "PARTY_CODE", length = 60)
    private String partyCode;
}
