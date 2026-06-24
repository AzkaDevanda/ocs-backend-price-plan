package com.sts.sinorita.repository;

import com.sts.sinorita.entity.TabDpDt;
import com.sts.sinorita.entity.TabDpDtId;
import com.sts.sinorita.projection.pricePlan.discount.QryTabDpDtProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabDpDtRepository extends JpaRepository<TabDpDt, TabDpDtId> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO TAB_DP_DT (
            DP_ID,
            SEQ_NO,
            DISCT_CALC_METHOD,
            CALC_VAL,
            F_VAL,
            C_VAL,
            S_VAL,
            E_VAL
        ) VALUES (
            :dpId,
            :seqNo,
            :disctCalcMethod,
            :calcVal,
            :fVal,
            :cVal,
            :sVal,
            :eVal
        )
    """, nativeQuery = true)
    void insertTabDpDt(
            @Param("dpId") Integer dpId,
            @Param("seqNo") Short seqNo,
            @Param("disctCalcMethod") String disctCalcMethod,
            @Param("calcVal") Integer calcVal,
            @Param("fVal") Integer fVal,
            @Param("cVal") Integer cVal,
            @Param("sVal") String sVal,   // pakai String kalau kolom CHAR/VARCHAR
            @Param("eVal") String eVal    // pakai String juga
    );

    @Query(value = """
        SELECT *
        FROM TAB_DP_DT
        WHERE DP_ID = :dpId
        ORDER BY SEQ_NO
        """, nativeQuery = true)
    List<TabDpDt> selectTabDp(@Param("dpId") Integer dpId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM TAB_DP_DT WHERE DP_ID = :dpId", nativeQuery = true)
    int deleteTabDpDt(@Param("dpId") Integer dpId);

    @Query(value = """
            SELECT
            	t.dp_id AS dpId,
            	t.seq_no AS seqNo,
            	t.disct_calc_method AS disctCalcMethod,
            	rv4.VALUE_STRING  AS sVal,
            	rv5.VALUE_STRING  AS eVal,
            	rv1.VALUE_STRING  AS refValue,
            	rv2.VALUE_STRING  AS refCellValue,
            	rv3.VALUE_STRING  AS refFloorValue,
            	t.sp_id AS spId,
            	(rv4.VALUE_STRING  || '-' || rv5.VALUE_STRING) AS showVal
            FROM
            	tab_dp_dt t
            LEFT JOIN\s
            	REF_VALUE rv1 ON t.CALC_VAL = rv1.REF_VALUE_ID \s
            LEFT JOIN\s
            	REF_VALUE rv2 ON t.F_VAL = rv2.REF_VALUE_ID
            LEFT JOIN\s
            	REF_VALUE rv3 ON t.C_VAL = rv3.REF_VALUE_ID
            LEFT JOIN\s
            	REF_VALUE rv4 ON t.S_VAL = rv4.REF_VALUE_ID\s
            LEFT JOIN\s
            	REF_VALUE rv5 ON t.E_VAL = rv5.REF_VALUE_ID\s
            WHERE
            	t.dp_id = :dpId
            ORDER BY
            	t.seq_no
            """,nativeQuery = true)
    List<QryTabDpDtProjection> qryTabDpDt(@Param("dpId") Integer dpId);

}
