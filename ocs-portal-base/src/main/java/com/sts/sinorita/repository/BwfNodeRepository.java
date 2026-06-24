package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BwfNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BwfNodeRepository extends JpaRepository<BwfNode, Integer> {
    @Query(value = "select max(NODE_ID) from BWF_NODE", nativeQuery = true)
    Integer getMaxNodeId();

    @Query(value = "select 1 from BWF_NODE where NODE_NAME = :nodeName", nativeQuery = true)
    Optional<Integer>getBwfNodeByNodeName(String nodeName);
}
