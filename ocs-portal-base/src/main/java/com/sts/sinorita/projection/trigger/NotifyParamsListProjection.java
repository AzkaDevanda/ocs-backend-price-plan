package com.sts.sinorita.projection.trigger;

import java.time.LocalDate;

public interface NotifyParamsListProjection {
    String getAdviceType();
    String getAdviceTypeName();
    String getAdviceChannel();
    String getAdviceChannelName();
    String getComments();
    String getMsgDefine();
    String getDisabled();
    LocalDate getEffTime();
    LocalDate getExpTime();
    String getStdCode();
    Integer getSpId();
    String getAdviceCatg();
    Integer getPriority();
    LocalDate getUpdateDate();
    Integer getDelayTime();
    String getIsHis();
    String getSrcNbr();
    String getSenderParam();
    Integer getAdviceTypeSortId();
    String getSubjectDefine();
    Integer getTimes();
    Integer getTimeInterval();
}
