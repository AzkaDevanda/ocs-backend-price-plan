package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.balanceAdjustment.AcctBookReconcileService;
import com.sts.sinorita.balanceAdjustment.AcctBookService;
import com.sts.sinorita.balanceAdjustment.invoke.adjust.ValidateForBalAdjustService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.dto.request.balanceAdjustment.add.AddBalanceAccountRequestDto;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BillingSeqHelper;
import com.sts.sinorita.helper.CalcFeeHelper;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.DateUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class RecordBalanceChangeService {
  private final Logger log = LoggerFactory.getLogger(AddAdjustBalDataPrepareService.class);

  private final ValidateForAddAdjustBalService validateForAddAdjustBalService;

  private final AcctBookService acctBookService;

  private final AcctBookReconcileService acctBookReconcileService;

  private final BillingSeqHelper billingSeqHelper;

  private final AddAdjustBalDataFillService addAdjustBalDataFillService;

  private final ValidateBeforeDataStoreService validateBeforeDataStoreService;

  private final BillingTransactionStoreService billingTransactionStoreService;

  private final ValidateForBalAdjustService validateForBalAdjustService;

  private final BalHelper balHelper;

  // private AddAdjustBalApplicationRequestDto dto;

  private String acctBookType = "O";

  private Long balId;

  private Long charge;

  private Long seconds;

  private Long days;

  private LocalDateTime effDate;

  private LocalDateTime expDate;

  private LocalDateTime preExpDate;

  private Long ceilLimitCharge;

  private AcctDto acct;

  private BillDto currentBill;

  private BillDto bill;

  private String partyType;

  private String partyCode;

  private Long subsId;

  private String isSubsLimit;

  private Long contactChannelId;

  private String partnerSn = "";

  private String partnerDetail;

  private Long reconcileLogId;

  private String reconcileState = "A";

  private Long spId = 0L;

  private AcctResDto acctResDto;

  private AcctBookData acctBookData;

  private Map<String, Object> extMap;

  private Long sourceBalId;

  private LocalDateTime nowDate;

  private Object databus;

  private Character flag;

  public static BalDto getBalFromListByBalId(BalDto[] balList, Long balId) {
    if (balId == null || balList == null || balList.length <= 0)
      return null;
    BalDto bal = null;
    for (BalDto balDto : balList) {
      if (balId.equals(balDto.getBalId())) {
        bal = balDto;
        break;
      }
    }
    return bal;
  }

  public void invoke(AddBalanceAccountRequestDto requestDto) {
    this.charge = requestDto.getBalance() != null ? requestDto.getBalance() : 0L;
    this.partyCode = requestDto.getPartyCode();
    this.contactChannelId = requestDto.getContactChannelId();
    this.effDate = requestDto.getEffDate();
    this.expDate = requestDto.getExpDate();
    if (this.spId == null)
      this.spId = 0L;
    BalDto modBal = recordModBalInfo();
    recordAcctBookInfo(modBal);
    recordReconcileInfo();
    log.info("recordBalanceChangeService passed.");
    if (flag != null && flag.equals('A')) {
      validateForBalAdjustService.invoke();
    }
    validateForAddAdjustBalService.invoke(requestDto);

  }

  private BalDto recordModBalInfo() {
    BalDto modBal = new BalDto();
    modBal.setCharge(this.charge);
    modBal.setAcctId(this.acct.getAcctId());
    modBal.setAcctResId(this.acctResDto.getAcctResId());
    modBal.setCeilLimitCharge(this.ceilLimitCharge);
    if (this.balId == null && this.sourceBalId != null)
      this.balId = this.sourceBalId;
    if (this.balId != null) {
      BalDto oldBal = getBalFromListByBalId(this.acct.getAllBalList(), this.balId);
      if (oldBal == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00079"));
      modBal.setAcctId(oldBal.getAcctId());
      modBal.setAcctResId(oldBal.getAcctResId());
      modBal.setBalId(oldBal.getBalId());
    }
    if ("Y".equals(this.isSubsLimit) && this.subsId != null)
      modBal.setSubsId(this.subsId);
    LocalDateTime dateNow = LocalDateTime.now();
    modBal.setUpdateDate(dateNow);
    if (this.effDate != null) {
      modBal.setEffDate(this.effDate);
    } else {
      modBal.setEffSeconds(0L);
      modBal.setEffDate(LocalDateTime.of(dateNow.toLocalDate(), dateNow.toLocalTime()));
    }
    if (this.expDate != null)
      modBal.setExpDate(this.expDate);
    if (this.seconds != null) {
      modBal.setSeconds(this.seconds);
    } else if (this.days != null) {
      modBal.setSeconds(this.days * 86400L);
    }
    modBal.setPreExpDate(this.preExpDate);
    BalDto[] updateBalList = { modBal };
    BalDto[] newBalList = this.balHelper.addBalListPrepare(updateBalList, this.acct.getNewBalList(),
        this.acct.getRoutingId());
    this.acct.setNewBalList(newBalList);
    this.acct.setUpdateBalList(BalHelper.concatBalArray(this.acct.getUpdateBalList(), updateBalList));
    return modBal;
  }

  private void recordAcctBookInfo(BalDto modBal) {
    if (this.acctBookData == null)
      this.acctBookData = new AcctBookData();

    this.nowDate = (LocalDateTime) CommonUtil.nvl(this.nowDate, DateUtil.GetDBDateTime());
    this.acctBookData.setAcctBookType(this.acctBookType);
    this.acctBookData.setAcctBookId(billingSeqHelper.getBillingSeq("ACCT_BOOK_ID_SEQ"));
    this.acctBookData.setAcctId(modBal.getAcctId());
    this.acctBookData.setAcctResId(modBal.getAcctResId());
    this.acctBookData.setBalId(modBal.getBalId());
    this.acctBookData.setCharge(modBal.getCharge());
    this.acctBookData.setSeconds(modBal.getSeconds());
    this.acctBookData.setPreBalance(modBal.getPreBalance());
    this.acctBookData.setPreSuttleBal(modBal.getPreSuttleBal());
    this.acctBookData.setPreExpDate(modBal.getPreExpDate());
    this.acctBookData.setBillId(getAcctBookBillId(this.acctBookType));
    this.acctBookData.setCreatedDate(this.nowDate);
    this.acctBookData.setPartyType(this.partyType);
    this.acctBookData.setPartyCode(this.partyCode);
    this.acctBookData.setContactChannelId(this.contactChannelId);
    this.acctBookData.setSpId(this.spId);
    this.acctBookData.setEventPaymentId(null);
    this.acctBookData.setPreEffDate(modBal.getPreEffDate());
    this.acctBookData.setEffSeconds(modBal.getEffSeconds());
    this.acctBookData.setModBal(modBal);

    if (this.extMap != null) {
      this.acctBookData.setRefAttr(getRefAttr());
      if (this.extMap.get("ORG_ID") != null) {
        String value = (String) this.extMap.get("ORG_ID");
        this.acctBookData.setOrgId(Long.valueOf(value));
      }
    }
    addAdjustBalDataFillService.setAcctBookData(this.acctBookData);
    BillingBaseDataBus dataBus = new BillingBaseDataBus();
    dataBus.setAcctBookType(this.acctBookType);
    dataBus.setAcctBookData(acctBookData);
    validateBeforeDataStoreService.setDataBus(dataBus);

  }

  private String getRefAttr() {
    StringBuffer refAttrBuf = new StringBuffer();
    String[][] refAttrKeys = { { "CELL_ID" }, { "GEO_HOME_ZONE_CODE_LIST", ",", ":" },
        { "GEO_HOME_ZONE_CATG_CODE_LIST", ",", ":" }, { "UNIT" }, { "VALUE" }, { "REQUEST_ID" }, { "PAYMENT_SOURCE" },
        { "REMARKS" } };
    for (String[] key : refAttrKeys) {
      String value = (String) this.extMap.get(key[0]);
      if (!value.isEmpty()) {
        if (key.length == 3)
          value = value.replaceAll(key[1], key[2]);
        CalcFeeHelper.appendParam(refAttrBuf, key[0], value, null, null);
      }
    }
    if (!this.partnerSn.isEmpty())
      CalcFeeHelper.appendParam(refAttrBuf, "PARTNER_SN", this.partnerSn, null, null);
    if (this.databus instanceof RechargeDataBus) {
      RechargeRequest req = (RechargeRequest) ((BillingBaseDataBus) this.databus).getRequest();
      if (req != null) {
        String refAttr = req.getRefAttr();
        String tmpKV = "";
        if (!refAttr.isEmpty()) {
          Pattern pattern1 = Pattern.compile("(\\w+)=(\\w+)[,|;]*");
          Matcher matcher1 = pattern1.matcher(refAttr);
          Pattern pattern2 = Pattern.compile("(\\w+)=(\\w+)");
          Matcher matcher2 = null;
          while (matcher1.find()) {
            tmpKV = matcher1.group();
            matcher2 = pattern2.matcher(tmpKV);
            if (matcher2.find())
              refAttrBuf.append(";").append(matcher2.group());
          }
        }
      }
    }
    return refAttrBuf.toString();
  }

  private Long getAcctBookBillId(String acctBookType) {
    BillDto operBill = null;
    if ("O".equals(acctBookType)) {
      operBill = this.bill;
    } else {
      operBill = this.currentBill;
    }
    if (operBill != null)
      return operBill.getBillId();
    return null;
  }

  private void recordReconcileInfo() {
    if (!this.partnerSn.isEmpty()) {
      AcctBookReconcileDto acctBookReconcileDto = new AcctBookReconcileDto();
      acctBookReconcileDto.setAcctBookId(this.acctBookData.getAcctBookId());
      acctBookReconcileDto.setContactChannelId(this.acctBookData.getContactChannelId());
      acctBookReconcileDto.setPartnerSn(this.partnerSn);
      acctBookReconcileDto.setLogId(this.reconcileLogId);
      acctBookReconcileDto.setReconcileState(
          this.reconcileState.isEmpty() ? "A" : this.reconcileState);
      acctBookReconcileDto.setSpId(this.spId);
      acctBookReconcileDto.setAcctBookType(this.acctBookType);
      acctBookReconcileDto.setCharge(this.acctBookData.getCharge());
      acctBookReconcileDto.setCreatedDate(this.acctBookData.getCreatedDate());
      setPaymentDate(acctBookReconcileDto);

      if (!this.partnerDetail.isEmpty())
        acctBookReconcileDto.setPartnerDetail(this.partnerDetail);

      if (this.extMap != null && this.extMap.get("TRANSACTION_TYPE") != null)
        acctBookReconcileDto.setTransactionType((String) this.extMap.get("TRANSACTION_TYPE"));
      if (this.extMap != null && this.extMap.get("VC_RECONCILE_ID") != null)
        acctBookReconcileDto.setContactChannelId((Long) this.extMap.get("VC_RECONCILE_ID"));

      this.acctBookData.setReconcileDto(acctBookReconcileDto);
      acctBookReconcileService.saveReconcile(acctBookReconcileDto);
    }
  }

  private void setPaymentDate(AcctBookReconcileDto acctBookReconcileDto) {
    LocalDateTime paymentDate = this.acctBookData.getCreatedDate();
    // if ("P".equals(this.acctBookType)) {
    // Object dataBus = DataContext.getEntity();
    // if (dataBus instanceof RechargeDataBus rechargeDataBus) {
    // PaymentDto paymentDto = rechargeDataBus.getPaymentDto();
    // if (paymentDto != null)
    // paymentDate = paymentDto.getPaymentDate();
    // }
    // }
    acctBookReconcileDto.setPaymentDate(paymentDate);
  }

}
