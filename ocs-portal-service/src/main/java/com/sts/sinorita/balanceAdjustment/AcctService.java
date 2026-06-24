package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.entity.Acct;
import com.sts.sinorita.entity.Bal;
import com.sts.sinorita.entity.embeddable.ReserveBalId;
import com.sts.sinorita.entity.mdbtt.ReserveBal;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BillingHelper;
import com.sts.sinorita.projection.acct.SelectAcctDtoByAcctIdProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectAllBalListByAcctIdExtRawProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectAllBalListByAcctIdProjection;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.AcctRepository;
import com.sts.sinorita.repository.BalEntityRepository;
import com.sts.sinorita.repository.BalRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcctService {
  private final Logger log = LoggerFactory.getLogger(AcctService.class);
  private final ConfigItemRepository configItemRepository;
  private final AcctRepository acctRepository;
  private final BalEntityRepository balRepository;
  private final AcctResService acctResService;
  private BalHelper balHelper;
  private final BillingHelper billingHelper;
  @PersistenceContext
  private EntityManager entityManager;

  public static <T> boolean isNotEmpty(T[] array) {
    return array != null && array.length != 0;
  }

  public Optional<SelectAcctDtoByAcctIdProjection> selectAcctDtoByAcctId(Long acctId, boolean lockFlag) {

    if (acctId == null) {
      throw new IllegalArgumentException("acctId must not be null");
    }

    return lockFlag
        ? acctRepository.selectAcctDtoByAcctIdLockFlagTrue(acctId)
        : acctRepository.selectAcctDtoByAcctIdLockFlagFalse(acctId);
  }

  public Acct qryAcctByAcctId(Long acctId, Boolean lockFlag, Boolean isLockRetryFlag) {
    Acct acct = null;
    if (null != isLockRetryFlag && isLockRetryFlag.booleanValue()) {
      Optional<ConfigItemParamProjection> findRetryTimes = configItemRepository.findConfigItem("ACCT", "PAYMENT",
          "LOCK_RETRY_TIMES_FOR_RECHARGE");
      Long retryTimes = Long.valueOf(findRetryTimes
          .map(ConfigItemParamProjection::getParamValue)
          .orElse(""));
      acct = qryAcct4LockRetry(acctId, null, retryTimes);
    } else {
      acct = qryAcctByAcctId(acctId, lockFlag);
    }
    return acct;
  }

  private Acct qryAcct4LockRetry(Long acctId, String acctNbr, Long retryTimes) {
    Acct acct = null;
    try {
      if (null != acctId) {
        acct = qryAcctByAcctId(acctId, Boolean.valueOf(true));
      } else if (!acctNbr.isEmpty()) {
        acct = qryAcctByAcctNbr(acctNbr, Boolean.valueOf(true));
      }
    } catch (ResponseStatusException e) {
      if ("BL-S-ACT-00358".equals(e.getMessage()))
        try {
          Optional<ConfigItemParamProjection> findOffsetTime = configItemRepository.findConfigItem("ACCT", "PAYMENT",
              "LOCK_RETRY_OFFSET_TIME");
          int offsetTime = Integer
              .parseInt(Objects.requireNonNull(findOffsetTime.map(ConfigItemParamProjection::getDefaultValue)
                  .orElse(null)));
          int randomTime = (int) (Math.random() * 100.0D + offsetTime);
          log.debug("The randomTime is {}", Integer.valueOf(randomTime));
          Thread.sleep(randomTime);
          if (retryTimes.longValue() > 0L) {
            Long long_1 = retryTimes, long_2 = retryTimes = Long.valueOf(retryTimes.longValue() - 1L);
            log.debug("{} times to query the account has left.", retryTimes);
            return qryAcct4LockRetry(acctId, acctNbr, retryTimes);
          }
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00341"));
          // ExceptionHandler.publish("BL-S-ACT-00341", 0, (Throwable) e);
        } catch (Exception ex) {
          // ExceptionHandler.publish("BL-S-ACT-00341", 0, ex);
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00341"));
        }
    }
    return acct;
  }

  public Acct qryAcctByAcctNbr(String acctNbr, Boolean lockFlag) {
    Acct acct = null;
    try {
      acct = selectAcctByAcctNbr(acctNbr, lockFlag);
    } catch (Exception e) {
      if (e.getCause() instanceof java.sql.SQLException) {
        log.warn("The error stack is {}", e);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00358"));
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00359"));
      }
    }
    return acct;
  }

  public Acct selectAcctByAcctNbr(String acctNbr, Boolean lockFlag) {
    String sql = """
        SELECT A.ACCT_ID, A.ACCT_NAME, A.STD_ADDR_ID, A.BILL_ADDRESS, A.CUST_ID, A.ACCT_NBR, A.BILLING_CYCLE_TYPE_ID, A.PAYMENT_TYPE, A.BANK_ID, A.BANK_ACCT_NBR, A.BANK_ACCT_NAME, A.CREATED_DATE, A.UPDATE_DATE, A.STATE, A.STATE_DATE, A.POSTPAID, A.ROUTING_ID, A.BILL_FORMAT_ID, A.SP_ID, A.DEFAULT_FLAG, A.PAYMENT_METHOD_ID, A.IS_LOCK, A.PARTY_TYPE, A.PARTY_CODE, A.BANK_ACCT_EXP_DATE, A.BANK_CARD_TYPE, A.NEED_UPLOAD, A.PAYMENT_COMMENTS, B.CUST_NAME, A.BILL_FLAG FROM ACCT A JOIN CUST B ON A.CUST_ID = B.CUST_ID WHERE A.ACCT_NBR = :acctNbr
        """;

    if (Boolean.TRUE.equals(lockFlag)) {
      sql += " FOR UPDATE OF A.ACCT_ID NOWAIT";
    }

    Query query = entityManager.createNativeQuery(sql, Acct.class);
    query.setParameter("acctNbr", acctNbr);

    List<Acct> results = query.getResultList();
    return results.isEmpty() ? null : results.get(0);
  }

  public Acct selectAcctByAcctId(Long acctId, Boolean lockFlag) {
    String sql = """
        SELECT A.ACCT_ID, A.STD_ADDR_ID, A.BILL_ADDRESS, A.CUST_ID, A.ACCT_NBR, A.BILLING_CYCLE_TYPE_ID, A.PAYMENT_TYPE, A.BANK_ID, A.BANK_ACCT_NBR, A.BANK_ACCT_NAME, A.CREATED_DATE, A.UPDATE_DATE, A.STATE, A.STATE_DATE, A.POSTPAID, A.ROUTING_ID, A.BILL_FORMAT_ID, A.SP_ID, A.DEFAULT_FLAG, A.PAYMENT_METHOD_ID, A.IS_LOCK, A.PARTY_TYPE, A.PARTY_CODE, A.BANK_ACCT_EXP_DATE, A.BANK_CARD_TYPE, A.NEED_UPLOAD, A.PAYMENT_COMMENTS, A.ACCT_NAME, B.CUST_NAME, A.BILL_FLAG FROM ACCT A JOIN CUST B ON A.CUST_ID = B.CUST_ID WHERE A.ACCT_ID = :acctId
        """;
    if (Boolean.TRUE.equals(lockFlag)) {
      sql += " FOR UPDATE OF A.ACCT_ID NOWAIT";
    }

    Query query = entityManager.createNativeQuery(sql, Acct.class);
    query.setParameter("acctId", acctId);

    List<Acct> results = query.getResultList();
    return results.isEmpty() ? null : results.get(0);
  }

  public Acct qryAcctByAcctId(Long acctId, Boolean lockFlag) {
    Acct acct = null;
    try {
      acct = selectAcctByAcctId(acctId, lockFlag);
    } catch (Exception e) {
      if (e.getCause() instanceof java.sql.SQLException) {
        log.warn("The error stack is {}", e);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00358"));
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00359"));
      }
    }
    return acct;
  }

  public Acct getAcctByAcctId(Long acctId, Boolean lockFlag, Boolean isLockRetryFlag) {
    if (Boolean.TRUE.equals(lockFlag)) {
      return acctRepository.findAcctByAcctIdForUpdate(acctId);
    }
    return acctRepository.findAcctByAcctId(acctId);
  }

  public Boolean isBigAccount(Long acctId) {
    Boolean isBigAccount = Boolean.FALSE;
    // String bigAccountIds =
    // ConfigItemCache.instance().getString("ACCT.COMMON.BIG_ACCOUNT_ID_LIST");
    // if (StringUtil.isNotEmpty(bigAccountIds) &&
    // CommonUtil.isInCommaText(bigAccountIds, StringUtil.toString(acctId)))
    // isBigAccount = Boolean.valueOf(true);
    return isBigAccount;
  }

  public Acct getAcctByAcctNbr(String acctNbr, Boolean lockFlag, Boolean isLockRetryFlag) {
    if (Boolean.TRUE.equals(lockFlag)) {
      return acctRepository.findAcctByAcctNbrForUpdate(acctNbr);
    }
    return acctRepository.findAcctByAcctNbr(acctNbr);
  }

  public Bal[] qryAllBalListForBigAcct(Long subsId, Long routingId, Long acctId) {
    // if (routingId == null)
    // routingId = RoutingHelper.getRoutingIdByAcctId(acctId);

    String acctResIdResStr = BillingHelper.getKeepUniqueAcctRes();

    String isQryByAcctResId = configItemRepository.findConfigItem("ACCT", "COMMON", "IS_QRY_BY_ACCT_RES_ID")
        .map(ConfigItemParamProjection::getDefaultValue).orElse("N");

    List<SelectAllBalListByAcctIdExtRawProjection> projections = balRepository.selectAllBalListByAcctIdExtRaw(acctId,
        subsId);

    Optional<Long> acctResIds = Arrays.stream(acctResIdResStr.split(","))
        .map(Long::valueOf)
        .findFirst();

    List<Bal> balList = projections.stream()
        .map(p -> {
          Bal b = new Bal();
          b.setAcctId(p.getAcctId());
          if ("Y".equals(isQryByAcctResId)) {
            b.setAcctResId(acctResIds.get());
          }
          b.setGrossBal(toBigDecimal(p.getGrossBal()));
          b.setConsumeBal(toBigDecimal(p.getConsumeBal()));
          b.setRatingBal(toBigDecimal(p.getRatingBal()));
          b.setBillingBal(toBigDecimal(p.getBillingBal()));
          b.setEffDate(p.getEffDate());
          b.setExpDate(p.getExpDate());
          b.setUpdateDate(p.getUpdateDate());
          b.setCeilLimit(toBigDecimal(p.getCeilLimit()));
          b.setFloorLimit(toBigDecimal(p.getFloorLimit()));
          b.setSubsId(p.getSubsId());
          b.setDailyCeilLimit(toBigDecimal(p.getDailyCeilLimit()));
          b.setDailyFloorLimit(toBigDecimal(p.getDailyFloorLimit()));
          b.setPriority(p.getPriority() != null ? p.getPriority().intValue() : null);
          b.setInitBal(toBigDecimal(p.getInitBal()));
          b.setBalId(p.getBalId());
          b.setVarCeilLimit(toBigDecimal(p.getVarCeilLimit()));
          b.setAbsExpOffset(p.getAbsExpOffset() != null ? p.getAbsExpOffset().intValue() : null);
          return b;
        })
        .toList();

    Bal[] allBalList = balList.toArray(new Bal[0]);

    allBalList = qryReserveBal(allBalList, acctId);

    Optional<ConfigItemParamProjection> findFilterCcBal = configItemRepository.findConfigItem("ACCT", "COMMON",
        "IS_FILTER_CC_BAL");
    String isFilterCcBal = findFilterCcBal
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse("Y");
    if ("Y".equals(isFilterCcBal))
      allBalList = filterCcBal(allBalList);
    return allBalList;
  }

  public Bal[] qryAllBalList(Long subsId, Long routingId, Long acctId) {
    // if (this.routingId == null)
    // this.routingId = RoutingHelper.getRoutingIdByAcctId(this.acctId);
    // ambil parameter tambahan dari konfigurasi lama
    // String acctResIdStr = BillingHelper.getKeepUniqueAcctRes();
    // List<Long> acctResIds = BillingHelper.convertToLongList(acctResIdStr);
    // boolean acctResIdsEmpty = acctResIds == null || acctResIds.isEmpty();

    List<SelectAllBalListByAcctIdProjection> results = balRepository.selectAllBalListByAcctId(acctId, subsId);

    if (results.isEmpty()) {
      return null;
    }

    List<Bal> balList = new ArrayList<>();
    for (SelectAllBalListByAcctIdProjection p : results) {
      Bal bal = new Bal();
      bal.setAcctId(p.getAcctId());
      bal.setAcctResId(p.getAcctResId());
      bal.setGrossBal(toBigDecimal(p.getGrossBal()));
      bal.setConsumeBal(toBigDecimal(p.getConsumeBal()));
      bal.setRatingBal(toBigDecimal(p.getRatingBal()));
      bal.setBillingBal(toBigDecimal(p.getBillingBal()));
      bal.setEffDate(p.getEffDate());
      bal.setExpDate(p.getExpDate());
      bal.setUpdateDate(p.getUpdateDate());
      bal.setCeilLimit(toBigDecimal(p.getCeilLimit()));
      bal.setFloorLimit(toBigDecimal(p.getFloorLimit()));
      bal.setDailyCeilLimit(toBigDecimal(p.getDailyCeilLimit()));
      bal.setDailyFloorLimit(toBigDecimal(p.getDailyFloorLimit()));
      bal.setPriority(p.getPriority() != null ? p.getPriority().intValue() : null);
      bal.setInitBal(toBigDecimal(p.getInitBal()));
      bal.setBalId(p.getBalId());
      bal.setVarCeilLimit(toBigDecimal(p.getVarCeilLimit()));
      bal.setSubsId(p.getSubsId());
      bal.setAbsExpOffset(p.getAbsExpOffset() != null ? p.getAbsExpOffset().intValue() : null);

      // lookup tambahan dari cache (mirip kode lama)
      AcctResDto acctResDto = acctResService.getAcctResById(bal.getAcctResId());
      if (acctResDto != null) {
        bal.setAcctResName(acctResDto.getAcctResName());
        bal.setAcctResDto(acctResDto);
      }

      balList.add(bal);
    }

    Bal[] allBalList = balList.toArray(new Bal[0]);
    allBalList = qryReserveBal(allBalList, acctId);
    Optional<ConfigItemParamProjection> findIsFilterCcBal = configItemRepository.findConfigItem("ACCT", "COMMON",
        "IS_FILTER_CC_BAL");
    String isFilterCcBal = findIsFilterCcBal
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse("");
    if (isFilterCcBal.isEmpty()) {
      isFilterCcBal = "Y";
    }

    if ("Y".equals(isFilterCcBal))
      allBalList = filterCcBal(allBalList);
    return allBalList;
  }

  public Boolean isRecordBill(String postpaid) {
    Boolean modBill = Boolean.TRUE;
    Optional<ConfigItemParamProjection> findRetValue = configItemRepository.findConfigItem("ACCT", "ACCOUNT_PUBLIC",
        "IS_MOD_BILL_4PREPAY");
    String retValue = findRetValue
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse("");
    if (retValue.isEmpty()) {
      retValue = "Y";
    }
    if (!"Y".equals(postpaid) && "N".equals(retValue))
      modBill = Boolean.FALSE;
    return modBill;
  }

  private Bal[] qryReserveBal(Bal[] balArray, Long acctId) {
    if (balArray != null && balArray.length != 0) {
      ReserveBal[] reserveBalArray = selectReserveBalList(acctId);
      Optional<ConfigItemParamProjection> findReverseBalType = configItemRepository.findConfigItem("ACCT", "COMMON",
          "REVERSE_BAL_TYPE");
      String reverseBalType = findReverseBalType
          .map(ConfigItemParamProjection::getDefaultValue)
          .orElse(null);
      Boolean isInclueResvBal = Boolean.valueOf(false);
      if (balHelper.isInclueResvBal().booleanValue())
        isInclueResvBal = Boolean.valueOf(true);
      for (Bal bal : balArray) {
        long reserveBal = 0L;
        long realBal = bal.getRealBal().longValue();
        for (ReserveBal reserveBalDto : reserveBalArray) {
          if (bal.getBalId().equals(reserveBalDto.getEmbeddedReserveBalId().getBalId()))
            if ("U".equals(reverseBalType)) {
              reserveBal += reserveBalDto.getUsedBal().longValue();
            } else {
              reserveBal += reserveBalDto.getReserveBal().longValue();
            }
        }
        if (isInclueResvBal.booleanValue())
          realBal += reserveBal;
        bal.setReserveBal(BigDecimal.valueOf(reserveBal));
        bal.setRealBal(Long.valueOf(realBal));
      }
    }
    return balArray;
  }

  public ReserveBal[] selectReserveBalList(Long acctId) {
    if (acctId == null) {
      throw new IllegalArgumentException("acctId must not be null");
    }

    String sql = """
        SELECT SESSION_ID, BAL_ID, ACCT_ID, BAL_SHARE_ID, USED_BAL, RESERVE_BAL, UPDATE_DATE FROM RESERVE_BAL WHERE ACCT_ID = ?1
        """;

    List<Object[]> rows = entityManager.createNativeQuery(sql)
        .setParameter(1, acctId)
        .getResultList();

    return rows.stream()
        .map(this::mapRowToReserveBal)
        .toArray(ReserveBal[]::new);
  }

  private ReserveBal mapRowToReserveBal(Object[] row) {
    int i = 0;
    ReserveBal reserveBal = new ReserveBal();
    ReserveBalId reserveBalId = new ReserveBalId();
    reserveBalId.setSessionId((String) row[i++]);
    reserveBalId.setBalId(toLong(toLong(row[i++])));
    reserveBal.setEmbeddedReserveBalId(reserveBalId);
    reserveBal.setAcctId(toLong(row[i++]));
    reserveBal.setBalShareId(toLong(row[i++]));
    reserveBal.setUsedBal(defaultIfNull(toLong(row[i++]), 0L));
    reserveBal.setReserveBal(defaultIfNull(toLong(row[i++]), 0L));
    reserveBal.setUpdateDate(toLocalDateTime(row[i++]));
    return reserveBal;
  }

  private Long toLong(Object value) {
    return value == null ? null : ((BigDecimal) value).longValue();
  }

  private Long defaultIfNull(Long value, Long defaultVal) {
    return value == null ? defaultVal : value;
  }

  private LocalDateTime toLocalDateTime(Object value) {
    if (value instanceof Timestamp ts) {
      return ts.toLocalDateTime();
    } else if (value instanceof Date date) {
      return date.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime();
    } else {
      return null;
    }
  }

  private Bal[] filterCcBal(Bal[] balArray) {
    List<Bal> balList = new ArrayList<>();
    if (isNotEmpty((Object[]) balArray))
      for (int i = 0; i < balArray.length; i++) {
        if (balArray[i].getAcctResDto() != null &&
            !(balArray[i].getAcctResDto().getBalType()).equals(Long.valueOf(6L)))
          balList.add(balArray[i]);
      }
    return balList.toArray(new Bal[0]);
  }

  private long safe(Long v) {
    return v == null ? 0L : v;
  }

  private BigDecimal toBigDecimal(Long value) {
    return value == null ? null : BigDecimal.valueOf(value);
  }

}
