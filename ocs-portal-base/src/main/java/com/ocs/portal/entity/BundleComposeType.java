package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BUNDLE_COMPOSE_TYPE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BundleComposeType {
  @Id
  @Column(name = "BUNDLE_COMPOSE_TYPE", nullable = false, length = 1)
  private String bundleComposeType;

  @Column(name = "BUNDLE_COMPOSE_TYPE_NAME", nullable = false, length = 60)
  private String bundleComposeTypeName;

  @Column(name = "COMMENTS", length = 200)
  private String comments;
}
