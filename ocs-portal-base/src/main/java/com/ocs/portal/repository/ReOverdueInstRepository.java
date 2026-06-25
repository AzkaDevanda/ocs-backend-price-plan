package com.ocs.portal.repository;

import com.ocs.portal.entity.ReOverdueInst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReOverdueInstRepository extends JpaRepository<ReOverdueInst, Long> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO RE_OVERDUE_INST (EVENT_INST_ID, PAYMENT_SETT_ID, OVERDUE_PLAN_ID, CAPITAL_AMOUNT, OVERDUE_DAY, SP_ID) VALUES (:eventInstId, :paymentSettId, :overduePlanId, :capitalAmount, :overdueDay, :spId)", nativeQuery = true)
  void reOverdueInstBatchStore (@Param("eventInstId") Long eventInstId, @Param("paymentSettId") Long paymentSettId, @Param("overduePlanId") Long overduePlanId, @Param("capitalAmount") Long capitalAmount, @Param("overdueDay") Long overdueDay, @Param("spId") Long spId);
}
