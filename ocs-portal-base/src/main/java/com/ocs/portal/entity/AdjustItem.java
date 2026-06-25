package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ADJUST_ITEM")
public class AdjustItem {
  @Id
  @Column(name = "ACCT_ITEM_ID", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "ADJUST_ID", nullable = false)
  private Long adjustId;

  @Column(name = "REF_ACCT_ITEM_ID")
  private Long refAcctItemId;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "REF_BILL_ID")
  private Long refBillId;

  @Size(max = 3000)
  @Column(name = "COMMENTS", length = 3000)
  private String comments;

}