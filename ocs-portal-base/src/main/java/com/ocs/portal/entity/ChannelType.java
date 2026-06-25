package com.ocs.portal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CHANNEL_TYPE")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ChannelType {
  @Id
  @Column(name = "CHANNEL_TYPE", nullable = false)
  private Integer channelType;

  @Column(name = "CHANNEL_TYPE_NAME", nullable = false, length = 60)
  private String channelTypeName;

  @Column(name = "COMMENTS", length = 120)
  private String comments;
}
