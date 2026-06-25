package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DS_TAG")
public class DsTag {
  @Id
  @Size(max = 255)
  @Column(name = "TAG", nullable = false)
  private String tag;

  @Size(max = 60)
  @NotNull
  @Column(name = "TAG_NAME", nullable = false, length = 60)
  private String tagName;

  @Size(max = 120)
  @Column(name = "COMMENTS", length = 120)
  private String comments;

}