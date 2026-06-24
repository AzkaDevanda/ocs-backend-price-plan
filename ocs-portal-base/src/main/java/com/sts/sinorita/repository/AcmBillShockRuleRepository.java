package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcmBillShockRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AcmBillShockRuleRepository extends JpaRepository<AcmBillShockRule,Integer> {

    @Modifying
    @Query(value = """
        DELETE FROM ACM_BILL_SHOCK_RULE WHERE ACM_THRESHOLD_ID = :thresholdId
        """,nativeQuery = true)
    void delAcmBillShockRule(@Param("thresholdId") Long acmThresholdId);
}
