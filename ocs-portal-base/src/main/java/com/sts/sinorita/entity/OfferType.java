package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OFFER_TYPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferType {
  @Id
  @Column(name = "OFFER_TYPE", length = 1, nullable = false)
  private Character offerType;

  @Column(name = "OFFER_TYPE_NAME", length = 60, nullable = false)
  private String offerTypeName;

  @Column(name = "COMMENTS", length = 600)
  private String comments;
}
