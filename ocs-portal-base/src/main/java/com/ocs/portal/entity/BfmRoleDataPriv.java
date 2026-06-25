package com.ocs.portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BFM_ROLE_DATA_PRIV", schema = "POT")
public class BfmRoleDataPriv {
  @Id
  @EmbeddedId
  private BfmRoleDataPrivId id;

  @Size(max = 4000)
  @NotNull
  @Column(name = "PRIV_VALUE", nullable = false, length = 4000)
  private String privValue;

  @Column(name = "SP_ID")
  private Integer spId;

  @Column(name = "OWNED_TYPE")
  private Boolean ownedType;

}