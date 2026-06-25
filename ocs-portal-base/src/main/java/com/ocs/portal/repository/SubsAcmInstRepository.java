package com.ocs.portal.repository;

import com.ocs.portal.entity.SubsAcmInst;
import com.ocs.portal.entity.SubsAcmInstId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SubsAcmInstRepository extends JpaRepository<SubsAcmInst, SubsAcmInstId> {

  @Modifying
  @Transactional
  @Query(
    value = """
          INSERT INTO SUBS_ACM_INST
          (SUBS_ID, RESOURCE_ID, INST_ID, VALUE)
          VALUES (:subsId, :resourceId, :instId, :value)
      """,
    nativeQuery = true
  )
  void insertSubsAcmInst (
    @Param("subsId") Long subsId,
    @Param("resourceId") Long resourceId,
    @Param("instId") Long instId,
    @Param("value") Long value
  );

  @Transactional
  @Modifying
  @Query(value = "UPDATE SUBS_ACM_INST SET VALUE = VALUE + :value WHERE SUBS_ID = :subsId AND RESOURCE_ID = :resourceId AND INST_ID = :instId", nativeQuery = true)
  int updateSubsAcmInst (@Param("value") Long value, @Param("subsId") Long subsId, @Param("resourceId") Long resourceId, @Param("instId") Long instId);

}
