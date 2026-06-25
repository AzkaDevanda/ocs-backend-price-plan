package com.ocs.portal.repository;

import com.ocs.portal.dto.request.AcctResNameDto;
import com.ocs.portal.dto.response.balanceAdjustment.AcctResDto;
import com.ocs.portal.entity.AcctRes;
import com.ocs.portal.projection.accountConfig.QryAcctResListProjection;
import com.ocs.portal.projection.accountConfig.QryBalanceTypeWithMVNOProjection;
import com.ocs.portal.projection.acct.AcctBalanceTypeListProjection;
import com.ocs.portal.projection.acct.AcctResProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcctResRepository extends JpaRepository<AcctRes, Long> {
  List<AcctRes> findByRefillable (String refillable);

  // ====> Native Query
  @Query(value = "SELECT ar.ACCT_RES_ID, ar.ACCT_RES_NAME, ar.IS_CURRENCY, bt.BAL_TYPE_NAME, ar.DEFAULT_ACCT_ITEM_TYPE_ID, ar.MAX_VALUE, ut.UNIT_TYPE_NAME, ar.PRIORITY, ar.MAX_EXP_DATE, ar.RESET_ZERO, ait.ACCT_ITEM_TYPE_NAME, ar.ACM_UNIT, ar.FLOOR_LIMIT, ar.DAILY_CEIL_LIMIT, ar.DAILY_FLOOR_LIMIT, ar.OVERDRAFT_FLAG FROM ACCT_RES ar JOIN BAL_TYPE bt ON ar.BAL_TYPE = bt.BAL_TYPE LEFT JOIN ACCT_ITEM_TYPE ait ON ar.DEFAULT_ACCT_ITEM_TYPE_ID = ait.ACCT_ITEM_TYPE_ID LEFT JOIN UNIT_TYPE ut ON ar.UNIT_TYPE_ID = ut.UNIT_TYPE_ID", nativeQuery = true)
  List<Object[]> getAcctRes ();

  @Query(value = "SELECT ACCT_RES_ID, BAL_TYPE, PARENT_ACCT_RES_ID, ACCT_RES_NAME, IS_CURRENCY, COMMENTS, CREDIT_LIMIT, REMIND_DAY, REMIND_VALUE, MAX_VALUE, NVL(REFILLABLE,'N') AS REFILLABLE, PAYMENT_FORCE, STD_CODE, IS_FREE_UNIT, DEFAULT_ACCT_ITEM_TYPE_ID, SP_ID, UNIT_TYPE_ID, UNIT_PRECISION, RATIO_MONEY, RATIO_PRECISION, PRIORITY, EXTEND_RULE, MAX_ADJUST_VALUE, MAX_EXP_DATE, MAX_CHG_VALUE, RESET_ZERO, PERIOD_CLASS, ACM_TYPE, ACM_THRESHOLD, ACM_UNIT, ACM_AMOUNT, CEIL_LIMIT, FLOOR_LIMIT, DAILY_CEIL_LIMIT, DAILY_FLOOR_LIMIT, GRACE_PERIOD, MAX_ROLLOVER, USAGE_TYPE, REWARD_FLAG, UNLIMITED_FLAG, ADJUST_TYPE, BALANCE_AGGREGATION, CATEGORY, ROLLOVER_FLAG, NVL(FREE_FLAG,'Y') AS FREE_FLAG, ADJUST_FLAG, BAL_CATEGORY, CLEAR_FLAG, CLEAR_DAYS, CUST_LEVEL FROM ACCT_RES WHERE (:acctResId IS NULL OR ACCT_RES_ID = :acctResId)", nativeQuery = true)
  Optional<AcctResDto> selectAcctRes (@Param("acctResId") Long acctResId);

  @Query("SELECT new com.sts.sinorita.dto.request.AcctResNameDto(ar.acctResId, ar.acctResName) FROM AcctRes ar")
  List<AcctResNameDto> findAcctResByAcctResId ();

  @Query(value = "SELECT ACCT_RES_ID as acctResId, ACCT_RES_NAME as acctResName FROM ACCT_RES ORDER BY ACCT_RES_NAME ASC", nativeQuery = true)
  List<AcctResProjection> getAcctResName ();

  @Query(value = "SELECT 1 FROM ACCT_RES ar WHERE ar.PARENT_ACCT_RES_ID = :accResId FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
  Optional<Integer> isReferencedInAcctRes (@Param("accResId") Long accResId);

  @Query("SELECT COUNT(a) FROM AcctRes a WHERE a.acctResName = :acctResName AND a.acctResId <> :acctResId")
  int checksSameAcctResName (@Param("acctResName") String acctResName, @Param("acctResId") Long acctResId);

  @Query("SELECT COUNT(ar) FROM AcctRes ar WHERE ar.acctResName = :acctResName")
  Long checksSameAcctResName (@Param("acctResName") String acctResName);

  @Query("SELECT COUNT(ar) FROM AcctRes ar WHERE ar.stdCode = :stdCode")
  Long checksSameStandardCode (@Param("stdCode") String stdCode);

  @Query("SELECT COUNT(a) FROM AcctRes a WHERE a.stdCode = :stdCode AND a.acctResId <> :acctResId")
  int checksSameStandardCode (@Param("stdCode") String stdCode, @Param("acctResId") Long acctResId);

  @Query(value = """
          SELECT A.BAL_TYPE as balType, A.BAL_TYPE_NAME as balTypeName, B.ACCT_RES_ID as acctResId, B.ACCT_RES_NAME as acctResName, B.IS_CURRENCY as isCurrency, B.COMMENTS as comments, B.CREDIT_LIMIT as creditLimit, B.MAX_VALUE as maxValue, B.REFILLABLE as refillable, B.STD_CODE as stdCode, B.PAYMENT_FORCE as paymentForce, B.PARENT_ACCT_RES_ID as parentAcctResId, B.IS_FREE_UNIT as isFreeUnit, H.VALUE as value, H.RUM as rum, B.DEFAULT_ACCT_ITEM_TYPE_ID as defaultAcctItemType, B.STORE_UNIT as storeUnit, C.ACCT_RES_NAME AS parentAcctResName, D.ACCT_ITEM_TYPE_NAME as acctItemTypeName, B.SP_ID as spId, B.UNIT_TYPE_ID as unitTypeId, B.UNIT_PRECISION as unitPrecision, E.UNIT_TYPE_NAME as unitTypeName, B.RATIO_MONEY as ratioMoney, B.RATIO_PRECISION as ratioPrecision, B.PRIORITY as priority, DECODE(B.EXTEND_RULE, '000000', '', B.EXTEND_RULE) AS extendRule, DECODE(B.EXTEND_RULE, '210000', 'Best Rule', '100000', 'Accumulation Rule', '') AS extendRuleName, B.MAX_ADJUST_VALUE as maxAdjustValue, B.MAX_CHG_VALUE as maxChgValue, B.MAX_EXP_DATE as maxExpDate, B.RESET_ZERO as resetZero, DECODE(B.RESET_ZERO, 'Y', 'Clear bal after expired', 'N', 'Can not clear bal after expired') AS resetZeroName, B.ACM_THRESHOLD as acmThreshold, B.ACM_TYPE as acmType, B.ACM_UNIT as acmUnit, F.TIME_UNIT_NAME AS acmUnitName, B.ACM_AMOUNT as acmAmount, B.CEIL_LIMIT as ceilLimit, B.FLOOR_LIMIT as floorLimit, B.DAILY_CEIL_LIMIT as dailyCeilLimit, B.DAILY_FLOOR_LIMIT as dailyFloorLimit, B.GRACE_PERIOD as gracePeriod, B.USAGE_TYPE as usageType, B.REWARD_FLAG as rewardFlag, B.UNLIMITED_FLAG as unlimitedFlag, B.MAX_ROLLOVER as maxRollover, B.ADJUST_TYPE as adjustType, B.BALANCE_AGGREGATION as balanceAggregation, B.CATEGORY as category, B.ROLLOVER_FLAG as rolloverFlag, G.DAY_THRESHOLD AS dayThreshold, G.WEEK_THRESHOLD AS weekThreshold, G.MONTH_THRESHOLD AS monthThreshold, G.DAY_COUNT AS dayCount, G.WEEK_COUNT AS weekCount, G.MONTH_COUNT AS monthCount, G.MIN_RESIDUAL_BAL AS minResidualBal, G.MAX_ALLOWED AS maxAllowed, G.MIN_ALLOWED AS minAllowed, G.TRANSFER_FACTOR AS transferFactor, B.OVERDRAFT_FLAG as overdraftFlag, B.RESERVE_PECENTAGE as reservePercentage, B.FREE_FLAG as freeFlag, B.ADJUST_FLAG as adjustFlag, B.BAL_CATEGORY as balCategory, B.CLEAR_FLAG as clearFlag, B.CLEAR_DAYS as clearDays, B.CUST_LEVEL AS customerFlag FROM ACCT_RES B JOIN BAL_TYPE A ON B.BAL_TYPE = A.BAL_TYPE LEFT JOIN ACCT_RES C ON B.PARENT_ACCT_RES_ID = C.ACCT_RES_ID LEFT JOIN ACCT_RES_FREE H ON B.ACCT_RES_ID = H.ACCT_RES_ID LEFT JOIN ACCT_ITEM_TYPE D ON B.DEFAULT_ACCT_ITEM_TYPE_ID = D.ACCT_ITEM_TYPE_ID LEFT JOIN UNIT_TYPE E ON B.UNIT_TYPE_ID = E.UNIT_TYPE_ID LEFT JOIN TIME_UNIT F ON B.ACM_UNIT = F.TIME_UNIT LEFT JOIN TRANS_ACCT_RES_CFG G ON B.ACCT_RES_ID = G.ACCT_RES_ID WHERE(:ACCT_RES_ID IS NULL OR B.ACCT_RES_ID = :ACCT_RES_ID) AND (:ACCT_RES_NAME IS NULL OR UPPER(B.ACCT_RES_NAME) LIKE '%' || UPPER(:ACCT_RES_NAME) || '%') AND (:BAL_TYPE IS NULL OR A.BAL_TYPE = :BAL_TYPE) AND (:EXCEPT_BAL_TYPES IS NULL OR A.BAL_TYPE NOT IN (:EXCEPT_BAL_TYPES)) AND ( TO_CHAR(B.ACCT_RES_ID) IN ( SELECT CURRENT_VALUE FROM SYSTEM_PARAM WHERE MASK = 'DEFAULT_ACCT_RES_ID') OR (:SP_ID IS NULL OR NVL(B.SP_ID, 0) = :SP_ID) OR EXISTS ( SELECT 1 FROM SP_RES E WHERE B.ACCT_RES_ID = E.ACCT_RES_ID AND (:SP_ID IS NULL OR E.SP_ID = :SP_ID) ) ) AND (:REFILLABLE IS NULL OR B.REFILLABLE IN (:REFILLABLE))
          """, countQuery = """
          SELECT COUNT(*) FROM ACCT_RES B JOIN BAL_TYPE A ON B.BAL_TYPE = A.BAL_TYPE LEFT JOIN ACCT_RES C ON B.PARENT_ACCT_RES_ID = C.ACCT_RES_ID LEFT JOIN ACCT_RES_FREE H ON C.ACCT_RES_ID = H.ACCT_RES_ID LEFT JOIN ACCT_ITEM_TYPE D ON B.DEFAULT_ACCT_ITEM_TYPE_ID = D.ACCT_ITEM_TYPE_ID LEFT JOIN UNIT_TYPE E ON B.UNIT_TYPE_ID = E.UNIT_TYPE_ID LEFT JOIN TIME_UNIT F ON B.ACM_UNIT = F.TIME_UNIT LEFT JOIN TRANS_ACCT_RES_CFG G ON B.ACCT_RES_ID = G.ACCT_RES_ID WHERE(:ACCT_RES_ID IS NULL OR B.ACCT_RES_ID = :ACCT_RES_ID) AND (:BAL_TYPE IS NULL OR A.BAL_TYPE = :BAL_TYPE) AND (:EXCEPT_BAL_TYPES IS NULL OR A.BAL_TYPE NOT IN (:EXCEPT_BAL_TYPES)) AND ( TO_CHAR(B.ACCT_RES_ID) IN ( SELECT CURRENT_VALUE FROM SYSTEM_PARAM WHERE MASK = 'DEFAULT_ACCT_RES_ID') OR (:SP_ID IS NULL OR NVL(B.SP_ID, 0) = :SP_ID) OR EXISTS ( SELECT 1 FROM SP_RES E WHERE B.ACCT_RES_ID = E.ACCT_RES_ID AND (:SP_ID IS NULL OR E.SP_ID = :SP_ID) ) ) AND (:REFILLABLE IS NULL OR B.REFILLABLE IN (:REFILLABLE))
          """, nativeQuery = true)
  Page<QryBalanceTypeWithMVNOProjection> qryBalanceTypeWithMVNO (@Param("ACCT_RES_ID") Integer acctResId, @Param("ACCT_RES_NAME") String acctResName, @Param("BAL_TYPE") Integer balType, @Param("EXCEPT_BAL_TYPES") String exceptBalTypes, @Param("REFILLABLE") String refillable, @Param("SP_ID") Integer spId, Pageable pageable);

  @Query(value = "SELECT A.ACCT_RES_ID AS acctResId, A.PARENT_ACCT_RES_ID AS parentAcctResId, A.ACCT_RES_NAME AS acctResName, A.STD_CODE AS stdCode, A.IS_CURRENCY AS isCurrency, A.BAL_TYPE AS balType, A.CREDIT_LIMIT AS creditLimit, A.COMMENTS AS comments, A.REFILLABLE AS refillable FROM ACCT_RES A WHERE 1 = 1 AND(:IS_CURRENCY IS NULL OR A.IS_CURRENCY = :IS_CURRENCY) AND (:REFILLABLE IS NULL OR A.REFILLABLE = :REFILLABLE) AND (:BAL_TYPE IS NULL OR A.BAL_TYPE = :BAL_TYPE) AND (:STD_CODE IS NULL OR A.STD_CODE = :STD_CODE) AND (:ACCT_RES_NAME IS NULL OR UPPER(A.ACCT_RES_NAME) LIKE '%' || UPPER(:ACCT_RES_NAME) || '%') AND (:ACCT_RES_IDS IS NULL OR A.ACCT_RES_ID IN (:ACCT_RES_IDS)) AND (TO_CHAR(A.ACCT_RES_ID) IN ( SELECT NVL(PARAM_VALUE, DEFAULT_VALUE) FROM CONFIG_ITEM_PARAM WHERE PARAM_CODE = 'DEFAULT_ACCT_RES_ID') OR (:SP_ID IS NULL OR NVL(A.SP_ID, 0) = :SP_ID)) ORDER BY A.ACCT_RES_NAME", nativeQuery = true)
  List<QryAcctResListProjection> qryAcctResList (@Param("ACCT_RES_NAME") String acctResName, @Param("IS_CURRENCY") Character isCurrency, @Param("REFILLABLE") Character refillable, @Param("BAL_TYPE") Integer balType, @Param("STD_CODE") String stdCode, @Param("ACCT_RES_IDS") Integer acctResIds, @Param("SP_ID") Integer spId);

  @Query(value = "SELECT B.ACCT_RES_ID AS acctResId, B.ACCT_RES_NAME AS acctResName, B.IS_CURRENCY AS isCurrency FROM ACCT_RES C,  ACCT_RES B, ACCT_ITEM_TYPE D, UNIT_TYPE E, TRANS_ACCT_RES_CFG F WHERE B.PARENT_ACCT_RES_ID = C.ACCT_RES_ID(+) AND B.DEFAULT_ACCT_ITEM_TYPE_ID = D.ACCT_ITEM_TYPE_ID(+) AND B.UNIT_TYPE_ID = E.UNIT_TYPE_ID(+) AND B.ACCT_RES_ID = F.ACCT_RES_ID(+) AND(:ACCT_RES_NAME IS NULL OR UPPER(B.ACCT_RES_NAME) LIKE '%' || UPPER(:ACCT_RES_NAME) || '%')", nativeQuery = true)
  Page<AcctBalanceTypeListProjection> findAllBalType (@Param("ACCT_RES_NAME") String acctResName, Pageable pageable);

  List<AcctRes> findByRefillable (Character refillable);

  @Query(value = "SELECT ACCT_RES_ID, BAL_TYPE, PARENT_ACCT_RES_ID, ACCT_RES_NAME, IS_CURRENCY, COMMENTS, CREDIT_LIMIT, REMIND_DAY, REMIND_VALUE, MAX_VALUE, REFILLABLE, PAYMENT_FORCE, STD_CODE, IS_FREE_UNIT, DEFAULT_ACCT_ITEM_TYPE_ID, SP_ID FROM ACCT_RES WHERE REFILLABLE IN (:refillable)", nativeQuery = true)
  List<AcctResDto> selectAcctResByRefillable (@Param("refillable") List<Character> refillable);


    @Query(value = "SELECT ACCT_RES_ID as acctResId, BAL_TYPE as balType, PARENT_ACCT_RES_ID as parentAcctResId, ACCT_RES_NAME as acctResName, IS_CURRENCY as isCurrency,  COMMENTS as comments,  CREDIT_LIMIT as creditLimit,  REMIND_DAY as remindDay,  REMIND_VALUE as remindValue,  MAX_VALUE as maxValue,  NVL(REFILLABLE,'N') as refillable, PAYMENT_FORCE as paymentForce, STD_CODE as stdCode, IS_FREE_UNIT as isFreeUnit, DEFAULT_ACCT_ITEM_TYPE_ID as defaultAcctItemTypeId, SP_ID as spId,  UNIT_TYPE_ID as unitTypeId, UNIT_PRECISION as unitPrecision, RATIO_MONEY as ratioMoney, RATIO_PRECISION as ratioPrecision, PRIORITY as priority, EXTEND_RULE as extendRule, MAX_ADJUST_VALUE as maxAdjustValue, MAX_EXP_DATE as maxExpDate, MAX_CHG_VALUE as maxChgValue, RESET_ZERO as resetZero, PERIOD_CLASS as periodClass, ACM_TYPE as acmType, ACM_THRESHOLD as acmThreshold, ACM_UNIT as acmUnit, ACM_AMOUNT as acmAmount, CEIL_LIMIT as ceilLimit, FLOOR_LIMIT as floorLimit, DAILY_CEIL_LIMIT as dailyCeilLimit,   DAILY_FLOOR_LIMIT as dailyFloorLimit, GRACE_PERIOD as gracePeriod, MAX_ROLLOVER as maxRollover,  USAGE_TYPE as usageType, REWARD_FLAG as rewardFlag, UNLIMITED_FLAG as unlimitedFlag, ADJUST_TYPE as adjustType, BALANCE_AGGREGATION as balanceAggregation, CATEGORY as category, ROLLOVER_FLAG as rolloverFlag, NVL(FREE_FLAG,'Y') as freeFlag,  ADJUST_FLAG as adjustFlag, BAL_CATEGORY as balCategory, CLEAR_FLAG as clearFlag, CLEAR_DAYS as clearDays, CUST_LEVEL as customerFlag FROM ACCT_RES WHERE (:acctResId IS NULL OR ACCT_RES_ID = :acctResId)", nativeQuery = true)
    Optional<AcctResProjection> selectAcctResProjection(@Param("acctResId") Long acctResId);
}
