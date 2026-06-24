package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AbEventBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface AbEventBenefitRepository extends JpaRepository<AbEventBenefit, Long> {

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO AB_EVENT_BENEFIT (EVENT_INST_ID, ACCT_BOOK_ID, PRICE_ID, SUB_BAL_TYPE_ID, EFF_DATE, EXP_DATE, BENEFIT_BAL, SP_ID) VALUES (:eventInstId, :acctBookId, :priceId, :subBalTypeId, :effDate, :expDate, :benefitBal, :spId)", nativeQuery = true)
  void abEventBenefitBatchStore (@Param("eventInstId") Long eventInstId, @Param("acctBookId") Long acctBookId, @Param("priceId") Long priceId, @Param("subBalTypeId") Long subBalTypeId, @Param("effDate") LocalDateTime effDate, @Param("expDate") LocalDateTime expDate, @Param("benefitBal") Long benefitBal, @Param("spId") Long spId);
}
