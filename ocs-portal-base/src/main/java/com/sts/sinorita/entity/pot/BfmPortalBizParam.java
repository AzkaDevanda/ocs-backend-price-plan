package com.sts.sinorita.entity.pot;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BFM_PORTAL_BIZ_PARAM", schema = "POT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BfmPortalBizParam {
  @Id
  @Column(name = "PARAM_ID")
  private Long paramId;

  @Column(name = "PARAM_NAME", length = 120)
  private String paramName;

  @Column(name = "PORTAL_ID")
  private Long portalId;

  @Column(name = "SERVICE_NAME", length = 64)
  private String serviceName;

  @Column(name = "SERVICE_INPUT", length = 4000)
  private String serviceInput;

  @Column(name = "SERVICE_OUTPUT", length = 20)
  private String serviceOutput;

  @Column(name = "COMMENTS", length = 120)
  private String comments;
}
