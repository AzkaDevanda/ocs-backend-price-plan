package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcmCycleType;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcmCycleTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcmCycleTypeRepository extends JpaRepository<AcmCycleType, Integer> {

  @Query(value = "SELECT ACM_CYCLE_TYPE_ID AS acmCycleTypeId, TIME_UNIT AS timeUnit, QUANTITY AS quantity, BEGIN_DATE AS beginDate, REF_TYPE AS refType, RE_ATTR AS reAttr, SP_ID AS spId FROM ACM_CYCLE_TYPE WHERE ACM_CYCLE_TYPE_ID = :acmCycleTypeId", nativeQuery = true)
  Optional<SelectAcmCycleTypeProjection> getAcmCycleTypeById (@Param("acmCycleTypeId") Long acmCycleTypeId);

}
