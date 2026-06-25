package com.ocs.portal.repository;

import com.ocs.portal.entity.DpRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DpRuleRepository extends JpaRepository<DpRule, Integer> {
    boolean existsByAcctItemTypeId(Integer acctItemTypeId);

    @Modifying
    @Transactional
    @Query(value = "delete from DP_RULE where DP_ID = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);
}
