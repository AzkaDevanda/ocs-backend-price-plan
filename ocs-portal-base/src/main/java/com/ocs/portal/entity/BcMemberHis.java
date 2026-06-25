package com.ocs.portal.entity;

import com.ocs.portal.entity.embeddable.BcMemberHisId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BC_MEMBER_HIS", schema = "STS")
public class BcMemberHis {

    @EmbeddedId
    private BcMemberHisId id;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "BC_MEMBER_TYPE_ID", length = 1)
    private String bcMemberTypeId;

    @Column(name = "BS_TEMPLATE_ID")
    private Long bsTemplateId;

    @Column(name = "CEIL_LIMIT")
    private Long ceilLimit;

    @Column(name = "SHORT_NBR", length = 30)
    private String shortNbr;

    @Column(name = "STATE", length = 1)
    private String state;

    @Column(name = "STATE_DATE")
    private LocalDateTime stateDate;

    @Column(name = "EFF_DATE")
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "REC_EFF_DATE")
    private LocalDateTime recEffDate;

    @Column(name = "PARTY_CODE", length = 30)
    private String partyCode;

    @Column(name = "PARTY_TYPE", length = 3)
    private String partyType;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "REC_EXP_DATE")
    private LocalDateTime recExpDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "MEMBER_PREFIX", length = 20)
    private String memberPrefix;

    @Column(name = "MEMBER_ACC_NBR", length = 60)
    private String memberAccNbr;

    @Column(name = "ACC_NBR_TYPE", length = 1)
    private String accNbrType;

    @Column(name = "EXT_ATTR")
    private String extAttr;
}
