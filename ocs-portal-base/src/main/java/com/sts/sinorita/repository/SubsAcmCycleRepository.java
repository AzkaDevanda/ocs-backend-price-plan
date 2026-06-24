package com.sts.sinorita.repository;

import com.sts.sinorita.entity.embeddable.SubsAcmCycleId;
import com.sts.sinorita.entity.mdbtt.SubsAcmCycle;
import com.sts.sinorita.projection.balanceAdjustment.AcmValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SubsAcmCycleRepository extends JpaRepository<SubsAcmCycle, SubsAcmCycleId> {

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO SUBS_ACM_CYCLE (SUBS_ID, RESOURCE_ID, BILLING_CYCLE_ID, VALUE) VALUES (:subsId, :resourceId, :billingCycleId, :value)", nativeQuery = true)
  void insertSubsAcmCycle (Long subsId, Long resourceId, Long billingCycleId, Long value);

  @Transactional
  @Modifying
  @Query(value = "UPDATE SUBS_ACM_CYCLE SET VALUE = NVL(VALUE,0) + :increaseValue WHERE SUBS_ID = :subsId AND RESOURCE_ID = :resourceId AND BILLING_CYCLE_ID = :billingCycleId", nativeQuery = true)
  int updateSubsAcmCycle (@Param("increaseValue") Long increaseValue, @Param("subsId") Long subsId, @Param("resourceId") Long resourceId, @Param("billingCycleId") Long billingCycleId);

  @Query(value = "SELECT VALUE FROM SUBS_ACM_CYCLE WHERE SUBS_ID = :subsId AND RESOURCE_ID = :resourceId AND BILLING_CYCLE_ID = :billingCycleId", nativeQuery = true)
  Optional<AcmValueProjection> getAcmType1 (@Param("subsId") Long subsId, @Param("resourceId") Long resourceId, @Param("billingCycleId") Long billingCycleId);

}
