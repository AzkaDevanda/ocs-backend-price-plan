package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BAL_ACCT_ITEM_TYPE")
public class BalAcctItemType {
  @EmbeddedId
  private BalAcctItemTypeId id;

  @Column(name = "SP_ID")
  private Integer spId;

}