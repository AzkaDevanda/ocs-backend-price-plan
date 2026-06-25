package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SUBS_RE_EVENT", schema = "STS", uniqueConstraints = {
    @UniqueConstraint(name = "AK_KEY_2_SUBS_RE_", columnNames = { "SUBS_EVENT_ID", "OFFER_ID", "SP_ID" })
}, indexes = {
    @Index(name = "PK_SUBS_RE_EVENT", columnList = "RE_ID", unique = true),
    @Index(name = "AK_KEY_2_SUBS_RE_", columnList = "SUBS_EVENT_ID, OFFER_ID, SP_ID", unique = true)
})
public class SubsReEvent {

  @Id
  @Column(name = "RE_ID", nullable = false)
  private Long reId;

  @Column(name = "SUBS_EVENT_ID", nullable = false)
  private Long subsEventId;

  @Column(name = "OFFER_ID")
  private Long offerId;

  @Column(name = "SP_ID")
  private Long spId;
}