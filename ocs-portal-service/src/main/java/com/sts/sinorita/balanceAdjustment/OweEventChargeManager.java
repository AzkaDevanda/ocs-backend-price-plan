package com.sts.sinorita.balanceAdjustment;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sts.sinorita.dto.request.balanceAdjustment.QryAcctAttrValueByCodeRequestDto;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.BalShareRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.StringUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OweEventChargeManager {

  private final AcctInfoServBllService acctInfoServBllService;

  private final BalShareRepository balShareRepository;

  private final EntityManager entityManager;

  boolean fromCC;

  String baseTable;

  // private static final String RECURRING_FEE_MSG_FORMAT_ACCT =
  // "20={0},3={1},211={2},1000={3}";

  // private static final String RECURRING_FEE_MSG_FORMAT_SUBS =
  // "20={0},3={1},201={2},1000={3}";

  private final ConfigItemRepository configItemRepository;

  public String prepareMsg4Acct(Long paramLong1, Long paramLong2, Long paramLong3, String paramString) {
    // if (paramLong3 == null)
    // paramLong3 = RoutingHelper.getRoutingIdByAcctId(paramLong1);
    // IDealOweEventChargeDAO iDealOweEventChargeDAO = getDao(paramLong3);
    // String str1 =
    // BillingHelper.getStringFromConfig("ACCT.PAYMENT.ENTEL_QRY_OWE_EVENT_STATE",
    // "0");
    String str1 = configItemRepository.findConfigItem("ACCT", "PAYMENT", "ENTEL_QRY_OWE_EVENT_STATE")
        .map(ConfigItemParamProjection::getParamValue).orElse("0");
    List<String> states = Arrays.stream(str1.split(","))
        .map(String::trim)
        .toList();
    Long long_ = qryOweEventChargeNew(paramLong1, states, this.baseTable);
    boolean bool = (long_ != null && long_.longValue() > 0L) ? true : false;
    String str2 = null;
    if (bool)
      str2 = StringUtil.stringFormat("20={0},3={1},211={2},1000={3}",
          (Object[]) new String[] { StringUtil.toString(paramLong2), paramString, paramLong1

              .toString(),
              StringUtil.toString(paramLong3) });
    log.debug("first message: {}", new Object[] { str2 });
    return str2;
  }

  public void prepareAndSend4BalShare(Long paramLong, String paramString) {
    // String str1 =
    // BillingHelper.getStringFromConfig("ACCT.FEE.QRY_OWE_CHARGE_FOR_BAL_SHARE",
    // "N");
    String str1 = configItemRepository.findConfigItem("ACCT", "FEE", "QRY_OWE_CHARGE_FOR_BAL_SHARE")
        .map(ConfigItemParamProjection::getParamValue).orElse("N");
    if (!"Y".equals(str1))
      return;
    QryAcctAttrValueByCodeRequestDto dynamicDict = new QryAcctAttrValueByCodeRequestDto();
    dynamicDict.setAcctId(paramLong);
    dynamicDict.setAcctAttrCode("EXT_ALLOW_SHARE_BAL");
    // dynamicDict.serviceName = "QryAcctAttrValueByCode";
    acctInfoServBllService.qryAcctAttrValueByCode(dynamicDict);
    if (dynamicDict != null && "N".equals(dynamicDict.getAttrValue())) {
      log.debug("Acct:[{}] is not allowed to bal share.");
      return;
    }
    // IBalShareDAO iBalShareDAO = (IBalShareDAO)
    // DAOFactory.createRouteMaster("BalShare", JdbcUtil4BC.getDbIdentifier());
    List<Long> list = balShareRepository.selectAllSubsId(paramLong);
    if (CommonUtil.isEmpty(list)) {
      log.debug("query bal share subsList from acct:[{}] is empty.", new Object[] { paramLong });
      return;
    }
    String str2 = null;
    for (Long long_ : list) {
      str2 = prepare(paramString, long_);
      if (StringUtil.isEmpty(str2))
        continue;
      // ZxosHelper.supplementCharge(str2);
    }
  }

  private String prepare(String paramString, Long paramLong) {
    // Long long_1 = RoutingHelper.getRoutingIdBySubsId(paramLong);
    // IDealOweEventChargeDAO iDealOweEventChargeDAO = getDao(long_1);
    String str1 = configItemRepository.findConfigItem("ACCT", "PAYMENT", "ENTEL_QRY_OWE_EVENT_STATE")
        .map(ConfigItemParamProjection::getParamValue).orElse("0");
    List<String> states = Arrays.stream(str1.split(","))
        .map(String::trim)
        .filter(s -> !s.isBlank())
        .toList();
    Long long_2 = qryOweEventChargeBySubsNew(paramLong, states, this.baseTable);
    boolean bool = (long_2 != null && long_2.longValue() > 0L) ? true : false;
    if (!bool)
      return null;
    String str2 = StringUtil.stringFormat("20={0},3={1},201={2},1000={3}",
        (Object[]) new String[] { "", paramString, paramLong

            .toString() });
    log.debug(str2);
    return str2;
  }

  private Long qryOweEventChargeNew(
      Long acctId,
      List<String> states,
      String baseTable) {

    validateTableName(baseTable);

    String sql = """
        SELECT NVL(SUM(A.CHARGE),0) AS TOTAL_CHARGE
        FROM %s A
        WHERE A.ACCT_ID = :acctId
        AND A.STATE IN (:states)
        """.formatted(baseTable);

    Query query = entityManager.createNativeQuery(sql);
    query.setParameter("acctId", acctId);
    query.setParameter("states", states);

    Number result = (Number) query.getSingleResult();

    return result != null ? result.longValue() : 0L;
  }

  private void validateTableName(String tableName) {
    if (tableName == null ||
        !tableName.matches("^[A-Z0-9_]+$")) {
      throw new IllegalArgumentException("Invalid table name");
    }
  }

  public Long qryOweEventChargeBySubsNew(
      Long subsId,
      List<String> states,
      String baseTable) {

    validateTableName(baseTable);

    String sql = """
        SELECT NVL(SUM(A.CHARGE),0) AS TOTAL_CHARGE
        FROM %s A
        WHERE A.SUBS_ID = :subsId
          AND A.STATE IN (:states)
        """.formatted(baseTable);

    Query query = entityManager.createNativeQuery(sql);
    query.setParameter("subsId", subsId);
    query.setParameter("states", states);

    Number result = (Number) query.getSingleResult();

    return result != null ? result.longValue() : 0L;
  }
}
