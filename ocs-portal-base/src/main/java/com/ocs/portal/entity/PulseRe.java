package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PULSE_RE")
public class PulseRe implements Serializable {

  @Id
  @Column(name = "PULSE_ID", nullable = false)
  private Integer id;

  @Column(name = "RE_ID", nullable = false)
  private Integer reId;

  @Column(name = "SP_ID")
  private Integer spId;
}
