package com.sts.sinorita.helper;

import com.sts.sinorita.balanceAdjustment.AcctResService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.entity.Bal;
import com.sts.sinorita.dto.request.balanceAdjustment.BalAcctItemTypeDto;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.BalEntityRepository;
import com.sts.sinorita.repository.BalRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.SystemParamRepository;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BalHelper {
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  // private static final SimpleDateFormat DATE_FORMAT = new
  // SimpleDateFormat("yyyy-MM-dd");
  static Logger logger = LoggerFactory.getLogger(BalHelper.class);
  @Autowired
  private AcctResService acctResService;
  @Autowired
  private ConfigItemRepository configItemRepository;
  @Autowired
  private BalEntityRepository balRepository;
  @Autowired
  private BalHelperExt balHelperExt;
  @Autowired
  private static SystemParamRepository systemParamRepository;

  public BalDto[] addBalListPrepare(BalDto[] addBalList, BalDto[] oldBalList, Long routingId) {
    if (addBalList == null || addBalList.length <= 0)
      return oldBalList;
    if (routingId == null)
      routingId = Long.valueOf(0L);
    LocalDateTime now = LocalDateTime.now();
    BalDto[] newBalArray = null;
    Hashtable<String, BalDto> uniqueBalDto = new Hashtable<>();
    Hashtable<Long, BalDto> oldBalCopyDto = new Hashtable<>();
    Hashtable<Long, BalDto> newBalTable = new Hashtable<>();
    Hashtable<String, Long> preBalanceOfAcctResId = new Hashtable<>();
    Hashtable<String, Long> preSuttleBalOfAcctResId = new Hashtable<>();
    addOldBalList(oldBalList, now, uniqueBalDto, oldBalCopyDto, newBalTable, preBalanceOfAcctResId,
        preSuttleBalOfAcctResId);
    Map<Long, Long> maxDayExtention4acctResIdMap = getMaxDayExtention4acctResIdMap();
    for (int i = 0; i < addBalList.length; i++) {
      BalDto curBal = addBalList[i];
      if (curBal != null) {
        if (!((curBal.getAcctResId() != null && curBal.getAcctId() != null) || curBal.getBalId() != null)) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "acctResId or acctId is null and balId is null.");
        }
        if ((curBal.getAcctId() == null || curBal.getAcctResId() == null) && curBal.getBalId() != null) {
          BalDto oldBal = getBalFromListByBalId(oldBalList, curBal.getBalId());
          if (oldBal == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00153"));
          curBal.setAcctResId(oldBal.getAcctResId());
          curBal.setAcctId(oldBal.getAcctId());
        }
        AcctResDto acctResDto = acctResService.getAcctResById(curBal.getAcctResId());
        if (acctResDto == null)
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00154"));

        String refillable = "";
        if (acctResDto.getRefillable().equals("")) {
          refillable = "N";
        } else {
          refillable = String.valueOf(acctResDto.getRefillable());
        }
        Long gracePeriod = acctResDto.getGracePeriod();
        Long maxRollOver = acctResDto.getMaxRollover();
        boolean isGracePeriod = false;
        logger.debug("gracePeriod is [{}],maxRollOver is [{}]");
        if (gracePeriod != null && gracePeriod > 0L && "L".equals(refillable))
          isGracePeriod = dealGracePeriodForOldBalList(curBal, acctResDto.getAcctResId(), oldBalList, gracePeriod,
              maxRollOver);
        if (curBal.getSubsId() == null)
          curBal.setSubsId(Long.valueOf(-1L));
        BalDto balForUpdated = null;
        if (curBal.getBalId() != null) {
          balForUpdated = oldBalCopyDto.get(curBal.getBalId());
        } else if (isMayModify(refillable)) {
          balForUpdated = uniqueBalDto.get(getKeyOfUniqueBal(refillable, curBal, now));
          logger.debug("the map of bal for May Modify bal. uniqueBal=[{}]", uniqueBalDto);
        }
        if (balForUpdated == null) {
          logger.debug("add a bal for update operation. curBalIn=[{}]", curBal);
          if (curBal.getEffDate() == null)
            curBal.setEffDate(now);
          LocalDateTime preExpDate = curBal.getPreExpDate();
          if (preExpDate == null)
            preExpDate = curBal.getEffDate();
          LocalDateTime preEffDate = curBal.getPreEffDate();
          if (preEffDate == null)
            preEffDate = curBal.getEffDate();
          curBal.setBalId(balRepository.getNextBalId());
          curBal.setOperationType("A");
          curBal.setRealBal(curBal.getCharge());
          curBal.setGrossBal(curBal.getCharge());
          curBal.setPreExpDate(preExpDate);
          curBal.setPreBalance(Long.valueOf(0L));
          curBal.setPreSuttleBal(Long.valueOf(0L));
          curBal.setUpdateDate(now);
          if (curBal.getInitBal() == null)
            curBal.setInitBal(curBal.getGrossBal());
          initBalLimit(curBal, acctResDto);
          if (curBal.getCeilLimitCharge() != null)
            curBal.setCeilLimit(curBal.getCeilLimitCharge());
          if (curBal.getConsumeBalCharge() != null) {
            curBal.setConsumeBal(curBal.getConsumeBalCharge());
            curBal.setRealBal(Long.valueOf(curBal.getRealBal().longValue() + curBal.getConsumeBalCharge().longValue()));
          }
          curBal.setPreEffDate(preEffDate);
          curBal.setEffSeconds(Long.valueOf(DateUtil.differDateInDays(curBal.getEffDate(), curBal.getPreEffDate(), 0)));
          if (curBal.getExpDate() != null) {
            curBal.setSeconds(Long.valueOf(DateUtil.differDateInDays(curBal.getExpDate(), preExpDate, 0)));
          } else if (curBal.getSeconds() != null) {
            LocalDateTime newExpDate = DateUtil.offsetSecond(preExpDate, curBal.getSeconds().longValue());
            curBal.setExpDate(newExpDate);
          }
          if (curBal.getExpDate() == null)
            curBal.setExpDate(preExpDate);
          if (curBal.getAcctResDto() == null)
            curBal.setAcctResDto(acctResDto);
          modAndValidateMaxExpDate(curBal, balForUpdated, maxDayExtention4acctResIdMap, now);
          if (isMayModify(refillable)) {
            BalDto copyBal = copyBalInfo(curBal);
            uniqueBalDto.put(getKeyOfUniqueBal(refillable, curBal, now), copyBal);
            verifyDateIsOverlap(newBalTable.values(), copyBal, refillable);
            newBalTable.put(curBal.getBalId(), copyBal);
          } else {
            newBalTable.put(curBal.getBalId(), curBal);
          }
          logger.debug("add a bal for update operation. curBalOut=[{}]", curBal);
        } else {
          logger.debug("update a bal for update operation. curBalIn=[{}] \n balForUpdated=[{}]", curBal, balForUpdated);
          curBal.setBalId(balForUpdated.getBalId());
          curBal.setOperationType("M");
          curBal.setUpdateDate(now);
          curBal.setPreExpDate(balForUpdated.getExpDate());
          balForUpdated.setGrossBal(Long.valueOf(balForUpdated.getGrossBal().longValue() + curBal
              .getCharge().longValue()));
          balForUpdated.setRealBal(Long.valueOf(balForUpdated.getRealBal().longValue() + curBal
              .getCharge().longValue()));
          if (curBal.getCeilLimitCharge() != null)
            if (balForUpdated.getCeilLimit() != null) {
              balForUpdated.setCeilLimit(
                  Long.valueOf(balForUpdated.getCeilLimit().longValue() + curBal.getCeilLimitCharge().longValue()));
            } else {
              balForUpdated.setCeilLimit(curBal.getCeilLimitCharge());
            }
          if (curBal.getConsumeBalCharge() != null) {
            balForUpdated.setConsumeBal(
                Long.valueOf(balForUpdated.getConsumeBal().longValue() + curBal.getConsumeBalCharge().longValue()));
            balForUpdated.setRealBal(
                Long.valueOf(balForUpdated.getRealBal().longValue() + curBal.getConsumeBalCharge().longValue()));
          }
          if (curBal.getInitBal() != null)
            balForUpdated.setInitBal(curBal.getInitBal());
          balForUpdated.setUpdateDate(now);
          Optional<ConfigItemParamProjection> findSupprortAbsExpDate = configItemRepository.findConfigItem("ACCT",
              "ACCT_BILLING", "SUPPORT_ABS_EXP_DATE");
          String supprortAbsExpDate = findSupprortAbsExpDate
              .map(ConfigItemParamProjection::getDefaultValue)
              .orElse("");
          if ("Y".equals(supprortAbsExpDate) && balForUpdated.getExpDate() == null && curBal.getExpDate() != null) {
            String dateNowStr = DateUtil.date2String(now, "yyyy-MM-dd");
            LocalDateTime tmpDate = DateUtil.string2SQLDate(dateNowStr + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            balForUpdated.setExpDate(tmpDate);
          }
          if (balForUpdated.getExpDate() != null) {
            if (curBal.getSeconds() != null) {
              if (isGracePeriod && balForUpdated.getExpDate().isBefore(now))
                curBal.setSeconds(Long.valueOf(CommonUtil.nvl(curBal.getSeconds(), Long.valueOf(0L)).longValue()
                    + DateUtil.differDateInDays(now, balForUpdated
                        .getExpDate(), 0)));
              LocalDateTime newExpDate = DateUtil.offsetSecond(balForUpdated.getExpDate(), curBal.getSeconds()
                  .longValue());
              balForUpdated.setExpDate(newExpDate);
            } else if (curBal.getExpDate() != null) {
              curBal.setSeconds(Long.valueOf(DateUtil.differDateInDays(curBal.getExpDate(), balForUpdated
                  .getExpDate(), 0)));
              balForUpdated.setExpDate(curBal.getExpDate());
            } else {
              curBal.setSeconds(Long.valueOf(0L));
            }
            modAndValidateMaxExpDate(curBal, balForUpdated, maxDayExtention4acctResIdMap, now);
          }
          curBal.setPreEffDate(balForUpdated.getEffDate());
          if (curBal.getEffSeconds() != null) {
            LocalDateTime newEffDate = DateUtil.offsetSecond(balForUpdated.getEffDate(), curBal.getEffSeconds()
                .longValue());
            balForUpdated.setEffDate(newEffDate);
          } else if (curBal.getEffDate() != null) {
            curBal.setEffSeconds(
                Long.valueOf(DateUtil.differDateInDays(curBal.getEffDate(), balForUpdated.getEffDate(), 0)));
            balForUpdated.setEffDate(curBal.getEffDate());
          } else {
            curBal.setEffSeconds(Long.valueOf(0L));
          }
          verifyDateIsOverlap(newBalTable.values(), balForUpdated, refillable);
          addBalAcctItemTypePrepare(curBal, balForUpdated);
          logger.debug("update a bal for update operation. curBalOut=[{}], balForUpdated=[{}]", curBal, balForUpdated);
        }
        String preBalKey = gePreBalKey(curBal.getAcctResId(),
            Long.valueOf((curBal.getSubsId() == null) ? -1L : curBal.getSubsId().longValue()), acctResDto.getStdCode());
        curBal.setPreBalance(preBalanceOfAcctResId.get(preBalKey));
        curBal.setPreSuttleBal(preSuttleBalOfAcctResId.get(preBalKey));
        if (curBal.getPreBalance() == null) {
          curBal.setPreBalance(Long.valueOf(0L));
          curBal.setPreSuttleBal(Long.valueOf(0L));
        }
        preBalanceOfAcctResId.put(preBalKey,
            Long.valueOf(curBal.getPreBalance().longValue() + curBal.getCharge().longValue()));
        preSuttleBalOfAcctResId.put(preBalKey,
            Long.valueOf(curBal.getPreSuttleBal().longValue() + curBal.getCharge().longValue()));
      }
    }
    if (!newBalTable.isEmpty())
      newBalArray = newBalTable.values().toArray(new BalDto[0]);
    return newBalArray;
  }

  public static BalDto copyBalInfo(BalDto oriBal) {
    BalDto retBal = new BalDto();
    retBal.setPreBalance(oriBal.getPreBalance());
    retBal.setPreSuttleBal(oriBal.getPreSuttleBal());
    retBal.setBalId(oriBal.getBalId());
    retBal.setAcctId(oriBal.getAcctId());
    retBal.setAcctResId(oriBal.getAcctResId());
    retBal.setCharge(oriBal.getCharge());
    retBal.setDailyCeilLimit(oriBal.getDailyCeilLimit());
    retBal.setDailyFloorLimit(oriBal.getDailyFloorLimit());
    retBal.setSeconds(oriBal.getSeconds());
    retBal.setFloorLimit(oriBal.getFloorLimit());
    retBal.setCeilLimit(oriBal.getCeilLimit());
    retBal.setPriority(oriBal.getPriority());
    retBal.setUpdateDate(oriBal.getUpdateDate());
    retBal.setGrossBal(oriBal.getGrossBal());
    retBal.setEffDate(oriBal.getEffDate());
    retBal.setExpDate(oriBal.getExpDate());
    retBal.setRealBal(oriBal.getRealBal());
    retBal.setReserveBal(oriBal.getReserveBal());
    retBal.setConsumeBal(oriBal.getConsumeBal());
    retBal.setRatingBal(oriBal.getRatingBal());
    retBal.setBillingBal(oriBal.getBillingBal());
    retBal.setPreExpDate(oriBal.getPreExpDate());
    retBal.setAcctResName(oriBal.getAcctResName());
    retBal.setAcctResComments(oriBal.getAcctResComments());
    retBal.setRoutingId(oriBal.getRoutingId());
    retBal.setBalCode(oriBal.getBalCode());
    retBal.setOperationType(oriBal.getOperationType());
    retBal.setInitBal(oriBal.getInitBal());
    retBal.setChangeInitBal(oriBal.getChangeInitBal());
    retBal.setSubsId(oriBal.getSubsId());
    retBal.setCheckMode(oriBal.getCheckMode());
    retBal.setEffSeconds(oriBal.getEffSeconds());
    retBal.setAbsExpOffset(oriBal.getAbsExpOffset());
    if (oriBal.getBalAcctItemTypeList() != null && oriBal.getBalAcctItemTypeList().length > 0) {
      com.sts.sinorita.dto.request.balanceAdjustment.BalAcctItemTypeDto[] balAcctItemTypeDtoList = Arrays
          .stream(oriBal.getBalAcctItemTypeList())
          .map(src -> {
            BalAcctItemTypeDto dto = new BalAcctItemTypeDto();
            dto.setBalId(src.getBalId());
            dto.setAcctItemTypeId(src.getAcctItemTypeId());
            dto.setSpId(src.getSpId());
            return dto;
          })
          .toArray(com.sts.sinorita.dto.request.balanceAdjustment.BalAcctItemTypeDto[]::new);

      retBal.setBalAcctItemTypeList(balAcctItemTypeDtoList);
    }
    retBal.setAcctResDto(oriBal.getAcctResDto());
    retBal.setCeilLimitCharge(oriBal.getCeilLimitCharge());
    retBal.setConsumeBalCharge(oriBal.getConsumeBalCharge());
    if (oriBal.getRefAttr() != null && !oriBal.getRefAttr().isEmpty()) {
      String balFlags = CalcFeeHelper.getExtParamFromRefAttr(oriBal.getRefAttr(), "BAL_FLAGS");
      if (balFlags.isEmpty())
        balFlags = CalcFeeHelper.getValFromKeyValueStr(oriBal.getRefAttr(), "BAL_FLAGS", ",", ":");
      retBal.setBalFlags(balFlags);
    }
    retBal.setCustId(oriBal.getCustId());
    return retBal;
  }

  public void addOldBalList(BalDto[] oldBalList, LocalDateTime now, Map<String, BalDto> uniqueBalDto,
      Map<Long, BalDto> oldBalCopyDto, Map<Long, BalDto> newBalTable, Map<String, Long> preBalanceOfAcctResId,
      Map<String, Long> preSuttleBalOfAcctResId) {
    for (int i = 0; oldBalList != null && i < oldBalList.length; i++) {
      BalDto curBal = oldBalList[i];
      if (curBal == null)
        continue;
      AcctResDto acctResDto = acctResService.getAcctResById(curBal.getAcctResId());
      if (acctResDto == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00154"));
      String refillable = "";
      if (!acctResDto.getRefillable().equals("")) {
        refillable = "N";
      } else {
        refillable = String.valueOf(acctResDto.getRefillable());
      }
      BalDto copyBal = copyBalInfo(curBal);
      if (isMayModify(refillable))
        uniqueBalDto.put(getKeyOfUniqueBal(refillable, curBal, now), copyBal);
      if (curBal.getBalId() != null) {
        oldBalCopyDto.put(curBal.getBalId(), copyBal);
        newBalTable.put(curBal.getBalId(), copyBal);
      }
      // Optional<ConfigItemParamProjection> findNotAccumulateStdCodes =
      // configItemRepository.findConfigItem("ACCT",
      // "COMMON", "SPECIAL_STD_CODE_4_NO_NEED_ACCUMULATE");
      // String notAccumulateStdCodes = findNotAccumulateStdCodes
      // .map(ConfigItemParamProjection::getDefaultValue)
      // .orElse("");
      // if (!notAccumulateStdCodes.isEmpty()) {
      // if ("-1".equals(notAccumulateStdCodes))
      // continue;
      // if (CommonUtil.isInCommaText(notAccumulateStdCodes, acctResDto.getStdCode(),
      // ','))
      // continue;
      // }
      if (curBal.getExpDate() == null || !curBal.getExpDate().isBefore(now) || "Y".equals(refillable)) {
        String preBalKey = gePreBalKey(curBal.getAcctResId(),
            Long.valueOf((curBal.getSubsId() == null) ? -1L : curBal.getSubsId().longValue()), acctResDto.getStdCode());
        Long preBalance = preBalanceOfAcctResId.get(preBalKey);
        Long preSuttleBal = preSuttleBalOfAcctResId.get(preBalKey);
        if (preBalance == null) {
          preBalance = Long.valueOf(0L);
          preSuttleBal = Long.valueOf(0L);
        }
        preBalanceOfAcctResId.put(preBalKey,
            Long.valueOf(curBal.getGrossBal().longValue() + preBalance.longValue()));
        preSuttleBalOfAcctResId.put(preBalKey,
            Long.valueOf(curBal.getRealBal().longValue() + preSuttleBal.longValue()));
      }
      continue;
    }
  }

  public static String getKeyOfUniqueBal(String refillable, BalDto bal, LocalDateTime now) {
    if (refillable == null || bal == null) {
      throw new IllegalArgumentException("Refillable type and balance cannot be null");
    }

    return switch (refillable) {
      case "Y" -> String.valueOf(bal.getAcctResId());
      case "M" -> buildMonthlyKey(bal);
      case "L" -> buildLifecycleKey(bal, now);
      default -> throw new IllegalArgumentException("Unknown refillable type: " + refillable);
    };
  }

  private static String buildMonthlyKey(BalDto bal) {
    String eff = formatDate(bal.getEffDate());
    String exp = formatDate(bal.getExpDate());
    return String.join("|", String.valueOf(bal.getAcctResId()), eff, exp);
  }

  private static String buildLifecycleKey(BalDto bal, LocalDateTime now) {
    StringBuilder sb = new StringBuilder();
    sb.append(bal.getAcctResId()).append("|");

    LocalDateTime eff = bal.getEffDate();
    LocalDateTime exp = bal.getExpDate();

    boolean activeNow = isActive(now, eff, exp);

    if (activeNow || isSameDay(now, eff, exp)) {
      sb.append("1");
    } else {
      sb.append(formatDate(eff)).append("|").append(formatDate(exp));
    }

    Long subsId = Objects.requireNonNullElse(bal.getSubsId(), -1L);
    sb.append("|").append(subsId);

    return sb.toString();
  }

  private static boolean isActive(LocalDateTime now, LocalDateTime eff, LocalDateTime exp) {
    return (eff != null && (now.equals(eff) || now.isAfter(eff))) &&
        (exp == null || now.isBefore(exp));
  }

  private static boolean isSameDay(LocalDateTime now, LocalDateTime eff, LocalDateTime exp) {
    if (eff == null || exp == null)
      return false;
    return isSameDay(now, eff) && isSameDay(now, exp);
  }

  private static boolean isSameDay(LocalDateTime d1, LocalDateTime d2) {
    if (d1 == null || d2 == null) {
      return false;
    }
    return d1.toLocalDate().equals(d2.toLocalDate());
  }

  private static String formatDate(LocalDateTime date) {
    if (date == null) {
      return "";
    }
    return date.format(DATE_FORMAT);
  }

  public static boolean isMayModify(String refillable) {
    return "Y".equals(refillable) || "M".equals(refillable) || "L".equals(refillable);
  }

  private static String gePreBalKey(Long acctResId, Long subsId, String stdCode) {
    String preBalKey = acctResId.toString();
    // Optional<ConfigItemParamProjection> findSubsAccumulateStdCodes =
    // configItemRepository.findConfigItem("ACCT",
    // "COMMON", "SUBS_ACCUMULATE_STD_CODE");
    // String subsAccumulateStdCodes = findSubsAccumulateStdCodes
    // .map(ConfigItemParamProjection::getDefaultValue)
    // .orElse("");
    // if (subsAccumulateStdCodes.isEmpty()) {
    // return preBalKey;
    // }
    // if ("-1".equals(subsAccumulateStdCodes)) {
    // preBalKey = preBalKey + "_" + subsId;
    // } else if (CommonUtil.isInCommaText(subsAccumulateStdCodes, stdCode, ',')) {
    // preBalKey = preBalKey + "_" + subsId;
    // }
    return preBalKey;
  }

  public static BalDto[] concatBalArray(BalDto[] balArr1, BalDto[] balArr2) {
    if (balArr1 == null && balArr2 == null)
      return null;
    if (balArr1 != null && balArr2 != null) {
      BalDto[] mergedBalArr = new BalDto[balArr1.length + balArr2.length];
      System.arraycopy(balArr1, 0, mergedBalArr, 0, balArr1.length);
      System.arraycopy(balArr2, 0, mergedBalArr, balArr1.length, balArr2.length);
      return mergedBalArr;
    }
    if (balArr1 != null)
      return balArr1;
    return balArr2;
  }

  private Map<Long, Long> getMaxDayExtention4acctResIdMap() {
    Map<Long, Long> maxDayExtention4acctResIdMap = new HashMap<>();
    Optional<ConfigItemParamProjection> findMaxDayExtention4acctResIdConfig = configItemRepository
        .findConfigItem("ACCT", "COMMON", "ACCT_RES_ID_AND_MAX_BAL_EXPIRE");
    String maxDayExtention4acctResIdConfig = findMaxDayExtention4acctResIdConfig
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse("");
    if ((maxDayExtention4acctResIdConfig.isEmpty())) {
      String[] nodeList = maxDayExtention4acctResIdConfig.split("\\|");
      for (String node : nodeList) {
        String[] keyValue = node.split("=");
        if (keyValue.length == 2)
          try {
            maxDayExtention4acctResIdMap.put(Long.valueOf(Long.parseLong(keyValue[0].trim())),
                Long.valueOf(Long.parseLong(keyValue[1].trim())));
          } catch (NumberFormatException ex) {
            logger.warn("illeagal config in [Account.AcctResIdAndMaxBalExpire],check config string [{}]", node);
          }
      }
    }
    return maxDayExtention4acctResIdMap;
  }

  public static BalDto getBalFromListByBalId(BalDto[] balList, Long balId) {
    if (balId == null || balList == null || balList.length <= 0)
      return null;
    BalDto bal = null;
    for (int i = 0; i < balList.length; i++) {
      if (balId.equals(balList[i].getBalId())) {
        bal = balList[i];
        break;
      }
    }
    return bal;
  }

  private static boolean dealGracePeriodForOldBalList(BalDto curBal, Long acctResId, BalDto[] oldBalList,
      Long gracePeriod, Long maxRollOver) {
    boolean isGracePeriod = false;
    if (gracePeriod == null || gracePeriod.longValue() == 0L) {
      logger.debug("gracePeriod is null, not need to deal.");
      return false;
    }
    if (CommonUtil.nvl(curBal.getCharge(), Long.valueOf(0L)).longValue() > 0L) {
      logger.debug("deduct balacne is not need to deal gracePeriod");
      return false;
    }
    Long balId = curBal.getBalId();
    if (balId != null && balId.longValue() > 0L) {
      isGracePeriod = dealGracePeriodForBal(curBal, gracePeriod, maxRollOver, getBalFromListByBalId(oldBalList, balId));
    } else {
      BalDto[] balList = sortBalArrayByExpdateDesc(getBalFromListByAcctResId(oldBalList, acctResId));
      if (balList != null && balList.length > 0)
        isGracePeriod = dealGracePeriodForBal(curBal, gracePeriod, maxRollOver, balList[0]);
    }
    return isGracePeriod;
  }

  private static boolean dealGracePeriodForBal(BalDto curBal, Long gracePeriod, Long maxRollOver, BalDto bal) {
    boolean isGracePeriod = false;
    if (bal != null) {
      LocalDateTime expDate = bal.getExpDate();
      if (expDate == null || expDate.isAfter(LocalDateTime.now())) {
        isGracePeriod = false;
      } else {
        Long balId = curBal.getBalId();
        Long realBal = CommonUtil.nvl(bal.getRealBal(), Long.valueOf(0L));
        logger.debug("balId={},realBal={}", balId, realBal);
        if (realBal.longValue() <= 0L &&
            expDate.isBefore(LocalDateTime.now())) {
          LocalDateTime graceExpDate = DateUtil.offsetDay(expDate, Integer.parseInt(String.valueOf(gracePeriod)));
          if (graceExpDate.isAfter(LocalDateTime.now()) && realBal.longValue() < 0L) {
            if (balId == null)
              curBal.setBalId(bal.getBalId());
            if (maxRollOver != null) {
              Long charge = Long.valueOf(realBal.longValue() * -1L - maxRollOver.longValue());
              if (charge.longValue() >= 0L)
                curBal.setCharge(Long
                    .valueOf(charge.longValue() + CommonUtil.nvl(curBal.getCharge(), Long.valueOf(0L)).longValue()));
            }
            isGracePeriod = true;
          }
        }
      }
    }
    return isGracePeriod;
  }

  public static BalDto[] sortBalArrayByExpdateDesc(BalDto[] array) {
    logger.debug("sortBalArrayByExpdateDesc called.");
    if (array == null || array.length == 0) {
      return array;
    }
    try {
      Arrays.sort(array, Comparator.comparing(
          BalDto::getExpDate,
          Comparator.nullsLast(Comparator.reverseOrder())));
    } catch (Exception e) {
      logger.warn("Error while sorting balance array by expDate descending.", e);
    }
    return array;
  }

  public static BalDto[] getBalFromListByAcctResId(BalDto[] balList, Long acctResId) {
    List<BalDto> filterBalList = new ArrayList<>();
    if (balList != null)
      for (int i = 0; i < balList.length; i++) {
        if (acctResId.equals(balList[i].getAcctResId()))
          filterBalList.add(balList[i]);
      }
    return filterBalList.toArray(new BalDto[0]);
  }

  public static void initBalLimit(BalDto curBal, AcctResDto acctResDto) {
    if (curBal.getCeilLimit() == null && curBal.getDailyCeilLimit() == null && curBal
        .getFloorLimit() == null && curBal.getDailyFloorLimit() == null) {
      curBal.setCeilLimit(acctResDto.getCeilLimit());
      curBal.setFloorLimit(acctResDto.getFloorLimit());
      curBal.setDailyCeilLimit(acctResDto.getDailyCeilLimit());
      curBal.setDailyFloorLimit(acctResDto.getDailyFloorLimit());
    }
  }

  private static void modAndValidateMaxExpDate(BalDto updateBal, BalDto balForUpdated,
      Map<Long, Long> maxDayExtention4acctResIdMap, LocalDateTime now) {
    Long maxBalExpiryDays = maxDayExtention4acctResIdMap.get(updateBal.getAcctResId());
    LocalDateTime maxDateAllowed = null;
    if (maxBalExpiryDays != null) {
      maxDateAllowed = DateUtil.offsetSecond(DateUtil.getFullDate(now), maxBalExpiryDays.longValue() * 86400L);
      LocalDateTime newExpDate = DateUtil.offsetSecond(updateBal.getPreExpDate(), updateBal.getSeconds().longValue());
      if (newExpDate.isAfter(maxDateAllowed)) {
        // Long oldseconds = updateBal.getSeconds();
        updateBal.setSeconds(Long.valueOf(DateUtil.differDateInDays(maxDateAllowed, updateBal.getPreExpDate(), 0)));
        if (balForUpdated == null) {
          updateBal.setExpDate(maxDateAllowed);
        } else {
          balForUpdated.setExpDate(maxDateAllowed);
        }
      }
    }
  }

  private static void verifyDateIsOverlap(Collection<BalDto> newBalDtoList, BalDto newBalDto, String refillable) {
    if (!"L".equalsIgnoreCase(refillable)) {
      return;
    }
    Long balId = newBalDto.getBalId();
    Long acctResId = newBalDto.getAcctResId();
    Long subsId = Optional.ofNullable(newBalDto.getSubsId()).orElse(-1L);
    LocalDateTime effDate = newBalDto.getEffDate();
    LocalDateTime expDate = newBalDto.getExpDate();
    for (BalDto existingBal : newBalDtoList) {
      if (!Objects.equals(acctResId, existingBal.getAcctResId()) || Objects.equals(balId, existingBal.getBalId())) {
        continue;
      }
      Long existingSubsId = Optional.ofNullable(existingBal.getSubsId()).orElse(-1L);
      if (!subsId.equals(existingSubsId)) {
        continue;
      }
      LocalDateTime existingEff = existingBal.getEffDate();
      LocalDateTime existingExp = existingBal.getExpDate();
      boolean overlaps = (expDate == null || (existingEff != null && existingEff.isBefore(expDate))) &&
          (existingExp == null || (effDate != null && existingExp.isAfter(effDate)));
      if (overlaps) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            MessageService.getMessage("BL-S-ACT-00144"));
      }
    }
  }

  private static void addBalAcctItemTypePrepare(BalDto curBal, BalDto oldBal) {
    BalAcctItemTypeDto[] curList = curBal.getBalAcctItemTypeList();
    BalAcctItemTypeDto[] oldList = oldBal.getBalAcctItemTypeList();
    if (curList == null || curList.length == 0) {
      return;
    }
    // Gunakan LinkedHashMap agar urutan tetap terjaga
    Map<Long, Object> curMap = Arrays.stream(curList)
        .filter(Objects::nonNull)
        .collect(Collectors.toMap(
            BalAcctItemTypeDto::getAcctItemTypeId,
            dto -> dto,
            (a, b) -> a,
            LinkedHashMap::new));
    Map<Long, Object> oldMap = (oldList == null)
        ? new LinkedHashMap<>()
        : Arrays.stream(oldList)
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(
                BalAcctItemTypeDto::getAcctItemTypeId,
                dto -> dto,
                (a, b) -> a,
                LinkedHashMap::new));
    for (Long key : new HashSet<>(curMap.keySet())) {
      if (oldMap.containsKey(key)) {
        curMap.remove(key); // sudah ada di old, hapus dari cur
      } else {
        oldMap.put(key, curMap.get(key)); // tambahkan yang baru ke old
      }
    }
    // Set hasilnya kembali ke Bal
    curBal.setBalAcctItemTypeList(curMap.values().toArray(new BalAcctItemTypeDto[0]));
    oldBal.setBalAcctItemTypeList(oldMap.values().toArray(new BalAcctItemTypeDto[0]));
  }

  public static Boolean isInclueResvBal() {
    boolean isInclueResvBal = true;
    // Optional<ConfigItemParamProjection> findIsIncludeResvBalStr =
    // configItemRepository.findConfigItem("ACCT", "FEE",
    // "IS_INCLUDE_RESV_BAL");
    // String isInclueResvBalStr = findIsIncludeResvBalStr
    // .map(ConfigItemParamProjection::getDefaultValue)
    // .orElse(null);
    // if ("N".equals(isInclueResvBalStr))
    // isInclueResvBal = false;
    return Boolean.valueOf(isInclueResvBal);
  }

  public void checkCreditBalUpdate(BalDto[] updateBalList) {
    if (updateBalList == null)
      return;
    for (BalDto updateBal : updateBalList) {
      logger.debug("check one bal[{}] ", updateBal);
      if (updateBal.getAcctResDto() != null &&
          updateBal.getAcctResDto().getBalType().equals(6L))
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00260"));
    }
  }

  public static BalDto getDefaultBal(BalDto[] paramArrayOfBal) {
    if (paramArrayOfBal == null)
      return null;
    BalDto bal = null;
    Long long_ = Long.valueOf(systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID"));
    for (byte b = 0; b < paramArrayOfBal.length; b++) {
      if (long_.equals(paramArrayOfBal[b].getAcctResId())) {
        bal = paramArrayOfBal[b];
        break;
      }
    }
    return bal;
  }

  public void checkBalByCheckMode(BalDto[] updateBalList, BalDto[] newBalList, Long creditLimit,
      String acctBookType) {
    logger.debug("checkBalOverdraft. updateBalList=[{}], newBalList=[{}], creditLimit=[{}]", updateBalList, newBalList,
        creditLimit);
    if (CommonUtil.isEmpty((Object[]) updateBalList) || CommonUtil.isEmpty((Object[]) newBalList))
      return;
    if (creditLimit == null)
      creditLimit = Long.valueOf(0L);
    List<BalDto> operatorList = mergeAddList(updateBalList);
    LocalDateTime now = LocalDateTime.now();
    for (BalDto updateBal : operatorList) {
      logger.debug("check one bal[{}] ", updateBal);
      if (updateBal.getCheckMode().isEmpty() || "0|0|0"
          .equals(updateBal.getCheckMode()))
        continue;
      if (updateBal.getCharge().longValue() <= 0L)
        continue;
      BalDto operBal = getBalFromListByBalId(newBalList, updateBal.getBalId());
      // AssertUtil.isNotNull(operBal, "fail to get bal in newBalList by id");
      checkOperBal(operBal, now, creditLimit, updateBal.getCheckMode());
    }
    balHelperExt.checkBalMaxValue(updateBalList, newBalList, acctBookType);
  }

  public static List<BalDto> mergeAddList(BalDto[] addBalList) {
    Hashtable<Long, BalDto> uniqueList = new Hashtable<>();
    ArrayList<BalDto> operatorList = new ArrayList<>();
    if (addBalList == null || addBalList.length == 0) {
      logger.debug("get no bal for balance merge.");
      return operatorList;
    }
    int i;
    for (i = 0; i < addBalList.length; i++) {
      BalDto currentBal = addBalList[i];
      if ("A".equals(currentBal.getOperationType())) {
        BalDto bal = copyBalInfo(currentBal);
        uniqueList.put(bal.getBalId(), bal);
        operatorList.add(bal);
      }
    }
    for (i = 0; i < addBalList.length; i++) {
      BalDto currentBal = addBalList[i];
      if ("M".equals(currentBal.getOperationType()) || "F".equals(currentBal.getOperationType())) {
        BalDto oldUpdate = uniqueList.get(currentBal.getBalId());
        if (oldUpdate == null) {
          oldUpdate = copyBalInfo(currentBal);
          uniqueList.put(oldUpdate.getBalId(), oldUpdate);
          operatorList.add(oldUpdate);
        } else {
          mergeBalUpdateOper(currentBal, oldUpdate);
        }
      }
    }
    logger.debug("Bal list after merge operation. balList=[{}].", operatorList);
    return operatorList;
  }

  private static void mergeBalUpdateOper(BalDto curUpdate, BalDto oldUpdate) {
    long oldBal = 0L;
    if (oldUpdate.getGrossBal() != null)
      oldBal = oldUpdate.getGrossBal().longValue();
    long curCharge = 0L;
    if (curUpdate.getCharge() != null)
      curCharge = curUpdate.getCharge().longValue();
    long oldCharge = 0L;
    if (oldUpdate.getCharge() != null)
      oldCharge = oldUpdate.getCharge().longValue();
    long curSeconds = 0L;
    if (curUpdate.getSeconds() != null)
      curSeconds = curUpdate.getSeconds().longValue();
    long oldSeconds = 0L;
    if (oldUpdate.getSeconds() != null)
      oldSeconds = oldUpdate.getSeconds().longValue();
    long curEffSeconds = 0L;
    if (curUpdate.getEffSeconds() != null)
      curEffSeconds = curUpdate.getEffSeconds().longValue();
    long oldEffSeconds = 0L;
    if (oldUpdate.getEffSeconds() != null)
      oldEffSeconds = oldUpdate.getEffSeconds().longValue();
    if ("M".equals(oldUpdate.getOperationType()) || "F".equals(oldUpdate.getOperationType())) {
      oldUpdate.setCharge(Long.valueOf(curCharge + oldCharge));
      oldUpdate.setSeconds(Long.valueOf(curSeconds + oldSeconds));
      oldUpdate.setEffSeconds(Long.valueOf(curEffSeconds + oldEffSeconds));
      if ("F".equals(oldUpdate.getOperationType())) {
        oldUpdate.setExpDate(curUpdate.getExpDate());
        oldUpdate.setOperationType("F");
      }
    }
    if ("A".equals(oldUpdate.getOperationType())) {
      oldUpdate.setGrossBal(Long.valueOf(curCharge + oldBal));
      if (oldUpdate.getExpDate() == null) {
        oldUpdate.setExpDate(null);
      } else {
        oldUpdate.setExpDate(DateUtil.offsetSecond(oldUpdate.getExpDate(), curSeconds));
      }
      oldUpdate.setEffDate(DateUtil.offsetSecond(oldUpdate.getEffDate(), curEffSeconds));
    }
    mergeBalCheckMode(curUpdate, oldUpdate);
    mergeAbsExpOffset(curUpdate, oldUpdate);
    mergeConsumeBalCharge(curUpdate, oldUpdate);
    mergeInitBal(curUpdate, oldUpdate);
  }

  private static void mergeBalCheckMode(BalDto curUpdate, BalDto oldUpdate) {
    if (oldUpdate.getCheckMode().isEmpty())
      oldUpdate.setCheckMode("0|0|0");
    if (curUpdate.getCheckMode().isEmpty())
      curUpdate.setCheckMode("0|0|0");
    String[] oldFlagList = oldUpdate.getCheckMode().split("\\|");
    String[] curFlagList = curUpdate.getCheckMode().split("\\|");
    for (int i = 0; i < 3; i++) {
      if ("1".equals(curFlagList[i]))
        oldFlagList[i] = "1";
    }
    String checkMode = oldFlagList[0] + "|" + oldFlagList[1] + "|" + oldFlagList[2];
    oldUpdate.setCheckMode(checkMode);
  }

  private static void mergeAbsExpOffset(BalDto curUpdate, BalDto oldUpdate) {
    if (curUpdate.getAbsExpOffset() != null && oldUpdate.getAbsExpOffset() == null)
      oldUpdate.setAbsExpOffset(curUpdate.getAbsExpOffset());
  }

  private static void mergeConsumeBalCharge(BalDto curUpdate, BalDto oldUpdate) {
    if (curUpdate.getConsumeBalCharge() != null)
      if ("M".equals(oldUpdate.getOperationType()) || "F".equals(oldUpdate.getOperationType())) {
        if (oldUpdate.getConsumeBalCharge() != null) {
          oldUpdate.setConsumeBalCharge(
              Long.valueOf(oldUpdate.getConsumeBalCharge().longValue() + curUpdate.getConsumeBalCharge().longValue()));
        } else {
          oldUpdate.setConsumeBalCharge(curUpdate.getConsumeBalCharge());
        }
      } else if ("A".equals(oldUpdate.getOperationType())) {
        oldUpdate.setConsumeBal(
            Long.valueOf(oldUpdate.getConsumeBal().longValue() + curUpdate.getConsumeBalCharge().longValue()));
        oldUpdate
            .setRealBal(Long.valueOf(oldUpdate.getRealBal().longValue() + curUpdate.getConsumeBalCharge().longValue()));
      }
  }

  private static void mergeInitBal(BalDto curUpdate, BalDto oldUpdate) {
    if (curUpdate.getInitBal() != null)
      oldUpdate.setInitBal(curUpdate.getInitBal());
  }

  private static void checkOperBal(BalDto operBal, LocalDateTime now, Long creditLimit, String checkMode) {
    String[] modeFlagList = checkMode.split("\\|");
    if ("1".equals(modeFlagList[0])) {
      Long checkAmount = Long.valueOf(0L);
      Long defaultAcctResId = Long.valueOf(systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID"));
      if (defaultAcctResId.equals(operBal.getAcctResId())) {
        checkAmount = Long.valueOf(operBal.getRealBal().longValue() + creditLimit.longValue());
      } else {
        checkAmount = operBal.getRealBal();
      }
      if (checkAmount.longValue() > 0L) {
        // Optional<ConfigItemParamProjection> findPoAcctResIds =
        // configItemRepository.findConfigItem("ACCT",
        // "ACCT_BILLING", "PO_ACCT_RES_IDS_4_COMPOUND");
        // String poAcctResIds = findPoAcctResIds
        // .map(ConfigItemParamProjection::getDefaultValue)
        // .orElse("");
        // StringBuffer sb = new StringBuffer();
        // if (CommonUtil.isInCommaText(poAcctResIds, operBal.getAcctResId().toString(),
        // ',')) {
        // sb.append("The PO amount insufficient.");
        // throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        // MessageService.getMessage("BL-S-ACT-00396"));
        // } else {
        // sb.append("The balance is overdrafted.");
        // throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        // MessageService.getMessage("BL-S-ACT-00146"));
        // }
        // sb.append("RealBal:")
        // sb.append(operBal.getRealBal());
        // sb.append(",ReserveBal:");
        // sb.append(operBal.getReserveBal());
        // sb.append(",CreditLimit:");
        // sb.append(creditLimit);
        // logger.warn(sb.toString());
      }
    }
    if ("1".equals(modeFlagList[1]))
      if (operBal.getEffDate() != null && operBal.getEffDate().isAfter(now)) {
        logger.debug("the effective date of balance is [{}].",
            DateUtil.date2String(operBal.getEffDate(), "yyyy-MM-dd"));
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00147"));
      }
    if ("1".equals(modeFlagList[2]))
      if (operBal.getExpDate() != null && DateUtil.compare(operBal.getExpDate(), now) < 2) {
        logger.debug("the expired date of balance is [{}].", DateUtil.date2String(operBal.getExpDate(), "yyyy-MM-dd"));
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00152"));
      }
  }

  public void checkPostpaidBalUpdate(BalDto[] updateBalList, BalDto[] newBalList) {
    if (CommonUtil.isEmpty((Object[]) updateBalList) || CommonUtil.isEmpty((Object[]) newBalList))
      return;
    List<BalDto> checkoverdrafteBalList = new ArrayList<>();
    for (BalDto updateBal : updateBalList) {
      logger.debug("check one bal[{}] ", updateBal);
      if (!updateBal.getPostCheckMode().isEmpty()) {
        String[] modeFlagList = updateBal.getPostCheckMode().split("\\|");
        Long defaultAcctResId = Long.valueOf(systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID"));
        if (defaultAcctResId.equals(updateBal.getAcctResId())) {
          if ("1".equals(modeFlagList[0]) && updateBal.getCharge().longValue() > 0L) {
            // Optional<ConfigItemParamProjection> findIsPostPaidCanDeductMainBal =
            // configItemRepository
            // .findConfigItem("ACCT", "PAYMENT", "IS_POSTPAID_CAN_DEDUCT_MAIN_BAL");
            // String isPostPaidCanDeductMainBal = findIsPostPaidCanDeductMainBal
            // .map(ConfigItemParamProjection::getDefaultValue)
            // .orElse("");
            // logger.debug("isPostPaidCanDeductMainBal is :{}",
            // isPostPaidCanDeductMainBal);
            // if ("Y".equals(isPostPaidCanDeductMainBal)) {
            // checkoverdrafteBalList.add(updateBal);
            // } else {
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            // MessageService.getMessage("BL-S-ACT-00239"));
            // }
          }
        } else {
          checkoverdrafteBalList.add(updateBal);
        }
      }
    }
    if (!checkoverdrafteBalList.isEmpty())
      checkBalByCheckMode(checkoverdrafteBalList.toArray(new BalDto[0]), newBalList, null, null);
  }

  public static BalDto[] filterExpireBal(BalDto[] allBalList) {
    if (allBalList == null)
      return null;
    List<BalDto> resultBalList = new ArrayList<>();
    LocalDateTime now = DateUtil.GetDBDateTime();
    Long garcePeriod = null;
    Character refillable = null;
    LocalDateTime gracePeriorDate = null;
    for (BalDto bal : allBalList) {
      AcctResDto acctResDto = bal.getAcctResDto();
      if (acctResDto != null)
        if ("Y".equals(acctResDto.getRefillable())) {
          resultBalList.add(bal);
        } else if (bal.getExpDate() == null) {
          resultBalList.add(bal);
        } else if (bal.getExpDate().isAfter(now)) {
          resultBalList.add(bal);
        } else {
          refillable = acctResDto.getRefillable();
          if ("L".equals(refillable)) {
            garcePeriod = acctResDto.getGracePeriod();
            if (garcePeriod != null && garcePeriod.longValue() > 0L) {
              gracePeriorDate = DateUtil.offsetDay(bal.getExpDate(),
                  Integer.parseInt(garcePeriod.toString()));
              if (gracePeriorDate.isAfter(now))
                resultBalList.add(bal);
            }
          }
        }
    }
    return resultBalList.toArray(new BalDto[resultBalList.size()]);
  }

  public static void validateMaxExpDate(Long maxExpireDate, LocalDateTime expDate, LocalDateTime now) {
    if (maxExpireDate == null || expDate == null)
      return;
    LocalDateTime maxExpDate = DateUtil.offsetDay(DateUtil.getFullDate(now), maxExpireDate.intValue());
    logger.debug("The real expDate=[{}], and the maxExpDate=[{}]", new Object[] { expDate, maxExpDate });
    long differSecond = DateUtil.differDateInDays(expDate, maxExpDate, 0);
    if (differSecond > 0L)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00298"));
    // ExceptionHandler.publish("BL-S-ACT-00298", 0);
  }

}
