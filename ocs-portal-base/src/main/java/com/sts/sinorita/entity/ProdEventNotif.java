package com.sts.sinorita.entity;

import com.sts.sinorita.entity.embeddable.ProdEventNotifId;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "PROD_EVENT_NOTIF", schema = "STS")
public class ProdEventNotif {
  @EmbeddedId
  private ProdEventNotifId id;

  @Column(name = "SP_ID")
  private Long spId;
}