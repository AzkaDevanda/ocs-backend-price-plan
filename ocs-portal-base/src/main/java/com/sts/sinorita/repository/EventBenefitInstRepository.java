package com.sts.sinorita.repository;

import com.sts.sinorita.entity.EventBenefitInst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EventBenefitInstRepository extends JpaRepository<EventBenefitInst, Long> {
  @Transactional
  @Modifying
  @Query(value = """
    UPDATE EVENT_BENEFIT_INST
    SET BAL_ID = :balId,
        ACCT_RES_ID = :acctResId
    WHERE EVENT_INST_ID = :eventInstId
      AND PRICE_ID = :priceId
      AND SEQ = :seq
    """, nativeQuery = true)
  void eventBenefitInstBalIdBatchFillup (
    @Param("balId") Long balId,
    @Param("acctResId") Long acctResId,
    @Param("eventInstId") Long eventInstId,
    @Param("priceId") Long priceId,
    @Param("seq") Long seq
  );
}
