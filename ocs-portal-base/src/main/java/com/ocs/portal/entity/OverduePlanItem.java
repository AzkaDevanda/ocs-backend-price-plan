package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "OVERDUE_PLAN_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverduePlanItem implements Serializable {
    @Id
    @Column(name = "ACCT_ITEM_TYPE_ID", nullable = false)
    private Integer acctItemTypeId;

    @Column(name = "OVERDUE_PLAN_ID")
    private Integer overduePlanId;

    @Column(name = "SP_ID")
    private Integer spId;
}
