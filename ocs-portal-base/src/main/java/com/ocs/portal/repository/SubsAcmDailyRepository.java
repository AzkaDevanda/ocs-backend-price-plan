package com.ocs.portal.repository;

import com.ocs.portal.entity.embeddable.SubsAcmDailyId;
import com.ocs.portal.entity.mdbtt.SubsAcmDaily;
import com.ocs.portal.projection.balanceAdjustment.AcmValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SubsAcmDailyRepository extends JpaRepository<SubsAcmDaily, SubsAcmDailyId> {

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO SUBS_ACM_DAILY (SUBS_ID, RESOURCE_ID, DATE_STAMP, VALUE) VALUES (:subsId, :resourceId, :dateStamp, :value)", nativeQuery = true)
  void insertSubsAcmDaily (Long subsId, Long resourceId, Long dateStamp, Long value);

  @Transactional
  @Modifying
  @Query(value = "UPDATE SUBS_ACM_DAILY SET VALUE = NVL(VALUE,0) + :increaseValue WHERE SUBS_ID = :subsId AND RESOURCE_ID = :resourceId AND DATE_STAMP = :dateStamp", nativeQuery = true)
  int updateSubsAcmDaily (@Param("increaseValue") Long increaseValue, @Param("subsId") Long subsId, @Param("resourceId") Long resourceId, @Param("dateStamp") Long dateStamp);

  // 7 = SUBS_ACM_DAILY
  @Query(value = "SELECT VALUE FROM SUBS_ACM_DAILY WHERE SUBS_ID = :subsId AND RESOURCE_ID = :resourceId AND DATE_STAMP = :dateStamp", nativeQuery = true)
  Optional<AcmValueProjection> getAcmType7 (@Param("subsId") Long subsId, @Param("resourceId") Long resourceId, @Param("dateStamp") Long dateStamp);

}
