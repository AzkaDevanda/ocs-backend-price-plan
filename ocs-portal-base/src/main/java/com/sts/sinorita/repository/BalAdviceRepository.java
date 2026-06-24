package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BalAdvice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface BalAdviceRepository extends JpaRepository<BalAdvice, Integer>, Serializable {
  @Query(value = "select b from BalAdvice b where b.id.balThresholdId = :balThresholdId and (:adviceType is null or b.id.adviceType = :adviceType ) AND (:eventType is null or b.adviceEventId = :eventType )")
  public List<BalAdvice> findByBalThresholdId(@Param("balThresholdId") Integer balThresholdId, @Param("adviceType") Integer adviceType, @Param("eventType") Integer eventType, Pageable pageable);
}
