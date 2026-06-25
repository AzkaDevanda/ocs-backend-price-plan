package com.ocs.portal.repository;

import com.ocs.portal.entity.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DisputeRepository extends JpaRepository<Dispute, Long> {
  @Transactional
  @Modifying
  @Query(value = """
    INSERT INTO DISPUTE
    (DISPUTE_ID, PROCESSED_DISPUTE_ID, COMMENTS, SP_ID)
    VALUES (:disputeId, :processedDisputeId, :comments, :spId)
    """, nativeQuery = true)
  void insertDispute (
    @Param("disputeId") Long disputeId,
    @Param("processedDisputeId") Long processedDisputeId,
    @Param("comments") String comments,
    @Param("spId") Long spId
  );


}
