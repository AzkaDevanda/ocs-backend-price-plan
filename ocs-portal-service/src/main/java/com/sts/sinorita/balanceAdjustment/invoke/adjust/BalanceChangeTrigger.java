package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sts.sinorita.balanceAdjustment.DebitMgrDataFill;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.balanceAdjustment.invoke.DebtManager;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingBaseDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingRequest;
import com.sts.sinorita.dto.request.balanceAdjustment.DisputeDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.DisputeRequest;
import com.sts.sinorita.dto.request.balanceAdjustment.ExtAttrBalanceChangeTrigger;
import com.sts.sinorita.dto.request.balanceAdjustment.RechargeDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.ReverseDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.dto.request.balanceAdjustment.adjust.BalanceChangeTriggerDict;
import com.sts.sinorita.entity.Acct;
import com.sts.sinorita.entity.Bal;
import com.sts.sinorita.entity.Subs;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BillingHelper;
import com.sts.sinorita.mapper.acct.AcctMapper;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.SystemParamRepository;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.StringUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class BalanceChangeTrigger {

  private final DebtManager debtManager;

  private final ConfigItemRepository configItemRepository;

  private final SystemParamRepository systemParamRepository;

  private final AcctMapper acctMapper;

  private String accNbr;

  private Long charge;

  private Long contactChannelId;

  private Long spId;

  private Map<String, Object> extMap;

  private AcctBookData acctBookData;

  private SubsDto subs;

  private AcctDto acct;

  private BalDto[] oldBalList;

  private BalDto[] updateBalList;

  private BalDto[] newBalList;

  private String acctBookType;

  // @DataMapping(mapping = "this", required = true)
  private BillingBaseDataBus dataBus;

  private final DebitMgrDataFill debitMgrDataFill;

  public void invoke() {
    try {
      if (this.dataBus == null)
        return;
      this.acctBookType = this.dataBus.getAcctBookType();
      this.acctBookData = this.dataBus.getAcctBookData();
      this.acct = this.dataBus.getAcct();
      if (this.acct != null) {
        this.oldBalList = this.acct.getOldBalList();
        this.updateBalList = this.acct.getUpdateBalList();
        this.newBalList = this.acct.getNewBalList();
      }
      this.subs = this.dataBus.getSubs();
      BillingRequest billingRequest = this.dataBus.getRequest();
      if (billingRequest != null) {
        this.accNbr = billingRequest.getAccNbr();
        this.charge = billingRequest.getCharge();
        this.contactChannelId = billingRequest.getContactChannelId();
        this.spId = billingRequest.getSpId();
        this.extMap = billingRequest.getExtMap();
      }
      callServiceSwitchAcctBookType();
    } catch (Exception e) {
      log.warn("Fail to change life cycle.", e);
      // ExceptionHandler.publish("BL-S-ACT-00108", 0);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00108"));
    }
    debitMgrDataFill.invoke();
  }

  private void callServiceSwitchAcctBookType() {
    String acctBookTypesNoLifycycle = configItemRepository
        .findConfigItem("ACCT", "ACCOUNT_PUBLIC", "ACCT_BOOK_TYPES_NO_LIFECYCLE")
        .map(ConfigItemParamProjection::getParamValue).orElse("");
    if (StringUtil.isNotEmpty(acctBookTypesNoLifycycle) &&
        CommonUtil.isInCommaText(acctBookTypesNoLifycycle, this.acctBookType)) {
      log.debug("not need deal lifyCycle, acctBookType is {}", new Object[] { this.acctBookType });
      return;
    }
    if ("H".equals(this.acctBookType)) {
      balanceChangeTriggerForBalAdjust();
    } else if ("L".equals(this.acctBookType)) {
      balanceChangeTriggerForBalTransferIn();
    } else if ("W".equals(this.acctBookType)) {
      balanceChangeTriggerForInstalment();
    } else if ("P".equals(this.acctBookType)) {
      if (this.dataBus instanceof RechargeDataBus) {
        balanceChangeTriggerForRecharge();
      } else if (this.dataBus instanceof ReverseDataBus) {
        balanceChangeTriggerForRechargeReverse();
      }
    } else if ("A".equals(this.acctBookType)) {
      balanceChangeTriggerForPostInvAdjust();
    } else if ("D".equals(this.acctBookType)) {
      DisputeDataBus disputeDataBus = (DisputeDataBus) this.dataBus;
      DisputeRequest disputeRequest = (DisputeRequest) disputeDataBus.getRequest();
      Long processType = disputeRequest.getProcessType();
      if (processType == null || processType.longValue() == 2L)
        balanceChangeTriggerForDispute();
    } else if ("T".equals(this.acctBookType)) {
      balanceChangeTriggerForBalAdjust();
    } else if ("M".equals(this.acctBookType)) {
      balanceChangeTriggerForBalAdjust();
    }
  }

  public void balanceChangeTriggerForBalAdjust() {
    String needJudgeAcctResIds = systemParamRepository.selectSystemParam("PRECHARGE_ACCT_RES_ID");
    if (StringUtil.isEmpty(needJudgeAcctResIds)) {
      needJudgeAcctResIds = systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID");
    } else {
      needJudgeAcctResIds = needJudgeAcctResIds + "," + systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID");
    }
    log.debug("needJudgeAcctResIds = [{}]", new Object[] { needJudgeAcctResIds });
    boolean isCallBalanceChangeTrigger = false;
    if (this.updateBalList != null)
      for (int i = 0; i < this.updateBalList.length; i++) {
        if (CommonUtil.isInCommaText(needJudgeAcctResIds, this.updateBalList[i].getAcctResId().toString())) {
          isCallBalanceChangeTrigger = true;
          break;
        }
      }
    log.debug("needCallBalanceChangeTrigger = [{}]", new Object[] { Boolean.valueOf(isCallBalanceChangeTrigger) });
    if (isCallBalanceChangeTrigger) {
      BalanceChangeTriggerDict balanceChangeTriggerDict = makeBalanceChangeTriggerDict();
      balanceChangeTriggerDict.setSpId(this.spId);
      callService(balanceChangeTriggerDict);
    }
  }

  public void balanceChangeTriggerForBalTransferIn() {
    String isTriggerLifeCycleForTransferIn = BillingHelper
        .getStringFromConfig("ACCT.TRANSFER.IS_TRIGGER_LIFE_CYCLE_FOR_TRANSFER_IN", "N");
    log.debug("isTriggerLifeCycleForTransferIn=[{}]", new Object[] { isTriggerLifeCycleForTransferIn });
    if ("Y".equals(isTriggerLifeCycleForTransferIn)) {
      BalanceChangeTriggerDict balanceChangeTriggerDict = makeBalanceChangeTriggerDict();
      balanceChangeTriggerDict.setSubsDto(this.subs);
      balanceChangeTriggerDict.setSpId(this.spId);
      callService(balanceChangeTriggerDict);
    }
  }

  public void balanceChangeTriggerForInstalment() {
    BalanceChangeTriggerDict balanceChangeTriggerDict = makeBalanceChangeTriggerDict();
    balanceChangeTriggerDict.setSpId(this.spId);
    callService(balanceChangeTriggerDict);
  }

  public void balanceChangeTriggerForRecharge() {
    // try {
      BalanceChangeTriggerDict balanceChangeTriggerDict = makeBalanceChangeTriggerDict();
      if (StringUtil.isNotEmpty(this.accNbr))
        balanceChangeTriggerDict.setSubsDto(this.subs);
      balanceChangeTriggerDict.setIsNewConnection(Boolean.FALSE);
      balanceChangeTriggerDict.setServType(0);
      String isNeedActiveSubs = getIsNeedActiveSubs();
      if (StringUtil.isNotEmpty(isNeedActiveSubs))
        balanceChangeTriggerDict.setIsNeedActiveSubs(isNeedActiveSubs);
      ExtAttrBalanceChangeTrigger extAttr = new ExtAttrBalanceChangeTrigger();
      if (this.subs != null)
        extAttr.setSubsId(this.subs.getSubsId());
      if (this.charge != null) {
        extAttr.setCharge(this.charge);
        log.debug("CHARGE is [{}]", new Object[] { this.charge });
      }
      if (this.extMap == null)
        this.extMap = new HashMap<>();
      String isLoanFeeEff = (String) this.extMap.get("IS_LOAN_FEE_EFF");
      if (StringUtil.isNotEmpty(isLoanFeeEff))
        extAttr.setIsLoanFreeEff(isLoanFeeEff);
      balanceChangeTriggerDict.setExtAttr(extAttr);
      balanceChangeTriggerDict.setSpId(this.spId);
      log.info("Balance change trigger spId:" + this.spId);
      callService(balanceChangeTriggerDict);
      List<BalDto> addBalList = balanceChangeTriggerDict.getAddBalList();
      BalDto[] curBalList = balanceChangeTriggerDict.getCurBalList();
      if (addBalList != null && !addBalList.isEmpty()) {
        this.newBalList = curBalList;
        ArrayList<BalDto> newUpdateBalList = new ArrayList<>(Arrays.asList(this.updateBalList));
        newUpdateBalList.addAll(addBalList);
        this.updateBalList = newUpdateBalList.<BalDto>toArray(new BalDto[0]);
      }
      extAttr = balanceChangeTriggerDict.getExtAttr();
      // if (extAttr != null) {
      //   this.extMap.put("SUBS_EVENT_ID", extAttr.getLong("SUBS_EVENT_ID"));
      //   this.extMap.put("ONCE_FEE", extAttr.getLong("ONCE_FEE"));
      //   this.extMap.put("IS_NEED_LIFE_CYCLE_INFO", extAttr.getBoolean("IS_NEED_LIFE_CYCLE_INFO"));
      // }
    // } catch (Exception ex) {
    //   if ("S-ACT-00112".equals(ex.getCode()))
    //     throw ex;
    //   log.warn("Fail to execute balance change trigger", (Throwable) ex);
    // } catch (Exception ex) {
    //   log.warn("Fail to execute balance change trigger", ex);
    // }
  }

  private String getIsNeedActiveSubs() {
    String needActiveSubs = "";
    if (this.charge != null && this.charge.longValue() <= 0L) {
      String forbiddenActivateChannelList = configItemRepository
          .findConfigItem("ACCT", "PAYMENT", "RECHARGE_NOT_ACTIVATE_CONTACT_CHANNEL")
          .map(ConfigItemParamProjection::getParamValue).orElse("");
      if (StringUtil.isNotEmpty(forbiddenActivateChannelList) && this.contactChannelId != null) {
        if (!CommonUtil.isInCommaText(forbiddenActivateChannelList, this.contactChannelId.toString())) {
          needActiveSubs = "1";
        } else {
          log.debug("RechargeNotActivateContactChannel=[{}], ContactChannelId=[{}]",
              new Object[] { forbiddenActivateChannelList, this.contactChannelId });
        }
      } else {
        needActiveSubs = "1";
      }
    }
    return needActiveSubs;
  }

  public void balanceChangeTriggerForRechargeReverse() {
    BalanceChangeTriggerDict balanceChangeTriggerDict = makeBalanceChangeTriggerDict();
    balanceChangeTriggerDict.setSubsDto(this.subs);
    balanceChangeTriggerDict.setSpId(this.spId);
    callService(balanceChangeTriggerDict);
  }

  public void balanceChangeTriggerForPostInvAdjust() {
    BalanceChangeTriggerDict balanceChangeTriggerDict = makeBalanceChangeTriggerDict();
    balanceChangeTriggerDict.setSpId(this.spId);
    callService(balanceChangeTriggerDict);
  }

  public void balanceChangeTriggerForDispute() {
    BalDto oldBasicBal = BalHelper.getDefaultBal(this.oldBalList);
    if (oldBasicBal != null && oldBasicBal.getRealBal().longValue() + this.charge.longValue() < 0L) {
      BalanceChangeTriggerDict balanceChangeTriggerDict = makeBalanceChangeTriggerDict();
      balanceChangeTriggerDict.setSpId(this.spId);
      callService(balanceChangeTriggerDict);
    }
  }

  public BalanceChangeTriggerDict makeBalanceChangeTriggerDict() {
    BalDto oldBasicBal = BalHelper.getDefaultBal(this.oldBalList);
    BalDto newBasicBal = BalHelper.getDefaultBal(this.newBalList);
    BalanceChangeTriggerDict balanceChangeTriggerDict = new BalanceChangeTriggerDict();
    if (this.acct != null) {
      balanceChangeTriggerDict.setAcctId(this.acct.getAcctId());
      balanceChangeTriggerDict.setAcct(this.acct);
    }
    // balanceChangeTriggerDict.set("OLD_BASIC_BAL", BoHelper.dtoToBO(oldBasicBal,
    // null));
    balanceChangeTriggerDict.setOldBasicBal(oldBasicBal);
    // balanceChangeTriggerDict.set("NEW_BASIC_BAL", BoHelper.dtoToBO(newBasicBal,
    // null));
    balanceChangeTriggerDict.setNewBasicBal(newBasicBal);
    // BoHelper.arrayDtoToBO("CUR_BAL_LIST", (Object[]) this.newBalList,
    // balanceChangeTriggerDict);
    balanceChangeTriggerDict.setCurBalList(this.newBalList);
    // BoHelper.arrayDtoToBO("OLD_BAL_LIST", (Object[]) this.oldBalList,
    // balanceChangeTriggerDict);
    balanceChangeTriggerDict.setOldBalList(this.oldBalList);
    balanceChangeTriggerDict.setAcctBookType(this.acctBookType);
    if (this.acctBookData != null)
      balanceChangeTriggerDict.setAcctBookId(this.acctBookData.getAcctBookId());
    balanceChangeTriggerDict.setSpId(this.spId);
    balanceChangeTriggerDict
        .setContactChannelId(this.contactChannelId == null ? null : this.contactChannelId.toString());
    return balanceChangeTriggerDict;
  }

  public void callService(BalanceChangeTriggerDict balanceChangeTriggerDict) {
    // balanceChangeTriggerDict.setServiceName("BalanceChangeTrigger");
    if (!"Y".equals(this.acct.getPostpaid()))
      // ServiceFlow.callService(balanceChangeTriggerDict);
      debtManager.balanceChangeTrigger(balanceChangeTriggerDict);
  }

}
