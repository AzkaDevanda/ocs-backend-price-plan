package com.sts.sinorita.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ServiceRuleId implements Serializable {
  @Column(name = "SERVICE_RULE_TYPE", nullable = false, precision = 3)
  private Integer serviceRuleType;

  @Column(name = "SERV_TYPE", nullable = false, length = 255)
  private String servType;

  @Column(name = "SUBS_EVENT_ID", nullable = false, length = 255)
  private String subsEventId;

  @Column(name = "OFFER_ID", nullable = false, length = 255)
  private String offerId;
}