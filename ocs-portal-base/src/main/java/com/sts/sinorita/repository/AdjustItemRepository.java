package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AdjustItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdjustItemRepository extends JpaRepository<AdjustItem, Long> {
  @Transactional
  @Modifying
  @Query(value = """
    INSERT INTO ADJUST_ITEM(
        ACCT_ITEM_ID,
        ADJUST_ID,
        REF_ACCT_ITEM_ID,
        SP_ID,
        REF_BILL_ID,
        COMMENTS
    ) VALUES (:acctItemId, :adjustId, :refAcctItemId, :spId, :refBillId, :comments)
    """, nativeQuery = true)
  void insertAdjustItem (
    @Param("acctItemId") Long acctItemId,
    @Param("adjustId") Long adjustId,
    @Param("refAcctItemId") Long refAcctItemId,
    @Param("spId") Long spId,
    @Param("refBillId") Long refBillId,
    @Param("comments") String comments
  );
}
