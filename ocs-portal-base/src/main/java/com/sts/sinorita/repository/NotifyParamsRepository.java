package com.sts.sinorita.repository;

import com.sts.sinorita.entity.NotifyParams;
import com.sts.sinorita.projection.trigger.NotifyParamsIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotifyParamsRepository extends JpaRepository<NotifyParams,Integer> {

    @Query(value = """
        SELECT 
            A.NOTIFY_PARAMS_ID as notifyParamsId,
            A.NOTIFY_NAME as notifyName,
            A.NOTIFY_TYPE as notifyType,
            A.NOTIFY_ID as notifyId,
            A.COMMENTS as comments,
            A.EFF_DATE as effDate,
            A.EXP_DATE as expDate,
            A.SP_ID as spId
        FROM NOTIFY_PARAMS A
        WHERE A.EFF_DATE <= SYSDATE
          AND (A.EXP_DATE > SYSDATE OR A.EXP_DATE IS NULL)
          AND A.NOTIFY_TYPE = :notifyType
          AND NVL(A.SP_ID, 0) = :spId
        """, nativeQuery = true)
    List<NotifyParamsIdProjection> findNotifyParamsId(
            @Param("notifyType") Character notifyType,
            @Param("spId") Integer spId
    );
}
