package com.ocs.portal.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BILL_SHOCK_RULE", schema = "STS")
public class BillShockRule {
    @Id
    @Column(name = "BILL_SHOCK_RULE_ID", nullable = false)
    private Long id;

    @Column(name = "RULE_NAME")
    private String ruleName;

    @Column(name = "USABLE_ACCT_RES_ID_LIST")
    private Long usableAcctResIdList;

    @Column(name = "SP_ID")
    private Integer spId;
}
