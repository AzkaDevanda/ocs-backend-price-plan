package com.sts.sinorita.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.AdjustReason;
import com.sts.sinorita.projection.balanceAdjustment.SelectAdjustReasonProjection;

@Repository
public interface AdjustReasonRepository extends JpaRepository<AdjustReason, Long> {

  @Query(value = "SELECT ADJUST_REASON_ID AS adjustReasonId, ADJUST_REASON_NAME AS adjustReasonName, COMMENTS AS comments, SP_ID AS spId, ADJUST_REASON_CODE AS adjustReasonCode FROM ADJUST_REASON WHERE ADJUST_REASON_ID = :adjustReasonId", nativeQuery = true)
  Optional<SelectAdjustReasonProjection> selectAdjustReason(
      @Param("adjustReasonId") Long adjustReasonId);

}
