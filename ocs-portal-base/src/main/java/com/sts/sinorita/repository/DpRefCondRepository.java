package com.sts.sinorita.repository;

import com.sts.sinorita.entity.DpRefCond;
import com.sts.sinorita.projection.pricePlan.discount.DpRefCondProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DpRefCondRepository extends JpaRepository<DpRefCond, Integer> {
    @Query(value = """
        SELECT 
            T.DP_REF_COND_ID        AS dpRefCondId,
            T.DP_REF_COND_TYPE      AS dpRefCondType,
            T.DP_REF_COND_NAME      AS dpRefCondName,
            T.DP_REF_COND_PARAM_NUM AS dpRefCondParamNum,
            T.DATA_TYPE             AS dataType,
            T.COMMENTS              AS comments
        FROM DP_REF_COND T
        WHERE 1 = 1
          AND (:dpRefCondId IS NULL OR T.DP_REF_COND_ID = :dpRefCondId)
          AND (:dpRefCondTypes IS NULL OR T.DP_REF_COND_TYPE IN (:dpRefCondTypes))
          AND (:spId IS NULL OR NVL(T.SP_ID, 0) = :spId)
        """, nativeQuery = true)
    List<DpRefCondProjection> getDpRefCond(@Param("dpRefCondId") Integer dpRefCondId,
                                           @Param("dpRefCondTypes") String dpRefCondTypes,
                                           @Param("spId") Integer spId);
}
