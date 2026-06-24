package com.sts.sinorita.repository;

import com.sts.sinorita.entity.embeddable.BalShareHisId;
import com.sts.sinorita.entity.mdbtt.BalShareHis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BalShareHisRepository extends JpaRepository<BalShareHis, BalShareHisId> {
  @Transactional
  @Modifying
  @Query(value = "UPDATE BAL_SHARE_HIS SET BILL_BAL = CASE WHEN :BILL_BAL IS NOT NULL THEN BILL_BAL + :BILL_BAL ELSE BILL_BAL END, DAILY_BAL = CASE WHEN :DAILY_BAL IS NOT NULL THEN DAILY_BAL + :DAILY_BAL ELSE DAILY_BAL END WHERE BAL_SHARE_ID = :BAL_SHARE_ID AND DATE_STAMP = :DATE_STAMP", nativeQuery = true)
  int updateBalShareHis (@Param("BILL_BAL") Long billBal, @Param("DAILY_BAL") Long dailyBal, @Param("BAL_SHARE_ID") Long balShareId, @Param("DATE_STAMP") Long dateStamp);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO BAL_SHARE_HIS (BAL_SHARE_ID, DATE_STAMP, BILL_BAL, DAILY_BAL) VALUES (:BAL_SHARE_ID, :DATE_STAMP, :BILL_BAL, :DAILY_BAL)", nativeQuery = true)
  void insertBalShareHis (@Param("BAL_SHARE_ID") Long balShareId, @Param("DATE_STAMP") Long dateStamp, @Param("BILL_BAL") Long billBal, @Param("DAILY_BAL") Long dailyBal);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO BAL_HIS (BAL_ID, DATE_STAMP, DAILY_BAL, BILL_BAL) VALUES (:balId, :dateStamp, :dailyBal, :billBal)", nativeQuery = true)
  void insertBalHis (@Param("balId") Long balId, @Param("dateStamp") Long dateStamp, @Param("dailyBal") Long dailyBal, @Param("billBal") Long billBal);

  @Modifying
  @Transactional
  @Query(value = "UPDATE BAL_HIS SET DAILY_BAL = :dailyBal, BILL_BAL = NVL(BILL_BAL, 0) + :billBal WHERE BAL_ID = :balId AND DATE_STAMP = :dateStamp", nativeQuery = true)
  int updateBalHis (
    @Param("dailyBal") Long dailyBal,
    @Param("billBal") Long billBal,
    @Param("balId") Long balId,
    @Param("dateStamp") Long dateStamp
  );
}
