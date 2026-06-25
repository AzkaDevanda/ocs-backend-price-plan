package com.ocs.portal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OL_DIAGRAM_LOG", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OlDiagramLog {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ol_diagram_log_seq")
  @SequenceGenerator(name = "ol_diagram_log_seq", sequenceName = "OL_DIAGRAM_LOG_SEQ", allocationSize = 1)
  @Column(name = "OL_DIAGRAM_LOG_ID", nullable = false)
  private Long olDiagramLogId;

  @Column(name = "NODE_ID", nullable = false)
  private Long nodeId;

  @Column(name = "OFFER_ID", nullable = false)
  private Long offerId;

  @Column(name = "OFFER_VER_ID")
  private Long offerVerId;

  @Column(name = "SEQ", nullable = false)
  private Integer seq;

  @Column(name = "SRC_OL_STATE", length = 1)
  private String srcOlState;

  @Column(name = "OBJ_OL_STATE", length = 1)
  private String objOlState;

  @Column(name = "OL_ACTION", length = 1)
  private String olAction;

  @Column(name = "CREATE_DATE")
  private LocalDateTime createDate;

  @Column(name = "COMMENTS", length = 120)
  private String comments;

  @Column(name = "PARTY_TYPE", length = 1)
  private String partyType;

  @Column(name = "PARTY_CODE", length = 60)
  private String partyCode;

  @Column(name = "SP_ID")
  private Long spId;
}
