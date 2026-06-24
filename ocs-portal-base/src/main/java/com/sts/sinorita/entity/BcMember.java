package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BC_MEMBER", schema = "STS")
@SequenceGenerator(
        name = "BC_MEMBER_SEQ",
        sequenceName = "STS.BC_MEMBER_ID_SEQ",
        allocationSize = 1
)
public class BcMember {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BC_MEMBER_SEQ")
    @Column(name = "BC_MEMBER_ID", nullable = false)
    private Long bcMemberId;

    @Column(name = "CUST_ID", nullable = false)
    private Long custId;

    @Column(name = "ACCT_ID", nullable = false)
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

    @Column(name = "STATE", nullable = false, length = 1)
    private String state;

    @Column(name = "STATE_DATE", nullable = false)
    private LocalDateTime stateDate;

    @Column(name = "EFF_DATE", nullable = false)
    private LocalDateTime effDate;

    @Column(name = "EXP_DATE")
    private LocalDateTime expDate;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "ACC_NBR_TYPE", length = 1)
    private String accNbrType;

    @Column(name = "MEMBER_PREFIX", length = 20)
    private String memberPrefix;

    @Column(name = "MEMBER_ACC_NBR", length = 60)
    private String memberAccNbr;

    @Column(name = "BC_ROLE_ID")
    private Long bcRoleId;

    @Column(name = "INDIVIDUAL_ACCT_ID")
    private Long individualAcctId;
}
