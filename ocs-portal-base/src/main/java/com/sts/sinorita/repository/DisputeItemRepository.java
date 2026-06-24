package com.sts.sinorita.repository;

import com.sts.sinorita.entity.DisputeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DisputeItemRepository extends JpaRepository<DisputeItem, Long> {

  @Transactional
  @Modifying
  @Query(value = """
    INSERT INTO DISPUTE_ITEM
    (ACCT_ITEM_ID, DISPUTE_ID, SP_ID)
    VALUES (:acctItemId, :disputeId, :spId)
    """, nativeQuery = true)
  int insertDisputeItem (
    @Param("acctItemId") Long acctItemId,
    @Param("disputeId") Long disputeId,
    @Param("spId") Long spId
  );

}
