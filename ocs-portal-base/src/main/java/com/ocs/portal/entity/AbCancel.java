package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

import com.ocs.portal.entity.embeddable.AbCancelId;

@Entity
@Table(name = "AB_CANCEL", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbCancel implements Serializable {

  @EmbeddedId
  private AbCancelId id; // composite key (acctBookId + balId)

  @Column(name = "MONEY_VALUE")
  private Long moneyValue;

  @Column(name = "SP_ID")
  private Long spId;

  @Column(name = "PART_ID", nullable = false)
  private Integer partId;
}