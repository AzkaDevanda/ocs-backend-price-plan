package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ACCT_ITEM_TYPE_BIND")
@IdClass(AcctItemTypeBind.class)
public class AcctItemTypeBind implements Serializable {
    @Id
    @Column(name = "ACCT_ITEM_TYPE_ID", nullable = false)
    private Integer acctItemTypeId;

    @Id
    @Column(name = "ACCT_ITEM_TYPE_BIND_TYPE", nullable = false, length = 1)
    private String acctItemTypeBindType;

    @Column(name = "BIND_ACCT_ITEM_TYPE_ID")
    private Integer bindAcctItemTypeId;

    @Column(name = "SP_ID")
    private Integer spId;

}
