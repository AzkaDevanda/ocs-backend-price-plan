package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcctResService {
  private final JdbcTemplate jdbcTemplate;

  public AcctResDto getAcctResByCode (String stdCode, Long spId) {
    StringBuilder sql = new StringBuilder("""
            SELECT ACCT_RES_ID AS acctResId, BAL_TYPE AS balType, PARENT_ACCT_RES_ID AS parentAcctResId, ACCT_RES_NAME AS acctResName, IS_CURRENCY AS isCurrency, COMMENTS AS comments, CREDIT_LIMIT AS creditLimit, REMIND_DAY AS remindDay, REMIND_VALUE AS remindValue, MAX_VALUE AS maxValue, NVL(REFILLABLE,'N') AS refillable, PAYMENT_FORCE AS paymentForce, STD_CODE AS stdCode, IS_FREE_UNIT AS isFreeUnit, DEFAULT_ACCT_ITEM_TYPE_ID AS defaultAcctItemTypeId, SP_ID AS spId, UNIT_TYPE_ID AS unitTypeId, UNIT_PRECISION AS unitPrecision, EXTEND_RULE AS extendRule, MAX_ADJUST_VALUE AS maxAdjustValue, MAX_EXP_DATE AS maxExpDate, MAX_CHG_VALUE AS maxChgValue, RESET_ZERO AS resetZero, PERIOD_CLASS AS periodClass, ACM_TYPE AS acmType, ACM_THRESHOLD AS acmThreshold, ACM_UNIT AS acmUnit, ACM_AMOUNT AS acmAmount, USAGE_TYPE AS usageType, REWARD_FLAG AS rewardFlag, UNLIMITED_FLAG AS unlimitedFlag, ADJUST_TYPE AS adjustType, BALANCE_AGGREGATION AS balanceAggregation, CATEGORY AS category, ROLLOVER_FLAG AS rolloverFlag, NVL(FREE_FLAG,'Y') AS freeFlag, ADJUST_FLAG AS adjustFlag, BAL_CATEGORY AS balCategory, CLEAR_FLAG AS clearFlag, CLEAR_DAYS AS clearDays FROM ACCT_RES WHERE STD_CODE = ?
            """);

    List<Object> params = new ArrayList<>();
    params.add(stdCode);

    if (spId != null) {
      sql.append(" AND NVL(SP_ID, 0) = ?");
      params.add(spId);
    }

    List<AcctResDto> results = jdbcTemplate.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(AcctResDto.class),
            params.toArray()
    );

    return results.isEmpty() ? null : results.get(0);
  }

  public AcctResDto getAcctResById (Long acctResId) {
    String sql = """
            SELECT ACCT_RES_ID AS acctResId, BAL_TYPE AS balType, PARENT_ACCT_RES_ID AS parentAcctResId, ACCT_RES_NAME AS acctResName, IS_CURRENCY AS isCurrency, COMMENTS AS comments, CREDIT_LIMIT AS creditLimit, REMIND_DAY AS remindDay, REMIND_VALUE AS remindValue, MAX_VALUE AS maxValue, REFILLABLE AS refillable, PAYMENT_FORCE AS paymentForce, STD_CODE AS stdCode, IS_FREE_UNIT AS isFreeUnit, DEFAULT_ACCT_ITEM_TYPE_ID AS defaultAcctItemTypeId, SP_ID AS spId FROM ACCT_RES WHERE ACCT_RES_ID = ?
            """;

    return jdbcTemplate.queryForObject(
            sql,
            new BeanPropertyRowMapper<>(AcctResDto.class),
            acctResId
    );
  }
}
