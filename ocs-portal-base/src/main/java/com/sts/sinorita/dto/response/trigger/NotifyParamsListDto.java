package com.sts.sinorita.dto.response.trigger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyParamsListDto {
    private String adviceType;
    private String adviceTypeName;
    private String adviceChannel;
    private String adviceChannelName;
    private String comments;
    private String msgDefine;
    private String disabled;
    private LocalDate effTime;
    private LocalDate expTime;
    private String stdCode;
    private Integer spId;
    private String adviceCatg;
    private Integer priority;
    private LocalDate updateDate;
    private Integer delayTime;
    private String isHis;
    private String srcNbr;
    private String senderParam;
    private Integer adviceTypeSortId;
    private String subjectDefine;
    private Integer times;
    private Integer timeInterval;
}
