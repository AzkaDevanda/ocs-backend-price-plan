package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BC_MEMBER_ORDER", schema = "STS")
@SequenceGenerator(
        name = "BC_MEMBER_ORDER_SEQ",
        sequenceName = "STS.BC_MEMBER_ORDER_ID_SEQ",
        allocationSize = 1
)
public class BcMemberOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BC_MEMBER_ORDER_SEQ")
    @Column(name = "BC_MEMBER_ORDER_ID", nullable = false)
    private Long bcMemberOrderId;

    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long orderItemId;

    @Column(name = "CUST_ID", nullable = false)
    private Long custId;

    @Column(name = "ACCT_ID", nullable = false)
    private Long acctId;

    @Column(name = "SUBS_ID")
    private Long subsId;

    @Column(name = "BC_MEMBER_TYPE_ID", nullable = false, length = 1)
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

    @Column(name = "SP_ID")
    private Long spId;

    @Column(name = "OLD_CUST_ID")
    private Long oldCustId;

    @Column(name = "OLD_ACCT_ID")
    private Long oldAcctId;

    @Column(name = "COMMENTS", length = 4000)
    private String comments;

    @Column(name = "OPERATION_TYPE", nullable = false, length = 1)
    private String operationType;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    @Column(name = "ACC_NBR_TYPE", length = 1)
    private String accNbrType;
}
