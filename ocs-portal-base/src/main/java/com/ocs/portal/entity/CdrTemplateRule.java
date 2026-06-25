package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CDR_TEMPLATE_RULE")
public class CdrTemplateRule {
  @Id
  @Column(name = "CDR_TEMPLATE_RULE_ID", nullable = false)
  private Integer id;

  @Column(name = "CONTACT_CHANNEL_ID")
  private Integer contactChannelId;

  @Column(name = "PAYMENT_METHOD")
  private Short paymentMethod;

  @NotNull
  @Column(name = "EVENT_FORMAT_ID", nullable = false)
  private Integer eventFormatId;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "SUBS_EVENT_ID")
  private Integer subsEventId;

}