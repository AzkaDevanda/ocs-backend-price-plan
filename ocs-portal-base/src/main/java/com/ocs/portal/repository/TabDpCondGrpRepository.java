package com.ocs.portal.repository;

import com.ocs.portal.entity.TabDpCondGrp;
import com.ocs.portal.projection.pricePlan.discount.QryTabDpCondGrpDtProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TabDpCondGrpRepository extends JpaRepository<TabDpCondGrp, Integer> {

    @Query("SELECT COALESCE(MAX(m.id)+1, 1) FROM TabDpCondGrp m")
    Integer getMaxValue();

    @Query(value = """
        SELECT T.TAB_DP_COND_GRP_ID          AS tabDpCondGrpId,
               T.TAB_DP_COND_GRP_NAME        AS tabDpCondGrpName,
               T.DP_REF_COND_TYPE            AS dpRefCondType,
               A.GRP_ID                      AS grpId,
               A.SEQ_NO                      AS seqNo,
               A.L_DP_REF_COND_ID            AS lDpRefCondId,
               A.L_PARAM1                    AS lParam1,
               A.SORT_OPERATOR               AS sortOperator,
               D.VALUE_STRING                AS rVal,
               B.DP_REF_COND_NAME            AS dpRefCondName,
               B.DP_REF_COND_NAME            AS lDpRefCondName,
               C.ATTR_NAME                   AS lParam1Name
          FROM TAB_DP_COND_GRP T
          JOIN TAB_DP_COND_GRP_DT A ON T.TAB_DP_COND_GRP_ID = A.TAB_DP_COND_GRP_ID
          JOIN DP_REF_COND B ON A.L_DP_REF_COND_ID = B.DP_REF_COND_ID
          JOIN REF_VALUE D ON A.R_VAL = D.REF_VALUE_ID
          LEFT JOIN ATTR C ON A.L_PARAM1 = C.ATTR_ID
         WHERE T.TAB_DP_COND_GRP_ID = :tabDpCondGrpId
           AND NVL(T.SP_ID,0) = :spId
         ORDER BY A.GRP_ID, A.SEQ_NO
        """, nativeQuery = true)
    List<QryTabDpCondGrpDtProjection> QryTabDpCondGrpDt(
            @Param("tabDpCondGrpId") Integer tabDpCondGrpId,
            @Param("spId") Integer spId
    );

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO TAB_DP_COND_GRP 
            (TAB_DP_COND_GRP_ID, TAB_DP_COND_GRP_NAME, DP_REF_COND_TYPE) 
        VALUES 
            (:id, :name, :type)
        """, nativeQuery = true)
    void insertTabDpCondGrp(
            @Param("id") Integer id,
            @Param("name") String tabDpCondGrpName,
            @Param("type") String dpRefCondType
    );

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TAB_DP_COND_GRP WHERE TAB_DP_COND_GRP_ID = :tabDpCondGrpId", nativeQuery = true)
    int deleteTabDpCondGrp(@Param("tabDpCondGrpId") Integer tabDpCondGrpId);



}
