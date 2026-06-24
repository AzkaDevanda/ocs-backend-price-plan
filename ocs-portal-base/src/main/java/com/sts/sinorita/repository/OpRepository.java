package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Op;
import com.sts.sinorita.projection.pricePlan.price.QryUpRuleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpRepository extends JpaRepository<Op, Integer> {

    @Query(value = """
            SELECT A.OP_ID as UP_ID, A.RULE_SCRIPT, A.RULE_COMMENTS, A.SCRIPT_PAGE, A.SCRIPT_TEMPLET_ID,A.SCRIPT_PAGE
              FROM OP A
             WHERE A.OP_ID = :OP_ID
             AND (:SP_ID IS NULL OR NVL(A.SP_ID, 0) = :SP_ID)
            """, nativeQuery = true)
    Optional<QryUpRuleProjection> qryOp(@Param("OP_ID") Integer opdId, @Param("SP_ID") Integer spId);
}
