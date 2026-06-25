package com.ocs.portal.repository;

import com.ocs.portal.entity.TabDp;
import com.ocs.portal.projection.pricePlan.discount.QryTabDpInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TabDpRepository extends JpaRepository<TabDp,Integer> {
    @Query(value = """
    SELECT T.DP_ID, T.TAB_DP_TYPE, T.REF_DISCT_OBJ_ID, T.ACCT_ITEM_TYPE_ID, T.DISTRIBUTE_METHOD, 
           T.TAB_DP_COND_GRP_ID, T.CALC_DISCT_OBJ_ID, T.APPLY_DISCT_OBJ_ID, T.NEGATIVE_FLAG, T.SP_ID, 
           A.ACCT_ITEM_TYPE_NAME, A.ACCT_ITEM_TYPE_CODE, A.ACCT_RES_ID, 
           R.OBJ_IDENTITY_ID AS REF_OBJ_IDENTITY_ID, R.DISCT_OBJ_NAME AS REF_DISCT_OBJ_NAME, 
           R.DISCT_OBJ_TYPE AS REF_DISCT_OBJ_TYPE, R.TAB_DP_COND_GRP_ID AS REF_TAB_DP_COND_GRP_ID, 
           R.GST_SEQ AS REF_GST_SEQ, 
           C.OBJ_IDENTITY_ID AS CALC_OBJ_IDENTITY_ID, C.DISCT_OBJ_NAME AS CALC_DISCT_OBJ_NAME, 
           C.DISCT_OBJ_TYPE AS CALC_DISCT_OBJ_TYPE, C.TAB_DP_COND_GRP_ID AS CALC_TAB_DP_COND_GRP_ID, 
           C.GST_SEQ AS CALC_GST_SEQ, 
           AA.OBJ_IDENTITY_ID AS APPLY_OBJ_IDENTITY_ID, AA.DISCT_OBJ_NAME AS APPLY_DISCT_OBJ_NAME, 
           AA.DISCT_OBJ_TYPE AS APPLY_DISCT_OBJ_TYPE, AA.TAB_DP_COND_GRP_ID AS APPLY_TAB_DP_COND_GRP_ID, 
           R.MEMBER_ALIAS AS REF_MEMBER_ALIAS, C.MEMBER_ALIAS AS CALC_MEMBER_ALIAS, 
           AA.MEMBER_ALIAS AS APPLY_MEMBER_ALIAS
      FROM TAB_DP T, ACCT_ITEM_TYPE A, DISCT_OBJ R, DISCT_OBJ C, DISCT_OBJ AA
     WHERE T.DP_ID = :dpId
       AND T.ACCT_ITEM_TYPE_ID = A.ACCT_ITEM_TYPE_ID(+)
       AND T.REF_DISCT_OBJ_ID = R.DISCT_OBJ_ID
       AND T.CALC_DISCT_OBJ_ID = C.DISCT_OBJ_ID
       AND T.APPLY_DISCT_OBJ_ID = AA.DISCT_OBJ_ID
       AND NVL(T.SP_ID, 0) = :spId
    """, nativeQuery = true)
    List<QryTabDpInfoProjection> QryTabDpInfo(@Param("dpId") Integer dpId, @Param("spId") Integer spId);

    @Modifying
    @Query(value = """
    INSERT INTO TAB_DP (
        DP_ID,
        TAB_DP_TYPE,
        REF_DISCT_OBJ_ID,
        ACCT_ITEM_TYPE_ID,
        DISTRIBUTE_METHOD,
        TAB_DP_COND_GRP_ID,
        CALC_DISCT_OBJ_ID,
        APPLY_DISCT_OBJ_ID,
        NEGATIVE_FLAG
    ) VALUES (:dpId, :tabDpType, :refDisctObjId, :acctItemTypeId, :distributeMethod, :tabDpCondGrpId, :calcDisctObjId, :applyDisctObjId, :negativeFlag)
""", nativeQuery = true)
    void insertTabDp(
            @Param("dpId") Integer dpId,
            @Param("tabDpType") Character tabDpType,
            @Param("refDisctObjId") Integer refDisctObjId,
            @Param("acctItemTypeId") Integer acctItemTypeId,
            @Param("distributeMethod") Character distributeMethod,
            @Param("tabDpCondGrpId") Integer tabDpCondGrpId,
            @Param("calcDisctObjId") Integer calcDisctObjId,
            @Param("applyDisctObjId") Integer applyDisctObjId,
            @Param("negativeFlag") Character negativeFlag
    );


    @Query(value = "SELECT * FROM TAB_DP WHERE DP_ID = :dpId", nativeQuery = true)
    Optional<TabDp> selectTabDp(@Param("dpId") Integer dpId);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE TAB_DP WHERE DP_ID= :dpId
            """, nativeQuery = true)
    int deleteTabDp(@Param("dpId") Integer dpId);
}
