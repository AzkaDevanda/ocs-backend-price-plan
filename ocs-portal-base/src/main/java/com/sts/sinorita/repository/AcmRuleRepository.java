package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcmRule;
import com.sts.sinorita.projection.pricePlan.price.QryAcmRuleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcmRuleRepository extends JpaRepository<AcmRule, Integer> {
    @Query("SELECT COALESCE(MAX(t.priority), 0) + 1 FROM AcmRule t WHERE t.id = :priceVerId")
    Integer findNextPriority(@Param("priceVerId") Integer priceVerId);

    //    AND A.ACM_RULE_ID = :ACM_RULE_ID
    @Query(value = """
            SELECT
            	A.ACM_RULE_ID as acmRuleId,
            	A.RULE_SCRIPT as ruleScript,
            	A.PRIORITY as priority,
            	A.RULE_COMMENTS as ruleComments,
            	A.PRICE_VER_ID as priceVerId,
            	A.SCRIPT_TEMPLET_ID as scriptTempletId,
            	A.SCRIPT_PAGE as scriptPage
            FROM
            	ACM_RULE A
            WHERE
            	1 = 1
            	AND A.PRICE_VER_ID = :PRICE_VER_ID
            	AND NVL(A.SP_ID, 0) = :SP_ID
            """, nativeQuery = true)
    Optional<QryAcmRuleProjection> qryAcmRule(@Param("PRICE_VER_ID") Integer priceVerId, @Param("SP_ID") Integer spId);

    @Modifying
    @Query("""
            DELETE FROM AcmRule ar WHERE ar.priceVerId = :priceVerId
            """)
    void deleteAcmRule(@Param("priceVerId") Integer priceVerId);


}
