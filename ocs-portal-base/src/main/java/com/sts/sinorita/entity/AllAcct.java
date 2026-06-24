package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ALL_ACCT", schema = "STS", indexes = {
    @Index(name = "IDX_ALL_ACCT_CUST_ID", columnList = "CUST_ID")
})
@Getter
@Setter
public class AllAcct {

  @Id
  @Column(name = "ACCT_ID", nullable = false, precision = 12, scale = 0)
  private Long acctId;

  @Column(name = "PARENT_ACCT_ID", precision = 12, scale = 0)
  private Long parentAcctId;

  @Column(name = "CUST_ID", nullable = false, precision = 12, scale = 0)
  private Long custId;

  @Column(name = "ACCT_NBR", nullable = false, length = 60)
  private String acctNbr;

  @Column(name = "POSTPAID", nullable = false, length = 1)
  private String postpaid;

  @Column(name = "ROUTING_ID", precision = 6, scale = 0)
  private Long routingId;

  @Column(name = "SP_ID", precision = 6, scale = 0)
  private Long spId;

}
