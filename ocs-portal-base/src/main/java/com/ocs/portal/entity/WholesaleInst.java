package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "WHOLESALE_INST", schema = "STS")
public class WholesaleInst implements Serializable {

  @Id
  @Column(name = "WHOLESALE_INST_ID", nullable = false)
  private Long wholesaleInstId;

  @Column(name = "WHOLESALE_ID")
  private Long wholesaleId;

  @Column(name = "ORDER_ITEM_ID")
  private Long orderItemId;

  @Column(name = "ACC_NBR_ID")
  private Long accNbrId;

  @Column(name = "SUBS_ID")
  private Long subsId;

  @Column(name = "STATE", length = 1, nullable = false)
  private Character state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDateTime stateDate;

  @Column(name = "COMMENTS", length = 3000)
  private String comments;

  @Column(name = "SIM_CARD_ID")
  private Long simCardId;

  @Column(name = "GOODS_ID")
  private Long goodsId;

  @Column(name = "SP_ID")
  private Integer spId;

  @Lob
  @Column(name = "EXT_ATTR")
  private String extAttr;

  @Column(name = "CUST_ID")
  private Long custId;

  @Column(name = "ACCT_ID")
  private Long acctId;

}
