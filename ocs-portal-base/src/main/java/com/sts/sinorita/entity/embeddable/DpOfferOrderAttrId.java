package com.sts.sinorita.entity.embeddable;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DpOfferOrderAttrId implements Serializable {
  private Long dpOrderId;
  private Long attrId;
}