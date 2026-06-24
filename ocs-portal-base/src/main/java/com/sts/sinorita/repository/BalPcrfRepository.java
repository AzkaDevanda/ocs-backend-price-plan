package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BalPcrf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalPcrfRepository extends JpaRepository<BalPcrf, Long> {

  @Query(value = "select b from BalPcrf b where b.balThresholdId = :balThresholdId")
  Optional<BalPcrf> findByTrehold(Integer balThresholdId);

  @Query(value = """
      SELECT BAL_THRESHOLD_ID
      FROM BAL_PCRF
      WHERE BAL_THRESHOLD_ID = :balThresholdId
      """, nativeQuery = true)
  List<Long> selectBalPCRF(@Param("balThresholdId") Long balThresholdId);

}
