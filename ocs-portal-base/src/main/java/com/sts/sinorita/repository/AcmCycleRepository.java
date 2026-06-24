package com.sts.sinorita.repository;

import com.sts.sinorita.entity.embeddable.AcmCycleId;
import com.sts.sinorita.entity.mdbtt.AcmCycle;
import com.sts.sinorita.projection.balanceAdjustment.AcmValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcmCycleRepository extends JpaRepository<AcmCycle, AcmCycleId> {
  @Query(value = "SELECT A.VALUE FROM ACM_CYCLE A WHERE A.SUBS_ID = :subsId AND A.ACM_CYCLE_TYPE_ID = :acmCycleTypeId AND A.CYCLE_BEGIN_DATE = :cycleBeginDate", nativeQuery = true)
  Optional<AcmValueProjection> getAcmTypeA (@Param("subsId") Long subsId, @Param("acmCycleTypeId") Long acmCycleTypeId, @Param("cycleBeginDate") Long cycleBeginDate);
}
