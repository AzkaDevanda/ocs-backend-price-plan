package com.sts.sinorita.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ADVICE", schema = "STS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advice implements Serializable {
    @Id
    @Column(name = "ADVICE_ID", nullable = false)
    private Long adviceId;

    @Column(name = "ADVICE_TYPE", nullable = false)
    private Integer adviceType;

    @Column(name = "SUBS_ID", nullable = false)
    private Long subsId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "PREFIX", length = 60)
    private String prefix;

    @Column(name = "ACC_NBR", length = 60, nullable = false)
    private String accNbr;

    @Lob
    @Column(name = "MSG")
    private String msg;

    @Column(name = "MSG_PARAM", length = 4000)
    private String msgParam;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "STATE", nullable = false)
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

    @Column(name = "DELAY_TIME")
    private Integer delayTime;

    @Column(name = "MESSAGE_ID", length = 120)
    private String messageId;

    @Column(name = "TIMES")
    private Integer times;

    @Column(name = "TIME_INTERVAL")
    private Integer timeInterval;

    @Column(name = "PRIORITY", nullable = false)
    private String priority;

    @Column(name = "PART_ID", nullable = false)
    private Integer partId;

    @Column(name = "SP_ID")
    private Integer spId;

    @Column(name = "SRC_NBR", length = 60)
    private String srcNbr;
}