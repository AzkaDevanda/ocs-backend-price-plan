package com.sts.sinorita.repository;

import com.sts.sinorita.entity.TabDpCondGrpDt;
import com.sts.sinorita.entity.TabDpCondGrpDtId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TabDpCondGrpDtRepository extends JpaRepository<TabDpCondGrpDt, TabDpCondGrpDtId> {

    @Modifying
    @Query(value = """
        INSERT INTO TAB_DP_COND_GRP_DT (
            TAB_DP_COND_GRP_ID,
            GRP_ID,
            SEQ_NO,
            L_DP_REF_COND_ID,
            L_PARAM1,
            SORT_OPERATOR,
            R_VAL
        ) VALUES (
            :tabDpCondGrpId,
            :grpId,
            :seqNo,
            :lDpRefCondId,
            :lParam1,
            :sortOperator,
            :rVal
        )
    """, nativeQuery = true)
    void insertTabDpCondGrpDt(
            @Param("tabDpCondGrpId") Integer tabDpCondGrpId,
            @Param("grpId") Integer grpId,
            @Param("seqNo") Integer seqNo,
            @Param("lDpRefCondId") Integer lDpRefCondId,
            @Param("lParam1") String lParam1,
            @Param("sortOperator") String sortOperator,
            @Param("rVal") String rVal
    );

    @Query(value = """
           SELECT *
           FROM TAB_DP_COND_GRP_DT
           WHERE TAB_DP_COND_GRP_ID = :tabDpCondGrpId
           -- ORDER BY GRP_ID, SEQ_NO
           """, nativeQuery = true)
    List<TabDpCondGrpDt> selectTabDpCondGrpDt(@Param("tabDpCondGrpId") Integer tabDpCondGrpId);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM TAB_DP_COND_GRP_DT WHERE TAB_DP_COND_GRP_ID = :tabDpCondGrpId
        """, nativeQuery = true)
    void deleteTabDpCondGrpDt(@Param("tabDpCondGrpId") Integer tabDpCondGrpId);

}