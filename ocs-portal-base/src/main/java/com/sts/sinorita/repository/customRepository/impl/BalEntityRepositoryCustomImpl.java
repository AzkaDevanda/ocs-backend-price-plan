package com.sts.sinorita.repository.customRepository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.sts.sinorita.entity.Bal;
import com.sts.sinorita.entity.mdbtt.BalEntity;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.customRepository.BalEntityRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BalEntityRepositoryCustomImpl implements BalEntityRepositoryCustom {
  @PersistenceContext
  private EntityManager em;

  private final ConfigItemRepository configItemRepository;

  @SuppressWarnings("unused")
  @Override
  public List<BalEntity> selectBalListFilterExpireExceptRefillY(Long acctId, Long acctResId) {
    Assert.notNull(acctId, "acctId must not be null");

    StringBuilder sql = new StringBuilder("""
            SELECT *
            FROM MDBTT.BAL
            WHERE ACCT_ID = :acctId
        """);

    if (acctResId != null) {
      sql.append(" AND ACCT_RES_ID = :acctResId ");
    }

    // String acctResIdStr = AccountHelper.getRefillaableYAcctRes();
    String acctResIdStr = null; // TODO: get from config or helper
    

    if (acctResIdStr != null && !acctResIdStr.isEmpty()) {

      String expDateCondition = "(EXP_DATE > SYSDATE OR EXP_DATE IS NULL)";

      Optional<ConfigItemParamProjection> findOffsetFlag = configItemRepository.findConfigItem("ACCT", "ACCOUNT_PUBLIC", "IS_FILTER_BAL_BY_ABS_EXP_OFFSET");
      String offsetFlag = findOffsetFlag.isPresent() ? findOffsetFlag.get().getParamValue() : "N";

      if ("Y".equals(offsetFlag)) {
        expDateCondition = """
                (
                  (ABS_EXP_OFFSET IS NULL AND (EXP_DATE > SYSDATE OR EXP_DATE IS NULL))
                  OR
                  (ABS_EXP_OFFSET IS NOT NULL
                    AND EFF_DATE + ABS_EXP_OFFSET / 86400 > SYSDATE)
                )
            """;
      }

      sql.append("""
              AND (
                  (ACCT_RES_ID NOT IN (%s) AND %s)
                  OR ACCT_RES_ID IN (%s)
              )
          """.formatted(acctResIdStr, expDateCondition, acctResIdStr));
    }

    Query query = em.createNativeQuery(sql.toString(), BalEntity.class);
    query.setParameter("acctId", acctId);

    if (acctResId != null) {
      query.setParameter("acctResId", acctResId);
    }

    return query.getResultList();
  }

}
