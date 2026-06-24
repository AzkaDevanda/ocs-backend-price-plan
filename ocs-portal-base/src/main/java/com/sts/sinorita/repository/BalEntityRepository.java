package com.sts.sinorita.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.mdbtt.BalEntity;
import com.sts.sinorita.projection.balanceAdjustment.SelectAllBalListByAcctIdExtRawProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectAllBalListByAcctIdProjection;
import com.sts.sinorita.repository.customRepository.BalEntityRepositoryCustom;

import jakarta.transaction.Transactional;

@Repository
public interface BalEntityRepository extends JpaRepository<BalEntity, Long>, BalEntityRepositoryCustom {

  @Query("SELECT b FROM BalEntity  b WHERE b.acctId = :acctId AND ((:acctResIds) IS NULL OR b.acctResId IN (:acctResIds)) AND ( :refillableYIdsEmpty = true OR ( (b.acctResId NOT IN :refillableYIds AND (b.expDate > CURRENT_TIMESTAMP OR b.expDate IS NULL)) OR b.acctResId IN :refillableYIds ) )")
  List<BalEntity> selectBalListFilterExpireExceptRefillY(@Param("acctId") Long acctId, @Param("acctResIds") List<Long> acctResId, @Param("refillableYIds") List<Long> refillableYIds, @Param("refillableYIdsEmpty") boolean refillableYIdsEmpty);

  @Query(value = "SELECT BAL_ID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
  Long getNextBalId();

  @Query(value = "SELECT ACCT_ID AS acctId, ACCT_RES_ID AS acctResId, GROSS_BAL AS grossBal, CONSUME_BAL AS consumeBal, RATING_BAL AS ratingBal, BILLING_BAL AS billingBal, EFF_DATE AS effDate, EXP_DATE AS expDate, UPDATE_DATE AS updateDate, CEIL_LIMIT AS ceilLimit, FLOOR_LIMIT AS floorLimit, SUBS_ID AS subsId, DAILY_CEIL_LIMIT AS dailyCeilLimit, DAILY_FLOOR_LIMIT AS dailyFloorLimit, PRIORITY AS priority, INIT_BAL AS initBal, BAL_ID AS balId, VAR_CEIL_LIMIT AS varCeilLimit, ABS_EXP_OFFSET AS absExpOffset FROM BAL WHERE ACCT_ID = :acctId AND (:subsId IS NULL OR SUBS_ID = :subsId OR SUBS_ID = -1)", nativeQuery = true)
  List<SelectAllBalListByAcctIdExtRawProjection> selectAllBalListByAcctIdExtRaw(@Param("acctId") Long acctId, @Param("subsId") Long subsId);

  @Query(value = "SELECT ACCT_ID AS acctId, ACCT_RES_ID AS acctResId, NVL(GROSS_BAL, 0) AS grossBal, NVL(CONSUME_BAL, 0) AS consumeBal, NVL(RATING_BAL, 0) AS ratingBal, NVL(BILLING_BAL, 0) AS billingBal, EFF_DATE AS effDate, EXP_DATE AS expDate, UPDATE_DATE AS updateDate, CEIL_LIMIT AS ceilLimit, FLOOR_LIMIT AS floorLimit, SUBS_ID AS subsId, DAILY_CEIL_LIMIT AS dailyCeilLimit, DAILY_FLOOR_LIMIT AS dailyFloorLimit, PRIORITY AS priority, NVL(INIT_BAL, 0) AS initBal, BAL_ID AS balId, VAR_CEIL_LIMIT AS varCeilLimit FROM BAL WHERE ACCT_ID = :acctId AND (:subsId IS NULL OR SUBS_ID = :subsId OR SUBS_ID = -1)", nativeQuery = true)
  List<SelectAllBalListByAcctIdProjection> selectAllBalListByAcctId(@Param("acctId") Long acctId, @Param("subsId") Long subsId);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO MDBTT.BAL ( ACCT_ID, ACCT_RES_ID, GROSS_BAL, CONSUME_BAL, RATING_BAL, BILLING_BAL, EFF_DATE, EXP_DATE, UPDATE_DATE, CREATED_DATE, CEIL_LIMIT, FLOOR_LIMIT, DAILY_CEIL_LIMIT, DAILY_FLOOR_LIMIT, PRIORITY, LAST_BAL, LAST_RECHARGE, BAL_USED, INIT_BAL, SUBS_ID, BAL_ID, ABS_EXP_OFFSET, BAL_FLAGS, CUST_ID ) VALUES ( :ACCT_ID, :ACCT_RES_ID, :GROSS_BAL, :CONSUME_BAL, :RATING_BAL, :BILLING_BAL, :EFF_DATE, :EXP_DATE, :UPDATE_DATE, :CREATED_DATE, :CEIL_LIMIT, :FLOOR_LIMIT, :DAILY_CEIL_LIMIT, :DAILY_FLOOR_LIMIT, :PRIORITY, :LAST_BAL, :LAST_RECHARGE, :BAL_USED, :INIT_BAL, :SUBS_ID, :BAL_ID, :ABS_EXP_OFFSET, :BAL_FLAGS, :CUST_ID )", nativeQuery = true)
  void insertBal(@Param("ACCT_ID") Long acctId, @Param("ACCT_RES_ID") Long acctResId, @Param("GROSS_BAL") Long grossBal, @Param("CONSUME_BAL") Long consumeBal, @Param("RATING_BAL") Long ratingBal, @Param("BILLING_BAL") Long billingBal, @Param("EFF_DATE") LocalDateTime effDate, @Param("EXP_DATE") LocalDateTime expDate, @Param("UPDATE_DATE") LocalDateTime updateDate, @Param("CREATED_DATE") LocalDateTime createdDate, @Param("CEIL_LIMIT") Long ceilLimit, @Param("FLOOR_LIMIT") Long floorLimit, @Param("DAILY_CEIL_LIMIT") Long dailyCeilLimit, @Param("DAILY_FLOOR_LIMIT") Long dailyFloorLimit, @Param("PRIORITY") Long priority, @Param("LAST_BAL") Long lastBal, @Param("LAST_RECHARGE") Long lastRecharge, @Param("BAL_USED") Long balUsed, @Param("INIT_BAL") Long initBal, @Param("SUBS_ID") Long subsId, @Param("BAL_ID") Long balId, @Param("ABS_EXP_OFFSET") Long absExpOffset, @Param("BAL_FLAGS") String balFlags, @Param("CUST_ID") Long custId);

  @Transactional
  @Modifying
  @Query(value = "UPDATE MDBTT.BAL SET GROSS_BAL = GROSS_BAL + :CHARGE, INIT_BAL = CASE WHEN :INIT_BAL IS NOT NULL THEN :INIT_BAL ELSE INIT_BAL END, EFF_DATE = ADD_SECONDS(EFF_DATE, :EFF_SECONDS), UPDATE_DATE = CASE WHEN :UPDATE_DATE IS NOT NULL THEN :UPDATE_DATE ELSE SYSDATE END, EXP_DATE = CASE WHEN :EXP_DATE IS NOT NULL THEN CASE WHEN EXP_DATE IS NOT NULL THEN ADD_SECONDS(EXP_DATE, :SECONDS) ELSE :EXP_DATE END ELSE EXP_DATE END, ABS_EXP_OFFSET = CASE WHEN :ABS_EXP_OFFSET IS NULL THEN ABS_EXP_OFFSET WHEN :ABS_EXP_OFFSET = -1 THEN NULL ELSE :ABS_EXP_OFFSET END, CEIL_LIMIT = CASE WHEN :CEIL_LIMIT_CHARGE IS NOT NULL THEN NVL(CEIL_LIMIT, 0) + :CEIL_LIMIT_CHARGE ELSE CEIL_LIMIT END, CONSUME_BAL = CASE WHEN :CONSUME_BAL_CHARGE IS NOT NULL THEN CONSUME_BAL + :CONSUME_BAL_CHARGE ELSE CONSUME_BAL END WHERE BAL_ID = :BAL_ID", nativeQuery = true)
  void updateBal(@Param("CHARGE") Long charge, @Param("INIT_BAL") Long initBal, @Param("EFF_SECONDS") Long effSeconds, @Param("UPDATE_DATE") LocalDateTime updateDate, @Param("EXP_DATE") LocalDateTime expDate, @Param("SECONDS") Long seconds, @Param("ABS_EXP_OFFSET") Long absExpOffset, @Param("CEIL_LIMIT_CHARGE") Long ceilLimitCharge, @Param("CONSUME_BAL_CHARGE") Long consumeBalCharge, @Param("BAL_ID") Long balId);
}
