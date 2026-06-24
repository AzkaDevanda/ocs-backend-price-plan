package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BwfWorkflow;
import com.sts.sinorita.projection.trigger.StepProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BwfWorkFlowRepository extends JpaRepository<BwfWorkflow, Integer> {

    @Query(value = "select b from BwfWorkflow b where b.workflowName = :workflowName")
    Optional<BwfWorkflow> getMaxWorkFLowByName(@Param("workflowName") String workflowName);

    @Query(value = "select max(WORKFLOW_ID) from BWF_WORKFLOW", nativeQuery = true)
    Integer getMaxWorkflowId();


    @Query(value = """
    SELECT 
        A.NODE_ID AS inputNodeId,
        A.NODE_ID AS nodeId,
        A.STEP_ID AS stepId,
        A.EFF_DATE AS effDate,
        A.EXP_DATE AS expDate,
        A.SORT_RULE_NAME AS sortRuleName,
        A.COMMENTS AS comments,
        A.EXEC_ORDER AS execOrder,
        A.OUTPUT_NODE_ID AS outputNodeId,
        B.NODE_NAME AS inputNodeName,
        B.WORKFLOW_ID AS workflowId,
        C.NODE_NAME AS outputNodeName
    FROM 
        BWF_STEP A
    JOIN 
        BWF_NODE B ON A.NODE_ID = B.NODE_ID
    LEFT JOIN 
        BWF_NODE C ON A.OUTPUT_NODE_ID = C.NODE_ID
    WHERE 
        (:nodeId IS NULL OR A.NODE_ID = :nodeId)
        AND (:workflowId IS NULL OR B.WORKFLOW_ID = :workflowId)
        AND (:sortRuleName IS NULL OR UPPER(A.SORT_RULE_NAME) LIKE '%' || UPPER(:sortRuleName) || '%')
    ORDER BY 
        B.FIRST_NODE DESC, A.NODE_ID, A.EXEC_ORDER
""", nativeQuery = true)
    List<StepProjection> findStepsWithDetails(
            @Param("nodeId") Long nodeId,
            @Param("workflowId") Long workflowId,
            @Param("sortRuleName") String sortRuleName
    );

}
