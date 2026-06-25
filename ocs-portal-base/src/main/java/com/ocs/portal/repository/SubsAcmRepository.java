package com.ocs.portal.repository;

import com.ocs.portal.entity.embeddable.SubsAcmId;
import com.ocs.portal.entity.mdbtt.SubsAcm;
import com.ocs.portal.projection.balanceAdjustment.AcmValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SubsAcmRepository extends JpaRepository<SubsAcm, SubsAcmId> {

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO SUBS_ACM (SUBS_ID, RESOURCE_ID, VALUE) VALUES (:subsId, :resourceId, :value)", nativeQuery = true)
  void insertSubsAcm (Long subsId, Long resourceId, Long value);

  @Transactional
  @Modifying
  @Query(value = "UPDATE SUBS_ACM SET VALUE = NVL(VALUE,0) + :increaseValue WHERE SUBS_ID = :subsId AND RESOURCE_ID = :resourceId", nativeQuery = true)
  int updateSubsAcm (@Param("increaseValue") Long increaseValue, @Param("subsId") Long subsId, @Param("resourceId") Long resourceId);

  // 2 = SUBS_ACM
  @Query(value = "SELECT VALUE FROM SUBS_ACM WHERE SUBS_ID = :subsId AND RESOURCE_ID = :resourceId", nativeQuery = true)
  Optional<AcmValueProjection> getAcmType2 (@Param("subsId") Long subsId, @Param("resourceId") Long resourceId);

}
