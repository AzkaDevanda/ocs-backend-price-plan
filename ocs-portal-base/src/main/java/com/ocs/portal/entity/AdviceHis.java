package com.ocs.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ADVICE_HIS", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdviceHis {
  @Id
  @Column(name = "ADVICE_ID", nullable = false, precision = 12)
  private Long adviceId;

  @Column(name = "ADVICE_TYPE", nullable = false, precision = 6)
  private Long adviceType;

  @Column(name = "SUBS_ID", nullable = false, precision = 12)
  private Long subsId;

  @Column(name = "CUST_ID", precision = 12)
  private Long custId;

  @Column(name = "PREFIX", length = 60)
  private String prefix;

  @Column(name = "ACC_NBR", nullable = false, length = 60)
  private String accNbr;

  @Lob
  @Column(name = "MSG")
  private String msg;

  @Column(name = "MSG_PARAM", length = 4000)
  private String msgParam;

  @Column(name = "CREATED_DATE", nullable = false)
  private LocalDateTime createdDate;

  @Column(name = "STATE", nullable = false, length = 1)
  private String state;

  @Column(name = "STATE_DATE", nullable = false)
  private LocalDateTime stateDate;

  @Column(name = "COMMENTS", length = 4000)
  private String comments;

  @Column(name = "SENDER_PARAM", length = 4000)
  private String senderParam;

  @Column(name = "SENDED_DATE")
  private LocalDateTime sendedDate;

  @Column(name = "EXP_DATE")
  private LocalDateTime expDate;

  @Column(name = "DELAY_TIME", precision = 6)
  private Long delayTime;

  @Column(name = "MESSAGE_ID", length = 120)
  private String messageId;

  @Column(name = "TIMES", precision = 4)
  private Integer times;

  @Column(name = "TIME_INTERVAL", precision = 6)
  private Long timeInterval;

  @Column(name = "PART_ID", nullable = false, precision = 6)
  private Integer partId;

  @Column(name = "SP_ID", precision = 6)
  private Long spId;

  @Column(name = "SRC_NBR", length = 60)
  private String srcNbr;
}