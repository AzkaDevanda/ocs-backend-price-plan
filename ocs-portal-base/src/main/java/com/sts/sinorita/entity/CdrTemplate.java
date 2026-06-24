package com.sts.sinorita.entity;

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
@Table(name = "CDR_TEMPLATE")
public class CdrTemplate {
  @Id
  @Column(name = "CDR_TEMPLATE_ID", nullable = false)
  private Integer id;

  @Size(max = 255)
  @NotNull
  @Column(name = "CDR_TEMPLATE_NAME", nullable = false)
  private String cdrTemplateName;

  @Size(max = 4000)
  @NotNull
  @Column(name = "CDR_CONTENT", nullable = false, length = 4000)
  private String cdrContent;

  @Size(max = 4000)
  @Column(name = "COMMENTS", length = 4000)
  private String comments;

}