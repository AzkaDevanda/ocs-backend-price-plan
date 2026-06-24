package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AdjustNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdjustNoteRepository extends JpaRepository<AdjustNote, String> {
  @Query(value = "SELECT COUNT(1) FROM ADJUST_NOTE T WHERE T.VOUCHER = :voucher AND T.STATE = :state", nativeQuery = true)
  int qryAdjustNoteByVoucherAndState (@Param("voucher") String voucher, @Param("state") String state);

  @Transactional
  @Modifying
  @Query(value = "UPDATE ADJUST_NOTE T SET T.STATE_DATE = SYSDATE, T.STATE = :state WHERE T.VOUCHER = :voucher AND (:oriState IS NULL OR T.STATE = :oriState)", nativeQuery = true)
  void updateAdjustNoteByVoucherAndState (@Param("voucher") String voucher, @Param("state") String state, @Param("oriState") String oriState);

}
