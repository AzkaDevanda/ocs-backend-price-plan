package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DISPUTE_ITEM")
public class DisputeItem {
  @Id
  @Column(name = "ACCT_ITEM_ID", nullable = false)
  private Long id;

  @Column(name = "SP_ID")
  private Integer spId;

}