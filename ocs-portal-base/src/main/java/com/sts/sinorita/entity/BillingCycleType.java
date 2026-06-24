package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BILLING_CYCLE_TYPE")
public class BillingCycleType implements Serializable {
    @Id
    @Column(name = "BILLING_CYCLE_TYPE_ID", nullable = false)
    private Integer billingCycleTypeId;

    @Column(name = "TIME_UNIT", nullable = false, length = 1)
    private Character timeUnit;

    @Column(name = "BILLING_CYCLE_TYPE_NAME", nullable = false, length = 60)
    private String billingCycleTypeName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "BEGIN_DATE", nullable = false)
    private Date beginDate;

    @Column(name = "DEBT_DATE", nullable = false)
    private Date debtDate;

    @Column(name = "OPERATOR", length = 1)
    private Character operator;

    @Column(name = "BILLING_CYCLE_TYPE_CODE", length = 60)
    private String billingCycleTypeCode;

    @Column(name = "RUN_DATE")
    private Date runDate;

    @Column(name = "POSTPAID", nullable = false, length = 1)
    private Character postpaid;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "CUST_TYPE", length = 1)
    private Character custType;

    @Column(name = "PROD_TYPE", length = 1)
    private Character prodType;

}
