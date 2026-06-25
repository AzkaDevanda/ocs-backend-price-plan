package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ALL_BC_MEMBER", schema = "STS")
public class AllBcMember {

    @Id
    @Column(name = "BC_MEMBER_ID", nullable = false)
    private Long bcMemberId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "ACCT_ID")
    private Long acctId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "BC_MEMBER_TYPE_ID", length = 1)
    private String bcMemberTypeId;

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "STATE", length = 1)
    private String state;
}
