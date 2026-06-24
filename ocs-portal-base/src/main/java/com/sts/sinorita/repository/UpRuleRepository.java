package com.sts.sinorita.repository;

import com.sts.sinorita.entity.UpRule;
import com.sts.sinorita.projection.pricePlan.price.QryUpRuleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UpRuleRepository extends JpaRepository<UpRule, Long> {

    @Query("SELECT COALESCE(MAX(t.priority), 0) + 1 FROM UpRule t WHERE t.upId = :upId")
    Integer findNextPriority(@Param("upId") Integer UpId);

    @Query(value = """
            SELECT A.UP_RULE_ID, A.UP_ID, A.PRIORITY, A.RULE_SCRIPT, A.RULE_COMMENTS, A.SCRIPT_PAGE, A.SCRIPT_TEMPLET_ID,A.SCRIPT_PAGE
              FROM UP_RULE A
             WHERE A.UP_ID = :UP_ID
             AND (:SP_ID IS NULL OR NVL(A.SP_ID, 0) = :SP_ID)
            """, nativeQuery = true)
    Optional<QryUpRuleProjection> qryUpRule(@Param("UP_ID") Integer upId, @Param("SP_ID") Integer spId);


    @Modifying
    @Query("DELETE FROM UpRule t WHERE t.upId = :upId")
    void deleteByUpId(@Param("upId") Integer upId);

    @Query(value = "select up from UpRule up where up.upId = :upId ")
    Optional<UpRule> findByUpId(@Param("upId") Integer upId);
}
