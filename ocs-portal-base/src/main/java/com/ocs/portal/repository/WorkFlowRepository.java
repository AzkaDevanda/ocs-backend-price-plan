package com.ocs.portal.repository;

import com.ocs.portal.entity.BwfWorkflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkFlowRepository extends JpaRepository<BwfWorkflow,Integer> {
    @Query(value = "select b from BwfWorkflow b where b.workflowName =:name and b.id = :id")
    public Optional<BwfWorkflow> selectWorkFlowByNameAndId(@Param("name") String name, @Param("id") Integer id);
}
