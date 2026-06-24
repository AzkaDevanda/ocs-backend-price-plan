package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BwfStep;
import com.sts.sinorita.projection.trigger.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BwfStepRepository extends JpaRepository<BwfStep, Integer> {

    @Query(value = "select max (STEP_ID) from BWF_STEP",nativeQuery = true)
    Integer getMaxStepId();

    @Query(value = "SELECT b FROM BwfStep b where b.sortRuleName = :ruleName")
    Optional<BwfStep> findByRuleName(@Param("ruleName") String ruleName);

    @Query(value = "SELECT b FROM  BwfStep b WHERE b.id = :stepId")
    Optional<BwfStep> selectBwfStep(@Param("stepId") Integer stepId);

    @Query(value = """
            SELECT
        	A.COND_GROUP_ID AS condGroupId,
        	A.STEP_ID AS stepId,
        	B.COND_GROUP_ID AS matchSortAttrGroupId,
        	B.SEQ AS seq,
        	B.RE_ATTR AS reAttr,
        	B.FUNCTION AS FUNCTION,
        	B.PARAM1 AS param1,
        	B.PARAM2 AS param2,
        	B.SORT_OPERATOR AS sortOperator,
        	B.OPERAND AS operand,
        	B.IS_CONST AS isConst,
        	B.R_RE_ATTR AS rReAttr,
        	B.R_FUNCTION AS rFunction,
        	B.R_PARAM1 AS rParam1,
        	B.R_PARAM2 AS rParam2,
        	B.ZONE_ID AS zoneId,
        	B.FUNCTION_SCRIPT AS functionScript,
        	B.R_FUNCTION_SCRIPT AS rFunctionScript,
        	C.RE_TYPE AS reType,
        	C.RE_ATTR_NAME AS reAttrName,
        	D.RE_TYPE AS rReType,
        	D.RE_ATTR_NAME AS rReAttrName,
        --	E.SORT_OPERATOR_NAME AS sortOperatorName,
        --	E.COMMENTS AS sortOperatorComments,
        	F.ZONE_ID AS zoneId2,
        	F.PARENT_ZONE_ID AS parentZoneId,
        	F.ZONE_NAME AS zoneName,
        	F.COMMENTS AS zoneItemComments,
        	F.ZONE_MAP_ID AS zoneMapId,
        	F.ZONE_CODE AS zoneCode
        FROM
        	BWF_COND_GROUP A
        JOIN BWF_COND B ON
        	A.COND_GROUP_ID = B.COND_GROUP_ID
        LEFT JOIN RE_ATTR D ON
        	B.R_RE_ATTR = D.RE_ATTR
        LEFT JOIN ZONE F ON
        	B.ZONE_ID = F.ZONE_ID,
        	RE_ATTR C
        --	SORT_OPERATOR E
        WHERE
        	B.RE_ATTR = C.RE_ATTR
        --	AND B.SORT_OPERATOR = E.SORT_OPERATOR
        	AND A.STEP_ID = :stepId
        ORDER BY
        	A.COND_GROUP_ID,
        	B.SEQ
        """, nativeQuery = true)
    List<FindBwfCondGroupByStepIdProjection> findBwfCondGroupByStepId(@Param("stepId") Integer stepId);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE BWF_STEP
        SET NODE_ID = :nodeId,
            -- OUTPUT_NODE_ID = :outputNodeId,
            SORT_RULE_NAME = :sortRuleName,
            -- COMMENTS = :comments,
            EXEC_ORDER = :execOrder,
            EFF_DATE = :effDate,
            EXP_DATE = :expDate,
            SP_ID = :spId
        WHERE STEP_ID = :stepId
        """, nativeQuery = true)
    void updateBwfStep(
            @Param("nodeId") Integer nodeId,
//            @Param("outputNodeId") Integer outputNodeId,
            @Param("sortRuleName") String sortRuleName,
//            @Param("comments") String comments,
            @Param("execOrder") Integer execOrder,
            @Param("effDate") LocalDate effDate,
            @Param("expDate") LocalDate expDate,
            @Param("spId") Integer spId,
            @Param("stepId") Integer stepId
    );


    @Query(value = "select b from BwfStep b where b.id = :id")
    public Optional<BwfStep> selectBwfStepById(@Param("id") Integer id);


    @Query(value = "SELECT COALESCE(MAX(STEP_ID), 0) + 1 FROM BWF_STEP", nativeQuery = true)
    Long getNextStepId();


    @Query(value = """
        SELECT A.FUNCTION AS function,
               A.COMMENTS AS comments,
               A.PARAM_NUM AS paramNum,
               A.PARAM1_NAME AS param1Name,
               A.PARAM1_VALUE_TYPE AS param1ValueType,
               A.PARAM1_DESC AS param1Desc,
               A.PARAM1_VALUE_SCRIPT AS param1ValueScript,
               A.PARAM2_NAME AS param2Name,
               A.PARAM2_VALUE_TYPE AS param2ValueType,
               A.PARAM2_DESC AS param2Desc,
               A.PARAM2_VALUE_SCRIPT AS param2ValueScript,
               A.USAGE_FLAG AS usageFlag,
               A.FUNCTION_TYPE_FLAG AS functionTypeFlag,
               B1.TYPE_NAME AS param1ValueTypeName,
               B2.TYPE_NAME AS param2ValueTypeName
          FROM SORT_FUNCTION A
               LEFT JOIN PARAM_VALUE_TYPE B1 ON A.PARAM1_VALUE_TYPE = B1.VALUE_TYPE
               LEFT JOIN PARAM_VALUE_TYPE B2 ON A.PARAM2_VALUE_TYPE = B2.VALUE_TYPE
         WHERE (:usageFlag IS NULL OR UPPER(A.USAGE_FLAG) LIKE UPPER(:usageFlag))
           AND (:functionTypeFlag IS NULL OR UPPER(A.FUNCTION_TYPE_FLAG) LIKE UPPER(:functionTypeFlag))
           AND (:functionName IS NULL OR A.FUNCTION = :functionName)
           AND (:usageStage IS NULL OR UPPER(A.USAGE_STAGE) LIKE UPPER(:usageStage))
         ORDER BY A.FUNCTION
        """, nativeQuery = true)
    List<SortFunctionProjection> qrySortFunction(
            @Param("usageFlag") String usageFlag,
            @Param("functionTypeFlag") String functionTypeFlag,
            @Param("functionName") String functionName,
            @Param("usageStage") String usageStage
    );

    @Query(value = """
        SELECT 
            SORT_OPERATOR.SORT_OPERATOR AS sortOperator,
            SORT_OPERATOR.SORT_OPERATOR_NAME AS sortOperatorName,
            SORT_OPERATOR.COMMENTS AS comments
        FROM SORT_OPERATOR
        """, nativeQuery = true)
    List<SortOperatorProjection> qrySortOperator();

    @Query(value = """
        SELECT 
            SYS_ACTION_NAME AS sysActionName,
            COMMENTS AS comments
        FROM SYS_ACTION_FUNCTION
        """, nativeQuery = true)
    List<SysActionFunctionProjection> qrySysActionFunction();

}
