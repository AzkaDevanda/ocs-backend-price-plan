package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Adjust;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdjustRepository extends JpaRepository<Adjust, Long> {

  @Modifying
  @Query(value = "INSERT INTO ADJUST (ADJUST_ID, ADJUST_REASON_ID, COMMENTS, SP_ID, REVERSED_ADJUST_ID, SUBS_ID) VALUES (:adjustId, :adjustReasonId, :comments, :spId, :reversedAdjustId, :subsId)", nativeQuery = true)
  void balAdjustStore (@Param("adjustId") Long adjustId, @Param("adjustReasonId") Long adjustReasonId, @Param("comments") String comments, @Param("spId") Long spId, @Param("reversedAdjustId") Long reversedAdjustId, @Param("subsId") Long subsId);

  @Modifying
  @Transactional
  @Query(value = """
    UPDATE ADJUST
    SET REVERSED_BY_ADJUST_ID = :reversedByAdjustId
    WHERE ADJUST_ID = :adjustId
    """, nativeQuery = true)
  void updateReverseAdjust (@Param("reversedByAdjustId") Long reversedByAdjustId, @Param("adjustId") Long adjustId);


}
