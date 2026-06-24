package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BalTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BalTransferRepository extends JpaRepository<BalTransfer, Long> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO BAL_TRANSFER( ACCT_BOOK_ID, IN_ACCT_BOOK_ID, SP_ID, OUT_SUBS_ID, OUT_PREFIX, OUT_ACC_NBR, IN_SUBS_ID, IN_PREFIX, IN_ACC_NBR, REF_ATTR, REVERSED_AB_ID, REVERSED_BY_AB_ID, REVERSED_IN_AB_ID, REVERSED_BY_IN_AB_ID, COMMENTS, TRANSFER_REASON_ID ) VALUES ( :acctBookId, :inAcctBookId, :spId, :outSubsId, :outPrefix, :outAccNbr, :inSubsId, :inPrefix, :inAccNbr, :refAttr, :reversedAbId, :reversedByAbId, :reversedInAbId, :reversedByInAbId, :comments, :transferReasonId )", nativeQuery = true)
  void insertBalTransfer (@Param("acctBookId") Long acctBookId, @Param("inAcctBookId") Long inAcctBookId, @Param("spId") Long spId, @Param("outSubsId") Long outSubsId, @Param("outPrefix") String outPrefix, @Param("outAccNbr") String outAccNbr, @Param("inSubsId") Long inSubsId, @Param("inPrefix") String inPrefix, @Param("inAccNbr") String inAccNbr, @Param("refAttr") String refAttr, @Param("reversedAbId") Long reversedAbId, @Param("reversedByAbId") Long reversedByAbId, @Param("reversedInAbId") Long reversedInAbId, @Param("reversedByInAbId") Long reversedByInAbId, @Param("comments") String comments, @Param("transferReasonId") Long transferReasonId);
}
