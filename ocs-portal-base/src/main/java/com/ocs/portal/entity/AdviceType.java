package com.ocs.portal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.*;

@Entity
@Table(name = "ADVICE_TYPE", schema = "STS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "advice_type_seq")
    @SequenceGenerator(name = "advice_type_seq", sequenceName = "ADVICE_TYPE_SEQ", allocationSize = 1)
    @Column(name = "ADVICE_TYPE", nullable = false)
    private Long adviceType;

    @Column(name = "ADVICE_CHANNEL", nullable = false, length = 2)
    private String adviceChannel;

    @Column(name = "ADVICE_TYPE_NAME", nullable = false, length = 60)
    private String adviceTypeName;

    @Column(name = "AK_CODE", length = 60, unique = true)
    private String akCode;

    @Column(name = "PARENT_ADVICE_TYPE")
    private Long parentAdviceType;

    @Column(name = "IS_RULE", nullable = false, length = 1)
    private String isRule;

    @Column(name = "ADVICE_PARAM_CODE", length = 60)
    private String adviceParamCode;

    @Column(name = "COMMENTS", length = 3000)
    private String comments;

    @Column(name = "SUBJECT_DEFINE", length = 4000)
    private String subjectDefine;

    @Lob
    @Column(name = "MSG_DEFINE")
    private String msgDefine;

    @Column(name = "DISABLED", length = 1)
    private String disabled;

    @Column(name = "EFF_TIME")
    private LocalDateTime effTime;

    @Column(name = "EXP_TIME")
    private LocalDateTime expTime;

    @Column(name = "STD_CODE", length = 60)
    private String stdCode;

    @Column(name = "PRIORITY", length = 1)
    private String priority;

    @Column(name = "ADVICE_TYPE_SORT_ID")
    private Long adviceTypeSortId;

    @Column(name = "ADVICE_CATG", length = 60)
    private String adviceCatg;

    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updateDate;

    @Column(name = "IS_HIS", length = 1)
    private String isHis;

    @Column(name = "DELAY_TIME")
    private Long delayTime;

    @Column(name = "SRC_NBR", length = 1000)
    private String srcNbr;

    @Column(name = "IS_OLD_VERSION", length = 1)
    private String isOldVersion;

    @Column(name = "SENDER_PARAM", length = 1000)
    private String senderParam;

    @Column(name = "TIMES")
    private Integer times;

    @Column(name = "TIME_INTERVAL")
    private Long timeInterval;

    @Column(name = "SP_ID")
    private Long spId;
}
