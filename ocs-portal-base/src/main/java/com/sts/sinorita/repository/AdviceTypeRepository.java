package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AdviceType;
import com.sts.sinorita.projection.trigger.NotifyParamsListProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdviceTypeRepository extends JpaRepository<AdviceType,Integer> {
    @Query(value = """
    SELECT 
        A.ADVICE_TYPE AS adviceType,
        A.ADVICE_TYPE_NAME AS adviceTypeName,
        A.ADVICE_CHANNEL AS adviceChannel,
        B.ADVICE_CHANNEL_NAME AS adviceChannelName,
        TO_CHAR(A.COMMENTS) AS comments,
        TO_CHAR(A.MSG_DEFINE) AS msgDefine,
        A.DISABLED AS disabled,
        A.EFF_TIME AS effTime,
        A.EXP_TIME AS expTime,
        A.STD_CODE AS stdCode,
        A.SP_ID AS spId,
        A.ADVICE_CATG AS adviceCatg,
        A.PRIORITY AS priority,
        A.UPDATE_DATE AS updateDate,
        A.DELAY_TIME AS delayTime,
        A.IS_HIS AS isHis,
        A.SRC_NBR AS srcNbr,
        TO_CHAR(A.SENDER_PARAM) AS senderParam,
        A.ADVICE_TYPE_SORT_ID AS adviceTypeSortId,
        TO_CHAR(A.SUBJECT_DEFINE) AS subjectDefine,
        A.TIMES AS times,
        A.TIME_INTERVAL AS timeInterval
    FROM ADVICE_TYPE A
    JOIN ADVICE_CHANNEL B ON A.ADVICE_CHANNEL = B.ADVICE_CHANNEL
    WHERE A.ADVICE_CATG = :adviceCatg
      AND NVL(A.SP_ID, 0) = :spId
    ORDER BY A.ADVICE_TYPE_NAME
    """, nativeQuery = true)
    List<NotifyParamsListProjection> findNotifyParamsList(@Param("adviceCatg") String adviceCatg, @Param("spId") Integer spId);
}
