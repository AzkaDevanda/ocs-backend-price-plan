package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.RecordBalanceChangeParam;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BillingHelper;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.AcctResRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.SystemParamRepository;
import com.sts.sinorita.util.DateUtil;
import com.sts.sinorita.util.StringUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class BalAdjustDataPrepareService {

  private final ConfigItemRepository configItemRepository;

  private final SystemParamRepository systemParamRepository;

  private final AcctResRepository acctResRepository;

  private Long acctId;

  private Long balId;

  private Long charge;

  private LocalDateTime effDate;

  private LocalDateTime expDate;

  private Long days;

  private Long seconds;

  private Long months;

  private Long balance;

  private Long balanceCalcFlag;

  private Boolean canCreateBal;

  private Long expDateExtendFlag;

  private AcctResDto acctResDto;

  private String postpaid;

  private BalDto[] allBalList;

  private LocalDateTime dateNow;

  private BalDto[] oldBalList;

  private BalDto[] newBalList;

  private Long balAdjustAmount;

  private LocalDateTime balAdjustEffDate;

  private LocalDateTime balAdjustExpDate;

  private Long balAdjustSeconds;

  private Long balAdjustDays;

  private LocalDateTime balAdjustPreExpDate;

  private BalDto balBeforeAdjust;

  private RecordBalanceChangeParam recordBalanceChangeParam;

  private Long ceilLimit;

  private Long ceilLimitCharge;

  private Map<String, Object> extMap;

  public Information getInformation() {
    return null;
  }

  public void invoke() {
    this.oldBalList = BalHelper.filterExpireBal(this.allBalList);
    this.newBalList = this.oldBalList;
    this.balBeforeAdjust = getAdjustBalInfo();
    makeRecordBalChangeRequest();
  }

  public BalDto getAdjustBalInfo() {
    BalDto balBeforeAdjust = null;
    if (this.balId != null) {
      balBeforeAdjust = BalHelper.getBalFromListByBalId(this.oldBalList, this.balId);
      if (balBeforeAdjust == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00043"));
      // ExceptionHandler.publish("BL-S-ACT-00043", 0);
      this.acctResDto = acctResRepository.selectAcctRes(balBeforeAdjust.getAcctResId()).get();
    } else {
      BalDto[] balListTemp = BalHelper.getBalFromListByAcctResId(this.oldBalList, this.acctResDto.getAcctResId());
      if (balListTemp != null && balListTemp.length != 0)
        balBeforeAdjust = findBalFromBalList(balListTemp);
      if (balListTemp == null || balListTemp.length == 0 || balBeforeAdjust == null)
        if (this.canCreateBal != null && this.canCreateBal.booleanValue()) {
          balBeforeAdjust = createAdjustBal();
        } else {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00043"));
          // ExceptionHandler.publish("BL-S-ACT-00043", 0);
        }
    }
    return balBeforeAdjust;
  }

  private BalDto findBalFromBalList(BalDto[] balListTemp) {
    BalDto balBeforeAdjust = null;
    if (balListTemp[0] != null && "L".equals(balListTemp[0].getAcctResDto().getRefillable())) {
      balBeforeAdjust = findLBalFromBalList(balListTemp);
    } else if (balListTemp[0] != null && "M".equals(balListTemp[0].getAcctResDto().getRefillable())) {
      balBeforeAdjust = findMBalFromBalList(balListTemp);
    } else if (balListTemp[0] != null && "Y".equals(balListTemp[0].getAcctResDto().getRefillable())) {
      balBeforeAdjust = findYBalFromBalList(balListTemp);
    } else if (balListTemp[0] != null && "N".equals(balListTemp[0].getAcctResDto().getRefillable())) {
      balBeforeAdjust = findNBalFromBalList(balListTemp);
    }
    return balBeforeAdjust;
  }

  private BalDto findLBalFromBalList(BalDto[] balListTemp) {
    BalDto tempBal = null;
    for (int i = 0; i < balListTemp.length; i++) {
      tempBal = balListTemp[i];
      if (tempBal.getEffDate() != null && DateUtil.compare(tempBal.getEffDate(), this.dateNow) < 2 && (tempBal
          .getExpDate() == null || tempBal.getExpDate().isAfter(this.dateNow)))
        return tempBal;
    }
    return tempBal;
  }

  private BalDto findMBalFromBalList(BalDto[] balListTemp) {
    BalDto tempBal = null;
    for (int i = 0; i < balListTemp.length; i++) {
      tempBal = balListTemp[i];
      if (this.effDate != null && this.expDate != null && this.effDate.equals(tempBal.getEffDate()) && this.expDate
          .equals(tempBal.getExpDate()))
        return tempBal;
    }
    return tempBal;
  }

  private BalDto findYBalFromBalList(BalDto[] balListTemp) {
    BalDto tempBal = null;
    if (balListTemp.length > 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00045"));
      // ExceptionHandler.publish("BL-S-ACT-00045", 0);
    } else if (balListTemp.length == 1) {
      tempBal = balListTemp[0];
    }
    return tempBal;
  }

  private BalDto findNBalFromBalList(BalDto[] balListTemp) {
    if (balListTemp.length >= 1)
      return createAdjustBal();
    return null;
  }

  public BalDto createAdjustBal() {
    BalDto balBeforeAdjust = null;
    if (this.acctResDto.getAcctResId().longValue() == Long
        .parseLong(systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID")))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00043"));
    // ExceptionHandler.publish("BL-S-ACT-00043", 0);
    if (this.expDateExtendFlag.longValue() == 1L) {
      balBeforeAdjust = new BalDto();
      balBeforeAdjust.setAcctId(this.acctId);
      balBeforeAdjust.setAcctResId(this.acctResDto.getAcctResId());
      balBeforeAdjust.setGrossBal(Long.valueOf(0L));
      balBeforeAdjust.setRealBal(Long.valueOf(0L));
      balBeforeAdjust.setEffDate((this.effDate == null) ? DateUtil.getFullDate(this.dateNow) : this.effDate);
      balBeforeAdjust.setExpDate(balBeforeAdjust.getEffDate());
    } else {
      BalDto defaultBal = BalHelper.getDefaultBal(this.oldBalList);
      if (defaultBal == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00043"));
        // ExceptionHandler.publish("BL-S-ACT-00047", 0);
      } else {
        balBeforeAdjust = new BalDto();
        balBeforeAdjust.setAcctId(this.acctId);
        balBeforeAdjust.setAcctResId(this.acctResDto.getAcctResId());
        balBeforeAdjust.setGrossBal(Long.valueOf(0L));
        balBeforeAdjust.setRealBal(Long.valueOf(0L));
        balBeforeAdjust.setEffDate(defaultBal.getEffDate());
        balBeforeAdjust.setExpDate(defaultBal.getExpDate());
      }
    }
    return balBeforeAdjust;
  }

  public void makeRecordBalChangeRequest() {
    if (this.recordBalanceChangeParam == null)
      this.recordBalanceChangeParam = new RecordBalanceChangeParam();
    long tmpcharge = 0L;
    if (this.balance != null) {
      if (this.balanceCalcFlag.longValue() == 1L) {
        tmpcharge = this.balance.longValue() - this.balBeforeAdjust.getRealBal().longValue();
      } else {
        tmpcharge = this.balance.longValue() - this.balBeforeAdjust.getGrossBal().longValue();
      }
    } else if (this.charge != null) {
      tmpcharge = this.charge.longValue();
    }
    this.balAdjustAmount = Long.valueOf(tmpcharge);
    Long tmpCeilLimitCharge = calculateCeilLimitCharge();
    this.balAdjustEffDate = (this.effDate == null) ? this.balBeforeAdjust.getEffDate() : this.effDate;
    Optional<ConfigItemParamProjection> findSupprortAbsExpDate = configItemRepository.findConfigItem("ACCT",
        "ACCT_BILLING", "SUPPORT_ABS_EXP_DATE");
    String supprortAbsExpDate = findSupprortAbsExpDate.map(ConfigItemParamProjection::getDefaultValue).orElse(null);
    // String supprortAbsExpDate =
    // ConfigItemCache.instance().getString("ACCT.ACCT_BILLING.SUPPORT_ABS_EXP_DATE");
    if ("Y".equals(supprortAbsExpDate) && this.balBeforeAdjust.getExpDate() == null && this.expDate != null) {
      String dateNowStr = DateUtil.date2String(this.dateNow, "yyyy-MM-dd");
      LocalDateTime tmpDate = DateUtil.string2SQLDate(dateNowStr + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
      this.balBeforeAdjust.setExpDate(tmpDate);
    }
    if (this.expDate != null && this.balBeforeAdjust.getExpDate() != null) {
      this.balAdjustExpDate = this.expDate;
    } else {
      this.balAdjustExpDate = calBalAdjustExpDate(this.seconds, this.days, this.months);
    }
    if (this.balBeforeAdjust.getBalId() == null && this.expDateExtendFlag.longValue() != 1L)
      this.balAdjustPreExpDate = this.balBeforeAdjust.getExpDate();
    this.recordBalanceChangeParam.setOperateAmount(this.balAdjustAmount);
    this.recordBalanceChangeParam.setOperateEffDate(this.balAdjustEffDate);
    this.recordBalanceChangeParam.setOperateExpDate(this.balAdjustExpDate);
    this.recordBalanceChangeParam.setOperateSeconds(this.balAdjustSeconds);
    this.recordBalanceChangeParam.setOperateDays(this.balAdjustDays);
    this.recordBalanceChangeParam.setNewBalPreExpDate(this.balAdjustPreExpDate);
    this.recordBalanceChangeParam.setOperateCeilLimitCharge(tmpCeilLimitCharge);
  }

  public Long calculateCeilLimitCharge() {
    Long tmpCeilLimitCharge = null;
    if (this.ceilLimit != null) {
      if (this.balBeforeAdjust.getCeilLimit() != null) {
        tmpCeilLimitCharge = Long.valueOf(this.ceilLimit.longValue() - this.balBeforeAdjust.getCeilLimit().longValue());
      } else {
        tmpCeilLimitCharge = this.ceilLimit;
      }
    } else if (this.ceilLimitCharge != null) {
      tmpCeilLimitCharge = this.ceilLimitCharge;
    }
    return tmpCeilLimitCharge;
  }

  private LocalDateTime calBalAdjustExpDate(Long seconds, Long days, Long months) {
    LocalDateTime retDate = this.balBeforeAdjust.getExpDate();
    String balProlongPolicy = "";
    if (this.extMap != null && !this.extMap.isEmpty())
      balProlongPolicy = (String) this.extMap.get("PROLONG_POLICY");
    if (StringUtil.isEmpty(balProlongPolicy))
      balProlongPolicy = BillingHelper.getStringFromConfig("ACCT.BALAJUST.BAL_PROLONG_POLICY", "E");
    if (this.balBeforeAdjust.getExpDate() != null)
      if ("B".equals(balProlongPolicy)) {
        if (seconds != null) {
          retDate = DateUtil.offsetSecond(this.dateNow, seconds.longValue());
        } else if (days != null) {
          retDate = DateUtil.offsetDay(this.dateNow, days.intValue());
        } else if (months != null) {
          retDate = DateUtil.offsetMonth(this.dateNow, months.intValue());
        }
        if (DateUtil.compare(retDate, this.balBeforeAdjust.getExpDate()) < 2)
          retDate = this.balBeforeAdjust.getExpDate();
      } else if ("B2".equals(balProlongPolicy)) {
        retDate = calBalAdjustExpDateB2Policy(seconds, days, months);
      } else if ("F".equals(balProlongPolicy)) {
        if (seconds != null) {
          retDate = DateUtil.offsetSecond(this.dateNow, seconds.longValue());
        } else if (days != null) {
          retDate = DateUtil.offsetDay(this.dateNow, days.intValue());
        } else if (months != null) {
          retDate = DateUtil.offsetMonth(this.dateNow, months.intValue());
        }
      } else if ("O".equals(balProlongPolicy)) {
        retDate = calBalAdjustExpDateOPolicy(seconds, days, months);
      } else if (seconds != null) {
        retDate = DateUtil.offsetSecond(this.balBeforeAdjust.getExpDate(), seconds.longValue());
      } else if (days != null) {
        retDate = DateUtil.offsetDay(this.balBeforeAdjust.getExpDate(), days.intValue());
      } else if (months != null) {
        retDate = DateUtil.offsetMonth(this.balBeforeAdjust.getExpDate(), months.intValue());
      }
    return retDate;
  }

  private LocalDateTime calBalAdjustExpDateB2Policy(Long seconds, Long days, Long months) {
    LocalDateTime retDate = this.balBeforeAdjust.getExpDate();
    if (seconds != null) {
      retDate = DateUtil.offsetSecond(this.dateNow, seconds.longValue());
    } else if (days != null) {
      LocalDateTime date1 = DateUtil.offsetDay(DateUtil.getFullDate(this.dateNow), days.intValue());
      Long newDays = Long
          .valueOf(DateUtil.differDateInDays(date1, DateUtil.getFullDate(this.balBeforeAdjust.getExpDate()), 3));
      retDate = DateUtil.offsetDay(this.balBeforeAdjust.getExpDate(), newDays.intValue());
    } else if (months != null) {
      retDate = DateUtil.offsetMonth(this.dateNow, months.intValue());
    }
    if (DateUtil.compare(retDate, this.balBeforeAdjust.getExpDate()) < 2)
      retDate = this.balBeforeAdjust.getExpDate();
    return retDate;
  }

  private LocalDateTime calBalAdjustExpDateOPolicy(Long seconds, Long days, Long months) {
    LocalDateTime retDate = this.balBeforeAdjust.getExpDate();
    Boolean experiedFlag = Boolean.FALSE;
    if (this.dateNow.isAfter(retDate))
      experiedFlag = Boolean.TRUE;
    if (seconds != null) {
      if (experiedFlag.booleanValue()) {
        retDate = DateUtil.offsetSecond(this.dateNow, seconds.longValue());
      } else {
        retDate = DateUtil.offsetSecond(this.balBeforeAdjust.getExpDate(), seconds.longValue());
      }
    } else if (days != null) {
      if (experiedFlag.booleanValue()) {
        retDate = DateUtil.offsetDay(this.dateNow, days.intValue());
      } else {
        retDate = DateUtil.offsetDay(this.balBeforeAdjust.getExpDate(), days.intValue());
      }
    } else if (months != null) {
      if (experiedFlag.booleanValue()) {
        retDate = DateUtil.offsetMonth(this.dateNow, months.intValue());
      } else {
        retDate = DateUtil.offsetMonth(this.balBeforeAdjust.getExpDate(), months.intValue());
      }
    }
    return retDate;
  }

}
