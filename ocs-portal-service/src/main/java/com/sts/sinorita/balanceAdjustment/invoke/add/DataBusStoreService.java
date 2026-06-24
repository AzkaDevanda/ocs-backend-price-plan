package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.balanceAdjustment.DebitBalService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.entity.DebitItem;
import com.sts.sinorita.helper.AcmHelper;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BillingHelper;
import com.sts.sinorita.helper.CalcFeeHelper;
import com.sts.sinorita.mapper.accountConfig.BalAcctItemTypeMapper;
import com.sts.sinorita.mapper.balanceAdjustment.AcctDepositBalMapper;
import com.sts.sinorita.mapper.balanceAdjustment.DebitBalInstallMapper;
import com.sts.sinorita.projection.balanceAdjustment.SelectDebitBalMapper;
import com.sts.sinorita.projection.balanceAdjustment.SelectNotePayPaymentProjection;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.util.CommonUtil;
import com.sts.sinorita.util.ValidateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class DataBusStoreService {
  private final Logger logger = LoggerFactory.getLogger(DataBusStoreService.class);

  // =========== REPOSITORY ===============
  private final SystemParamRepository systemParamRepository;
  private final BillRepository billRepository;
  private final ConfigItemRepository configItemRepository;
  private final BillExRepository billExRepository;
  private final NbBillSegmentRepository nbBillSegmentRepository;
  private final PaymentPlanRepository paymentPlanRepository;
  private final InstalmentRepository instalmentRepository;
  private final AcctBookRepository acctBookRepository;
  private final PaymentSettRepository paymentSettRepository;
  private final PaymentSettItemRepository paymentSettItemRepository;
  private final AcctItemRepository acctItemRepository;
  private final AcctBookReconcileRepository acctBookReconcileRepository;
  private final BalTransferRepository balTransferRepository;
  private final DebitBalRepository debitBalRepository;
  private final DebitPaymentRepository debitPaymentRepository;
  private final DebitItemRepository debitItemRepository;
  private final BalEntityRepository balRepository;
  private final BalShareHisRepository balShareHisRepository;
  private final ReAbInstRepository reAbInstRepository;
  private final AbEventBenefitRepository abEventBenefitRepository;
  private final EventBenefitInstRepository eventBenefitInstRepository;
  private final ReOverdueInstRepository reOverdueInstRepository;
  private final OverdueItemRepository overdueItemRepository;
  private final PaymentRepository paymentRepository;
  private final AdjustNoteRepository adjustNoteRepository;
  private final AcctDepositBalRepository acctDepositBalRepository;
  private final AcctDepositBalMapper acctDepositBalMapper;
  private final ReserveDepositBalRepository reserveDepositBalRepository;
  private final NotePayPaymentRepository notePayPaymentRepository;
  private final DebitNoteRepository debitNoteRepository;
  private final AdjustRepository adjustRepository;
  private final BatchAcctBookRepository batchAcctBookRepository;
  private final AdjustItemRepository adjustItemRepository;
  private final DisputeRepository disputeRepository;
  private final DisputeItemRepository disputeItemRepository;
  private final DebitBalHisRepository debitBalHisRepository;
  private final SelectDebitBalMapper selectDebitBalMapper;
  private final AcctItemInstalmentRepository acctItemInstalmentRepository;
  private final DebitBalInstallRepository debitBalInstallRepository;
  private final BalAcctItemTypeRepository balAcctItemTypeRepository;

  // =========== SERVICE ==================
  private final DebitBalService debitBalService;
  private final RecordAcctRecentOperateService recordAcctRecentOperateService;
  private BalHelper balHelper;

  // =========== MAPPER ===================
  private final DebitBalInstallMapper debitBalInstallMapper;
  private final BalAcctItemTypeMapper balAcctItemTypeMapper;

  // =========== DATA ================
  private BillingBaseDataBus dataBus;
  private Long spId;
  private String refAttr;

  @PersistenceContext
  private EntityManager entityManager;

  public void invoke() {
    logger.info("dataBusStoreService invoked.");
    if (this.spId == null)
      this.spId = 0L;
    String acctBookType = this.dataBus.getAcctBookType();
    logger.debug("AcctBookType is [{}]", acctBookType);
    LocalDateTime dateNow = this.dataBus.getDateNow();
    // if (dateNow == null)
    // dateNow = DateUtil.GetDBDateTime();
    AcctBookData acctBookData = this.dataBus.getAcctBookData();
    if (this.dataBus instanceof FeeDataBus) {
      recordFeeRelation(acctBookData);
      recordBill(acctBookType);
    } else if (this.dataBus instanceof BalExchangeDataBus balExDataBus) {
      balExDataBus.setAcctBookDataOut(balExDataBus.getAcctBookData());
      recordBillBalExchange(balExDataBus);
      recordBill4Settle();
      recordBillSegment4Settle();
      recordPaymentPlan();
      recordAcctBookData(balExDataBus.getAcctBookDataOut());
      recordAcctBookData(balExDataBus.getAcctBookDataIn());
      recordSettRelation(dateNow);
      recordFeeRelation(balExDataBus.getAcctBookDataOut());
      recordReconcile(balExDataBus.getAcctBookDataOut(), balExDataBus.getAcctBookTypeOut());
      recordBalTransfer(balExDataBus.getBalTransferDto());
    } else {
      recordBill(acctBookType);
      recordBill4Settle();
      recordBillSegment4Settle();
      recordPaymentPlan();
      recordAcctBookData(acctBookData, acctBookType);
      switchAcctBookType(acctBookType);
      recordSettRelation(dateNow);
      recordFeeRelation(acctBookData);
      recordReconcile(acctBookData, acctBookType);
      recordLoanRepayment();
    }
    if (this.dataBus instanceof RechargeDataBus) {
      Optional<ConfigItemParamProjection> findIsNeenCashBosFunction = configItemRepository.findConfigItem("BILLING",
          "CASH_MGR", "PC_IS_NEED_CASH_BOX_FUNCTION");
      // String isNeenCashBoxFunction =
      // ConfigItemCache.instance().getString("BILLING.CASH_MGR.PC_IS_NEED_CASH_BOX_FUNCTION");
      String isNeenCashBoxFunction = findIsNeenCashBosFunction
          .map(ConfigItemParamProjection::getDefaultValue)
          .orElse(null);
      RechargeRequest rechargeRequest = (RechargeRequest) this.dataBus.getRequest();
      this.refAttr = rechargeRequest.getRefAttr();
      if ("Y".equals(isNeenCashBoxFunction) && "A".equals(acctBookData.getPartyType()))
        recordPcCashInfoForRecharge(acctBookData, rechargeRequest);
    } else if (this.dataBus instanceof ReverseDataBus) {
      Optional<ConfigItemParamProjection> findIsNeenCashBoxFunction = configItemRepository.findConfigItem("BILLING",
          "CASH_MGR", "PC_IS_NEED_CASH_BOX_FUNCTION");
      // String isNeenCashBoxFunction =
      // ConfigItemCache.instance().getString("BILLING.CASH_MGR.PC_IS_NEED_CASH_BOX_FUNCTION");
      String isNeenCashBoxFunction = findIsNeenCashBoxFunction
          .map(ConfigItemParamProjection::getDefaultValue)
          .orElse(null);
      if ("Y".equals(isNeenCashBoxFunction) && "A".equals(acctBookData.getPartyType()))
        recordPcCashInfoForReverse(acctBookData);
    }
    AcctDto acct = this.dataBus.getAcct();
    if (acct != null)
      recordBalance(acct, acct.getUpdateBalList(), acct.getRoutingId());
    List<AcctDto> otherAcctList = this.dataBus.getOtherAcctList();
    if (otherAcctList != null && !otherAcctList.isEmpty())
      for (AcctDto tmp : otherAcctList) {
        // Long routingId = RoutingHelper.getRoutingIdByAcctId(tmp.getAcctId());
        // logger.debug("rougingId=[{}], acctId=[{}]", routingId, tmp.getAcctId());
        recordBalance(tmp, tmp.getUpdateBalList(), null);
      }
    dealBalShareHis(acctBookData);
    dealBalHis(acctBookData);
    dealAcmList(acctBookData);
    logger.info("dataBusStoreService passed.");
    recordAcctRecentOperateService.setDataBus(dataBus);
    recordAcctRecentOperateService.setIsAdjustTrue(true);
    recordAcctRecentOperateService.invoke();
  }

  private void recordFeeRelation(AcctBookData acctBookData) {
    if (acctBookData == null)
      return;
    if (this.dataBus instanceof ReverseDataBus)
      return;
    if (this.dataBus instanceof BalTransferReverseDataBus)
      return;
    List<ReCcInstBLData> reCcInstBLDataList = acctBookData.getReCcInstDataList();
    if (reCcInstBLDataList.isEmpty()) {
      if (acctBookData.getAcctBookId() != null)
        recordReAbInst(acctBookData, reCcInstBLDataList);
      recordBenefitInst(reCcInstBLDataList);
      recordOverdueInst(reCcInstBLDataList);
      dealDepositList(reCcInstBLDataList);
      recordReActionAsynCall(reCcInstBLDataList);
    }
    EventPaymentData eventPaymentData = acctBookData.getEventPaymentData();
    if (eventPaymentData != null && eventPaymentData.getCharge() != null)
      addAcctBookBatch(eventPaymentData);
  }

  private void recordBill(String acctBookType) {
    BillDto bill = this.dataBus.getAcct().getBill();
    if (bill != null && this.dataBus.getAcct().getRecordBill())
      if ("H".equals(acctBookType) && this.dataBus.getAcctResDtoList() != null && (this.dataBus
          .getAcctResDtoList()).length > 0) {
        boolean update = false;
        for (int i = 0; i < (this.dataBus.getAcctResDtoList()).length; i++) {
          AcctResDto acctResDto = this.dataBus.getAcctResDtoList()[i];

          if (systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID")
              .equalsIgnoreCase(acctResDto.getAcctResId().toString())) {
            update = true;
            break;
          }
        }
        if (update)
          recordBill(bill);
      } else if (this.dataBus.getAcctResDto() != null &&
          systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID")
              .equalsIgnoreCase(this.dataBus.getAcctResDto().getAcctResId().toString())) {
        recordBill(bill);
      }
    List<AcctDto> otherAcctList = this.dataBus.getOtherAcctList();
    if (otherAcctList != null && !otherAcctList.isEmpty())
      for (AcctDto tmp : otherAcctList) {
        BillDto otherBill = tmp.getBill();
        if (otherBill != null && tmp.isRecordBill())
          recordBill(otherBill);
      }
  }

  private void recordBill(BillDto bill) {
    bill.setSpId(this.spId);
    Optional<ConfigItemParamProjection> findIsWriteBillEx = configItemRepository.findConfigItem("ACCT", "COMMON",
        "IS_WRITE_BILL_EX_WHILE_BAL_DEDUCT");
    // String isWriteBillEx =
    // ConfigItemCache.instance().getString("ACCT.COMMON.IS_WRITE_BILL_EX_WHILE_BAL_DEDUCT",
    // "N");
    String isWriteBillEx = findIsWriteBillEx
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse(null);
    if ("A".equals(bill.getOperationType())) {
      billRepository.billStore(bill.getBillId(), bill.getBillNbr(), bill.getAcctId(), bill.getBillingCycleId(),
          bill.getPreBalance(), bill.getDue(), bill.getDisputeCharge(), bill.getRecvCharge(),
          bill.getPastAdjustCharge(), bill.getAdjustCharge(), bill.getChargeBeAdjusted(), bill.getSpId(),
          bill.getBillCode(), bill.getChargeBeReversed());
      if (bill.getBillEx() != null && "Y".equals(isWriteBillEx)) {
        BillEx billEx = bill.getBillEx();
        billExRepository.insertBillEx(billEx.getBillId(), billEx.getSubsEventCharge(), billEx.getPayInTime());
      }
    } else if ("M".equals(bill.getOperationType())) {
      billRepository.billUpdate(bill.getDisputeCharge(), bill.getRecvCharge(), bill.getPastAdjustCharge(),
          bill.getAdjustCharge(), bill.getSettCharge(), bill.getChargeBeReversed(), bill.getChargeBeAdjusted(),
          bill.getBillId());
      if (bill.getBillEx() != null && "Y".equals(isWriteBillEx)) {
        BillEx billEx = bill.getBillEx();
        int result = billExRepository.updateBillEx(billEx.getSubsEventCharge(), billEx.getBillId());
        if (result == 0) {
          logger.debug("No record to be updated,insert a new one.");
          billExRepository.insertBillEx(billEx.getBillId(), billEx.getSubsEventCharge(), billEx.getPayInTime());
        }
      }
    }
    bill.setOperationType(null);
  }

  private void recordBillBalExchange(BalExchangeDataBus balExDataBus) {
    if ((systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID")
        .equalsIgnoreCase(balExDataBus.getAcctResDtoOut().getAcctResId().toString()) ||
        systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID")
            .equalsIgnoreCase(balExDataBus.getAcctResDtoIn().getAcctResId().toString()))
        && balExDataBus
            .getAcct().getRecordBill())
      recordBill(balExDataBus.getAcct().getBill());
  }

  private void recordBill4Settle() {
    BillDto[] billList4SettleList = this.dataBus.getBillList4SettleList();
    if (billList4SettleList != null)
      for (BillDto bill : billList4SettleList) {
        if (bill.getSettCharge() != 0L) {
          billRepository.bill4SettleUpdate(bill.getSettCharge(), bill.getAcctId(), bill.getBillingCycleId());
        }
      }
  }

  private void recordBillSegment4Settle() {
    logger.info("enter recordBillSegment4Settle:{}", this.dataBus.getExtendDataMap());
    if (this.dataBus.getExtendDataMap() == null)
      return;
    @SuppressWarnings("unchecked")
    List<BillSegmentDto> billSegmentList4Settle = (List<BillSegmentDto>) this.dataBus.getExtendDataMap()
        .get("BILL_SEGMENT_LIST");
    if (!billSegmentList4Settle.isEmpty())
      for (BillSegmentDto temp : billSegmentList4Settle) {
        if (temp.getSettCharge() != 0L) {
          nbBillSegmentRepository.billSegment4SettleUpdate(temp.getSettCharge(), temp.getAcctId(),
              temp.getBillingCycleId(), temp.getBatchId());
          paymentPlanRepository.updatePaymentPlan(temp.getSettCharge(), temp.getAcctId(), temp.getBillingCycleId(),
              temp.getBatchId());
        }
      }
    this.dataBus.getExtendDataMap().remove("BILL_SEGMENT_LIST");
  }

  private void recordPaymentPlan() {
    PaymentPlanDto[] paymentPlanDtoList = this.dataBus.getPaymentPlanDtoList();
    if (paymentPlanDtoList != null)
      for (PaymentPlanDto paymentPlanDto : paymentPlanDtoList) {
        if (paymentPlanDto.getBillId() != null) {
          paymentPlanRepository.updatePaymentPlanSettChargeByBillId(paymentPlanDto.getBillId(), paymentPlanDto
              .getSettCharge(), this.spId, paymentPlanDto.getSettTaxCharge());
        } else {
          paymentPlanRepository.updatePaymentPlanSettChargeById(paymentPlanDto.getPaymentPlanId(), paymentPlanDto
              .getSettCharge(), this.spId, paymentPlanDto.getSettTaxCharge());
          paymentPlanRepository.updateInstallmentItemSettChargeByPaymentPlanId(paymentPlanDto
              .getPaymentPlanId(), paymentPlanDto.getSettCharge(), this.spId);
        }
      }
    Map<String, Object> extendDataMap = this.dataBus.getExtendDataMap();
    if (extendDataMap != null && extendDataMap.containsKey("INSTALLMENT_SETT_CHARGE_MAP")) {
      @SuppressWarnings("unchecked")
      Map<String, Long> intallmentSettChargeMap = (Map<String, Long>) extendDataMap.get("INSTALLMENT_SETT_CHARGE_MAP");
      for (String installmentId : intallmentSettChargeMap.keySet())
        instalmentRepository.updateInstallmentPaiedAmountById(Long.valueOf(installmentId),
            -intallmentSettChargeMap.get(installmentId), this.spId);
    }
  }

  private boolean recordAcctBookData(AcctBookData acctBookData) {
    if (acctBookData == null) {
      logger.warn("AcctBookData is null,return");
      return false;
    }
    acctBookData.setSpId(this.spId);
    if (acctBookData.getEventPaymentData() != null)
      acctBookData.setEventPaymentId(acctBookData.getEventPaymentData().getEventPaymentId());
    if (acctBookData.getBillId() == null && this.dataBus.getAcct() != null && this.dataBus.getAcct().getBill() != null
        && this.dataBus
            .getAcct().getBill().getBillId() != null)
      acctBookData.setBillId(this.dataBus.getAcct().getBill().getBillId());
    acctBookRepository.acctBookDataStore(acctBookData.getAcctBookId(), acctBookData.getAcctId(),
        acctBookData.getAcctResId(), acctBookData.getAcctBookType(),
        acctBookData.getCreatedDate(),
        acctBookData.getPreBalance(),
        acctBookData.getPreExpDate(),
        acctBookData.getCharge(), acctBookData.getBillId(), "A", "1",
        acctBookData.getPreSuttleBal(), acctBookData.getSeconds(), acctBookData.getBalId(),
        acctBookData.getContactChannelId(), acctBookData.getEventPaymentId(), acctBookData.getSpId(),
        acctBookData.getPreEffDate(),
        acctBookData.getEffSeconds(), acctBookData.getRefAttr(), acctBookData.getOrgId());
    return true;
  }

  private void recordSettRelation(LocalDateTime dateNow) {
    SettlementParam settlementParam = this.dataBus.getSettlementParam();
    if (settlementParam != null) {
      PaymentSettDto paymentSettDto = settlementParam.getPaymentSett();
      if (paymentSettDto != null) {
        paymentSettDto.setSpId(this.spId);
        paymentSettDto.setCreatedDate(dateNow);
        paymentSettRepository.paymentSettStore(paymentSettDto.getPaymentSettId(), paymentSettDto.getAcctBookId(),
            paymentSettDto.getReversedPaymentSettId(), paymentSettDto.getCharge(),
            paymentSettDto.getCreatedDate(),
            paymentSettDto.getSpId(), paymentSettDto.getTolerance());
      }
      List<PaymentSettItemDto> paymentSettItemDtoList = settlementParam.getPaymentSettItemList();
      if (paymentSettItemDtoList != null && !paymentSettItemDtoList.isEmpty()) {
        for (PaymentSettItemDto paymentSettItem : paymentSettItemDtoList)
          paymentSettItem.setSpId(this.spId);
        for (PaymentSettItemDto item : paymentSettItemDtoList) {
          paymentSettItemRepository.paymentSettItemBatchStore(item.getPaymentSettId(), item.getAcctItemId(), spId,
              item.getSettleCharge(), item.getBillId());
        }
      }
      List<AcctItemDto> updateAcctItemDtoList = settlementParam.getUpdateAcctItemList();
      if (updateAcctItemDtoList != null && !updateAcctItemDtoList.isEmpty())
        for (AcctItemDto dto : updateAcctItemDtoList) {
          acctItemRepository.acctItemBatchUpdate(dto.getState(), dto.getSettleCharge(), dto.getAcctItemId());
        }
    }
  }

  private void recordBalTransfer(BalTransferDto balTransferDto) {
    // IBalTransferDAO balTransferDAO = (IBalTransferDAO)
    // DAOFactory.create("BalTransfer",
    // JdbcUtil4BL.getDbBackService());
    balTransferDto.setSpId(this.spId);
    balTransferRepository.insertBalTransfer(balTransferDto.getAcctBookId(), balTransferDto.getInAcctBookId(),
        balTransferDto.getSpId(), balTransferDto.getOutSubsId(), balTransferDto.getOutPrefix(),
        balTransferDto.getOutAccNbr(), balTransferDto.getInSubsId(), balTransferDto.getInPrefix(),
        balTransferDto.getInAccNbr(), balTransferDto.getRefAttr(), balTransferDto.getReversedAbId(),
        balTransferDto.getReversedByAbIid(), balTransferDto.getReversedInAbId(), balTransferDto.getReversedByInAbId(),
        balTransferDto.getComments(), balTransferDto.getTransferReasonId());
  }

  private void recordAcctBookData(AcctBookData acctBookData, String acctBookType) {
    AcctBookData[] acctBookList = this.dataBus.getAcctBookDataList();
    if ("H".equals(acctBookType) && acctBookList != null) {
      for (AcctBookData bookData : acctBookList)
        recordAcctBookData(bookData);
    } else {
      recordAcctBookData(acctBookData);
    }
  }

  private void switchAcctBookType(String acctBookType) {
    if ("P".equals(acctBookType)) {
      if (this.dataBus instanceof RechargeDataBus) {
        recordPayment();
      } else if (this.dataBus instanceof ReverseDataBus) {
        recordReverse();
      }
    } else if ("H".equals(acctBookType)) {
      recordBalAdjust();
      recordBatchAcctBook();
    } else if ("A".equals(acctBookType)) {
      recordPostInvoicingAdjust();
    } else if ("D".equals(acctBookType)) {
      recordDisputeOrProcessDispute();
    } else if ("G".equals(acctBookType)) {
      recordBalCancel();
    } else if ("J".equals(acctBookType)) {
      recordPayment();
      recordLoan();
    } else if ("W".equals(acctBookType)) {
      recordInstalment();
    } else if ("N".equals(acctBookType)) {
      if (this.dataBus instanceof ReverseDataBus)
        recordReverseFee();
    } else if ("L".equals(acctBookType)) {
      if (this.dataBus instanceof BalTransferReverseDataBus)
        recordBalTransferReverse();
    } else if ("K".equals(acctBookType) &&
        this.dataBus instanceof BalTransferReverseDataBus) {
      recordBalTransferReverse();
    }
  }

  private void recordLoanRepayment() {
    LoanRepayParam loanRepayParam = this.dataBus.getLoanRepayParam();
    if (loanRepayParam != null) {
      debitBalService.updateClearDebitBal(this.dataBus.getAcct().getAcctId(), loanRepayParam);
      Long commission = loanRepayParam.getCommRepayAmount();
      DebitItemDto debitItemDto = loanRepayParam.getDebitItemDto();
      Long oldDebitItemId = null;
      Long reversedByItemId = null;
      if (debitItemDto != null) {
        oldDebitItemId = debitItemDto.getReversedItemId();
        reversedByItemId = debitItemDto.getDebitItemId();
        debitItemDto.setSpId(this.spId);
        if (commission != null)
          debitItemDto.setCommission(commission * -1L);
        debitItemDto.pricePlanCode = loanRepayParam.getPricePlanCode();
        debitItemRepository.insertDebitItem(debitItemDto.getDebitItemId(), debitItemDto.getAcctId(),
            debitItemDto.getCharge(),
            debitItemDto.getCreatedDate(),
            debitItemDto.getRatio(), debitItemDto.getSpId(), debitItemDto.getReversedItemId(),
            debitItemDto.getReversedByItemId(), debitItemDto.getAcctBookId(), debitItemDto.getLoanType(),
            debitItemDto.getCommission(), debitItemDto.getVolume(), debitItemDto.getPricePlanCode());
      }
      DebitPaymentDto debitPaymentDto = loanRepayParam.getDebitPaymentDto();
      if (debitPaymentDto != null) {
        debitPaymentDto.setSpId(this.spId);
        debitPaymentRepository.insertDebitPayment(debitPaymentDto.getAcctBookId(), debitPaymentDto.getDebitItemId(),
            debitPaymentDto.getSpId(), debitPaymentDto.getRefAttr());
      }
      if (oldDebitItemId != null)
        debitItemRepository.updateDebitItemReverseRela(oldDebitItemId, reversedByItemId);
      if (oldDebitItemId != null) {
        reverseClearLoanInstall(loanRepayParam);
      } else {
        updateClearLoanInstall(loanRepayParam);
      }
    }
  }

  private void recordPcCashInfoForRecharge(AcctBookData acctBookData, RechargeRequest rechargeRequest) {
    List<PcCashManage> pcCashManageList = new ArrayList<>();
    PcCashManage pcCashManage = new PcCashManage();
    pcCashManageList.add(pcCashManage);
    if (acctBookData.getCharge() <= 0L) {
      pcCashManage.setCashOperType(9L);
    } else {
      pcCashManage.setCashOperType(10L);
    }
    pcCashManage.setAmount(Math.abs(acctBookData.getCharge()));
    pcCashManage.setBankId(rechargeRequest.getBankId());
    pcCashManage.setCheckNbr(rechargeRequest.getCheckNbr());
    pcCashManage.setEventPaymentId(acctBookData.getAcctBookId());
    pcCashManage.setPaymentMethodId(rechargeRequest.getPaymentMethodId());
    pcCashManage.setTradeTime(acctBookData.getCreatedDate());
    pcCashManage.setPartyCode(acctBookData.getPartyCode());
    pcCashManage.setPartyType(acctBookData.getPartyType());
    pcCashManage.setRemarks(CalcFeeHelper.getValFromKeyValueStr(this.refAttr, "REMARKS", null, null));
    if (this.dataBus.getSubs() != null)
      pcCashManage.setSubsId(this.dataBus.getSubs().getSubsId());
    // DynamicDict dict = new DynamicDict();
    // BoHelper.dtoToBO(pcCashManage, dict);
    // dict.setServiceName("RecordCashService");
    // ServiceFlow.callService(dict);
  }

  // public void cashManage (DynamicDict dict) {
  // logger.debug("CashManageServBll called, dict is {}.", dict);
  // Optional<ConfigItemParamProjection> findIsNeenCashBoxFunction =
  // configItemRepository.findConfigItem("BILLING", "CASH_MGR",
  // "PC_IS_NEED_CASH_BOX_FUNCTION");
  // String isNeenCashBoxFunction = findIsNeenCashBoxFunction
  // .map(ConfigItemParamProjection::getDefaultValue)
  // .orElse(null);
  // if (!"Y".equals(isNeenCashBoxFunction)) {
  // logger.debug("no need to invoke pcCashManageService!");
  // return;
  // }
  // List<PcCashManageRequest> list = BoHelper.boToListDto(dict,
  // "PC_CASH_MANAGE_LIST", PcCashManageRequest.class);
  // List<PcCashManageResponse> responses = new ArrayList<>();
  // // MAKE JDBC ZSMART
  // Session session = null;
  // try {
  // session = SessionContext.newSession();
  // session.beginTrans();
  // responses = cashManage(list, false, dict);
  // session.commitTrans();
  // } finally {
  // if (session != null)
  // session.releaseTrans();
  // }
  // List<DynamicDict> dictList = BoHelper.listDtoToListBO(responses);
  // dict.set("PC_CASH_MANAGE_RESPONSE", dictList);
  // }

  private void recordPcCashInfoForReverse(AcctBookData acctBookData) {
    List<PcCashManage> pcCashManageList = new ArrayList<>();
    PcCashManage pcCashManage = new PcCashManage();
    pcCashManageList.add(pcCashManage);
    pcCashManage.setCashOperType(13L);
    pcCashManage.setAmount(Math.abs(acctBookData.getCharge()));
    pcCashManage.setBankId(((ReverseDataBus) this.dataBus).getPaymentDto().getBankId());
    pcCashManage.setCheckNbr(((ReverseDataBus) this.dataBus).getPaymentDto().getCheckNbr());
    pcCashManage.setEventPaymentId(acctBookData.getAcctBookId());
    pcCashManage.setPaymentMethodId(((ReverseDataBus) this.dataBus).getPaymentDto().getPaymentMethodId());
    pcCashManage.setTradeTime(acctBookData.getCreatedDate());
    pcCashManage.setPartyCode(acctBookData.getPartyCode());
    pcCashManage.setPartyType(acctBookData.getPartyType());
    if (this.dataBus.getSubs() != null)
      pcCashManage.setSubsId(this.dataBus.getSubs().getSubsId());
    // DynamicDict dict = new DynamicDict();
    // BoHelper.dtoToBO(pcCashManage, dict);
    // dict.setServiceName("RecordCashService");
    // ServiceFlow.callService(dict);
  }

  private void recordBalance(AcctDto acct, BalDto[] updateBalList, Long routingId) {
    if (routingId == null)
      // routingId = Long.valueOf(RoutingHelper.getCurrentRoutingId().toString());
      if (updateBalList == null || updateBalList.length == 0) {
        logger.debug("no updateBal to store on databus store.");
        return;
      }
    for (int i = 0; i < updateBalList.length; i++) {
      ValidateUtil.isFalse((updateBalList[i] == null), "addBalList[" + i + "]");
      logger.debug(String.valueOf(updateBalList[i]));
    }
    addBalAcctItemType(updateBalList);
    Optional<ConfigItemParamProjection> findSetInitBalAsRealBal = configItemRepository.findConfigItem("ACCT",
        "ACCT_BILLING", "SET_INIT_BAL_AS_REAL_BAL");
    String setInitBalAsRealBal = findSetInitBalAsRealBal
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse(null);
    BalDto[] oldBalList = acct.getOldBalList();
    Map<Long, List<BalDto>> oldBalMap = new HashMap<>();
    List<BalDto> balList = null;
    BalDto oldBal = null;
    if ("Y".equals(setInitBalAsRealBal) && oldBalList != null)
      for (BalDto balDto : oldBalList) {
        oldBal = balDto;
        Long acctResId = oldBal.getAcctResId();
        balList = oldBalMap.get(acctResId);
        if (balList == null)
          balList = new ArrayList<>();
        balList.add(oldBal);
        oldBalMap.put(oldBal.getAcctResId(), balList);
      }
    List<BalDto> operatorList = BalHelper.mergeAddList(updateBalList);
    for (BalDto currentBal : operatorList) {
      Long acctResId = currentBal.getAcctResId();
      Long balId = currentBal.getBalId();
      if ("A".equals(currentBal.getOperationType())) {
        // getDataBusStoreDAOTimesten(routingId).insertBal(currentBal);
        Long subsId = (currentBal.getSubsId() != null) ? currentBal.getSubsId() : -1L;

        Long absExpOffset = -1L;
        if (currentBal.getAbsExpOffset() != null && currentBal.getAbsExpOffset() != -1L) {
          absExpOffset = currentBal.getAbsExpOffset();
        }

        LocalDateTime updateDate = currentBal.getUpdateDate();
        if (updateDate == null) {
          updateDate = LocalDateTime.now();
        }
        LocalDateTime createdDate = LocalDateTime.now();
        // getDataBusStoreDAOTimesten(routingId).insertBal(currentBal);
        balRepository.insertBal(currentBal.getAcctId(), currentBal.getAcctResId(), currentBal.getGrossBal(),
            currentBal.getConsumeBal(), currentBal.getRatingBal(), currentBal.getBillingBal(),
            currentBal.getEffDate(),
            currentBal.getExpDate(), updateDate, createdDate,
            currentBal.getCeilLimit(), currentBal.getFloorLimit(), currentBal.getDailyCeilLimit(),
            currentBal.getDailyFloorLimit(), currentBal.getPriority(), currentBal.getLastBal(),
            currentBal.getLastRecharge(), currentBal.getBalUsed(), currentBal.getInitBal(), subsId,
            currentBal.getBalId(), absExpOffset, currentBal.getBalFlags(), currentBal.getCustId());
        logger.info("insert bal");
      }
      if ("M".equals(currentBal.getOperationType())) {
        if ("Y".equals(setInitBalAsRealBal)) {
          oldBal = BalHelper.getBalFromListByBalId(oldBalList, balId);
          assert oldBal != null;
          long realBal = oldBal.getRealBal() + currentBal.getCharge();
          if (currentBal.getConsumeBalCharge() != null)
            realBal = realBal + currentBal.getConsumeBalCharge();
          currentBal.setInitBal(realBal);
        }
        // default value seperti kode lama
        if (currentBal.getSeconds() == null)
          currentBal.setSeconds(0L);

        if (currentBal.getEffSeconds() == null)
          currentBal.setEffSeconds(0L);
        // getDataBusStoreDAOTimesten(routingId).updateBal(currentBal);
        balRepository.updateBal(currentBal.getCharge(), currentBal.getInitBal(), currentBal.getEffSeconds(),
            currentBal.getUpdateDate(),
            currentBal.getExpDate(),
            currentBal.getSeconds(), currentBal.getAbsExpOffset(), currentBal.getCeilLimitCharge(),
            currentBal.getConsumeBalCharge(), currentBal.getBalId());
      }
      if ("Y".equals(setInitBalAsRealBal) && oldBalMap.containsKey(acctResId)) {
        balList = oldBalMap.get(acctResId);
        for (BalDto balDto : balList) {
          oldBal = balDto;
          oldBal.setInitBal(oldBal.getRealBal());
          if (oldBal.getBalId().longValue() != balId.longValue()) {
            oldBal.setCharge(0L);
            // getDataBusStoreDAOTimesten(routingId).updateBal(oldBal);
            balRepository.updateBal(currentBal.getCharge(), currentBal.getInitBal(), currentBal.getEffSeconds(),
                currentBal.getUpdateDate(),
                currentBal.getExpDate(),
                currentBal.getSeconds(), currentBal.getAbsExpOffset(), currentBal.getCeilLimitCharge(),
                currentBal.getConsumeBalCharge(), currentBal.getBalId());
          }
        }
      }
    }
  }

  private void dealBalShareHis(AcctBookData acctBookData) {
    if (acctBookData == null)
      return;
    if (this.dataBus instanceof ReverseDataBus)
      return;
    if (this.dataBus instanceof BalTransferReverseDataBus)
      return;
    List<ReCcInstBLData> reCcInstBLDataList = acctBookData.getReCcInstDataList();
    if (CommonUtil.isEmpty(reCcInstBLDataList))
      return;
    for (ReCcInstBLData reCcInstBLData : reCcInstBLDataList) {
      OnceFeeInstDataDto[] onceFeeInstDataList = reCcInstBLData.getOnceFeeInstDataList();
      if (CommonUtil.isEmpty((Object[]) onceFeeInstDataList))
        continue;
      for (OnceFeeInstDataDto onceFeeInstData : onceFeeInstDataList) {
        BalanceShareHisData[] balanceShareHisDataList = onceFeeInstData.getBalanceShareHisDataList();
        if (!CommonUtil.isEmpty((Object[]) balanceShareHisDataList)) {
          // Long routingId = null;
          if (BillingHelper.isQryBalShareHisByAcctRoutingId()) {
            // routingId = RoutingHelper.getRoutingIdByAcctId(onceFeeInstData.getAcctId());
          } else {
            // routingId = RoutingHelper.getRoutingIdBySubsId(onceFeeInstData.getSubsId());
          }
          // IDataBusStoreDAO dataBusStoreDAOTimesten =
          // getDataBusStoreDAOTimesten(routingId);
          for (BalanceShareHisData balanceShareHisData : balanceShareHisDataList) {
            if (balanceShareHisData.getBalShareId() != -1L) {
              int ret = balShareHisRepository.updateBalShareHis(balanceShareHisData.getBalShareId(), balanceShareHisData
                  .getBillBal(), balanceShareHisData.getDailyBal(),
                  balanceShareHisData
                      .getDateStamp());
              if (ret == 0)
                balShareHisRepository.insertBalShareHis(balanceShareHisData.getBalShareId(), balanceShareHisData
                    .getBillBal(), balanceShareHisData.getDailyBal(),
                    balanceShareHisData
                        .getDateStamp());
            }
          }
        }
      }
    }
  }

  private void dealBalHis(AcctBookData acctBookData) {
    logger.debug("dealBalHis start...");
    if (acctBookData == null)
      return;
    if (this.dataBus instanceof ReverseDataBus)
      return;
    if (this.dataBus instanceof BalTransferReverseDataBus)
      return;
    EventPaymentData eventPaymentData = acctBookData.getEventPaymentData();
    if (eventPaymentData == null)
      return;
    BalDeductData[] balDeductDataList = eventPaymentData.getBalDeductDataList();
    BalDeductData[] otherBalDeductDataList = eventPaymentData.getOtherBalDeductDataList();
    balHisStore(balDeductDataList);
    balHisStore(otherBalDeductDataList);
    logger.debug("dealBalHis end...");
  }

  private void balHisStore(BalDeductData[] balDeductDataList) {
    logger.debug("BalHisStore start...");
    if (CommonUtil.isEmpty((Object[]) balDeductDataList)) {
      logger.debug("balDeductDataList is null");
      return;
    }
    for (BalDeductData balDeduct : balDeductDataList) {
      BalanceHisData balanceHisData = balDeduct.getBalHisInfo();
      logger.debug("balanceHisData is {}", balanceHisData);
      if (balanceHisData == null)
        return;
      // Long routingId = null;
      // routingId = RoutingHelper.getRoutingIdByAcctId(balDeduct.getAcctId());
      // IDataBusStoreDAO dataBusStoreDAOTimesten =
      // getDataBusStoreDAOTimesten(routingId);
      if (balanceHisData.getBalId() != null) {
        int ret = balShareHisRepository.updateBalHis(balanceHisData.getDailyBal(), balanceHisData.getBillBal(),
            balanceHisData.getBalId(), balanceHisData.getDateStamp());
        if (ret == 0)
          balShareHisRepository.insertBalHis(balanceHisData.getBalId(), balanceHisData.getDateStamp(),
              balanceHisData.getDailyBal(), balanceHisData.getBillBal());
      }
    }
  }

  public void dealAcmList(AcctBookData acctBookData) {
    if (this.dataBus instanceof ReverseDataBus)
      return;
    if (this.dataBus instanceof BalTransferReverseDataBus)
      return;
    if (acctBookData == null || acctBookData.getReCcInstDataList() == null || acctBookData
        .getReCcInstDataList().size() <= 0)
      return;
    List<ReCcInstBLData> reCcInstBLDataList = acctBookData.getReCcInstDataList();
    for (ReCcInstBLData reCcInstBLData : reCcInstBLDataList) {
      Long routingId = null;
      AcmInstData[] acmInstDataList = reCcInstBLData.getAcmInstDataList();
      if (acmInstDataList != null)
        for (AcmInstData acmInstData : acmInstDataList) {
          if (acmInstData != null) {
            if (acmInstData.getRoutingId() == null) {
              // routingId = Long.valueOf(RoutingHelper.getCurrentRoutingId().toString());
            } else {
              routingId = acmInstData.getRoutingId();
            }
            int count = AcmHelper.updateAcm(acmInstData, routingId);
            if (count == 0)
              AcmHelper.insertAcm(acmInstData, routingId);
          }
        }
    }
  }

  private void addAcctBookBatch(EventPaymentData eventPaymentData) {
    BalDeductData[] balDeductDataList = eventPaymentData.getBalDeductDataList();
    if (CommonUtil.isNotEmpty((Object[]) balDeductDataList)) {
      List<AcctBookDto> acctBookDtoList = new ArrayList<>();
      for (BalDeductData balDeductData : balDeductDataList) {
        if (balDeductData != null && balDeductData.getDeductAcctBookDto() != null) {
          balDeductData.getDeductAcctBookDto().setSpId(this.spId);
          acctBookDtoList.add(balDeductData.getDeductAcctBookDto());
        }
      }
      if (!acctBookDtoList.isEmpty())
        for (AcctBookDto dto : acctBookDtoList) {
          acctBookRepository.acctBookDtoBatchStore(dto.getAcctBookId(), dto.getAcctId(), dto.getAcctResId(),
              dto.getAcctBookType(), dto.getCreatedDate(),
              dto.getPreBalance(), dto.getPreExpDate(),
              dto.getCharge(), dto.getBillId(), dto.getPartyType(), dto.getPartyCode(), dto.getPreSuttleBal(),
              dto.getSeconds(), dto.getBalId(), dto.getContactChannelId(), dto.getEventPaymentId(), dto.getSpId(),
              dto.getPreEffDate(), dto.getEffSeconds(),
              dto.getRefAttr(), dto.getEventInstId());
        }
    }
    BalDeductData[] otherBalDeductDataList = eventPaymentData.getOtherBalDeductDataList();
    if (CommonUtil.isNotEmpty((Object[]) otherBalDeductDataList))
      for (BalDeductData balDeductData : otherBalDeductDataList) {
        if (balDeductData.getDeductAcctBookDto() != null) {
          // Long routingId =
          // RoutingHelper.getRoutingIdByAcctId(balDeductData.getAcctId());
          // logger.debug("routingId = [{}], acctId = [{}]", routingId,
          // balDeductData.getAcctId());
          AcctBookDto dto = balDeductData.getDeductAcctBookDto();
          if (dto == null)
            continue;
          acctBookRepository.acctBookDtoBatchStore(dto.getAcctBookId(), dto.getAcctId(), dto.getAcctResId(),
              dto.getAcctBookType(), dto.getCreatedDate(),
              dto.getPreBalance(), dto.getPreExpDate(),
              dto.getCharge(), dto.getBillId(), dto.getPartyType(), dto.getPartyCode(), dto.getPreSuttleBal(),
              dto.getSeconds(), dto.getBalId(), dto.getContactChannelId(), dto.getEventPaymentId(), dto.getSpId(),
              dto.getPreEffDate(), dto.getEffSeconds(),
              dto.getRefAttr(), dto.getEventInstId());
        }
      }
  }

  private void recordReAbInst(AcctBookData acctBookData, List<ReCcInstBLData> reCcInstBLDataList) {
    List<ReAbInstDto> reAbInstDtoList = new ArrayList<>();
    for (ReCcInstBLData reCcInstBLData : reCcInstBLDataList) {
      if (reCcInstBLData.getEventInstId() != null) {
        ReAbInstDto reAbInstDto = new ReAbInstDto();
        reAbInstDto.setAcctBookId(acctBookData.getAcctBookId());
        reAbInstDto.setEventInstId(reCcInstBLData.getEventInstId());
        reAbInstDto.setSpId(this.spId);
        reAbInstDtoList.add(reAbInstDto);
      }
    }
    if (CommonUtil.isNotEmpty(reAbInstDtoList))
      for (ReAbInstDto dto : reAbInstDtoList) {
        reAbInstRepository.reAbInstBatchStore(dto.getEventInstId(), dto.getAcctBookId(), dto.getSpId());
      }
  }

  private void recordBenefitInst(List<ReCcInstBLData> reCcInstBLDataList) {
    for (ReCcInstBLData reCcInstBLData : reCcInstBLDataList) {
      AcctBookDto[] accBookDtoList = reCcInstBLData.getAcctBookDtoList();
      if (CommonUtil.isNotEmpty((Object[]) accBookDtoList)) {
        for (AcctBookDto dto : accBookDtoList) {
          dto.setSpId(this.spId);
          acctBookRepository.acctBookDtoBatchStore(dto.getAcctBookId(), dto.getAcctId(), dto.getAcctResId(),
              dto.getAcctBookType(), dto.getCreatedDate(),
              dto.getPreBalance(), dto.getPreExpDate(),
              dto.getCharge(), dto.getBillId(), dto.getPartyType(), dto.getPartyCode(), dto.getPreSuttleBal(),
              dto.getSeconds(), dto.getBalId(), dto.getContactChannelId(), dto.getEventPaymentId(), dto.getSpId(),
              dto.getPreEffDate(), dto.getEffSeconds(),
              dto.getRefAttr(), dto.getEventInstId());
        }
      }
      AbEventBenefitDto[] abEventBenefitDtoList = reCcInstBLData.getAbEventBenefitDtoList();
      if (CommonUtil.isNotEmpty((Object[]) abEventBenefitDtoList)) {
        int batchSize = 300;
        int i = 0;
        // abEventBenefitRepository.abEventBenefitBatchStore(abEventBenefitDtoList);
        for (AbEventBenefitDto dto : abEventBenefitDtoList) {
          dto.setSpId(this.spId);
          abEventBenefitRepository.abEventBenefitBatchStore(dto.getEventInstId(), dto.getAcctBookId(), dto.getPriceId(),
              dto.getSubBalTypeId(), dto.getEffDate(),
              dto.getExpDate(), dto.getBenefitBal(),
              dto.getSpId());
          if (i % batchSize == 0) {
            entityManager.flush();
            entityManager.clear();
          }
          i++;
        }
      }
      PresentFeeInstData[] presentFeeInstInfoList = reCcInstBLData.getPresentFeeInstDataList();
      if (CommonUtil.isNotEmpty((Object[]) presentFeeInstInfoList)) {
        int batchSize = 300;
        int i = 0;
        for (PresentFeeInstData data : presentFeeInstInfoList) {
          eventBenefitInstRepository.eventBenefitInstBalIdBatchFillup(data.getBalanceInfo().getBalId(),
              data.getAcctResId(), data.getEventInstId(), data.getPriceId(), data.getSeq());
          if (i % batchSize == 0) {
            entityManager.flush();
            entityManager.clear();
          }
          i++;
        }
      }
    }
  }

  private void recordOverdueInst(List<ReCcInstBLData> reCcInstBLDataList) {
    List<OverdueItemDto> overdueItemDtoList = new ArrayList<>();
    List<ReOverDueInstDto> reOverdueInstDtoList = new ArrayList<>();
    for (ReCcInstBLData reCcInstBLData : reCcInstBLDataList) {
      OverdueItemDto overdueItemDto = reCcInstBLData.getOverdueItemDto();
      if (overdueItemDto != null) {
        overdueItemDto.setSpId(this.spId);
        overdueItemDtoList.add(overdueItemDto);
      }
      ReOverDueInstDto reOverdueInstDto = reCcInstBLData.getReOverdueInstDto();
      if (reOverdueInstDto != null) {
        reOverdueInstDto.setSpId(this.spId);
        reOverdueInstDtoList.add(reOverdueInstDto);
      }
    }
    if (CommonUtil.isNotEmpty(reOverdueInstDtoList)) {
      // getDataBusStoreDAO().reOverdueInstBatchStore(reOverdueInstDtoList.<ReOverdueInstDto>toArray(new
      // ReOverdueInstDto[0]));
      if (reOverdueInstDtoList.isEmpty()) {
        return;
      }
      int batchSize = 300;
      int i = 0;
      for (ReOverDueInstDto dto : reOverdueInstDtoList) {
        reOverdueInstRepository.reOverdueInstBatchStore(dto.getEventInstId(), dto.getPaymentSettId(),
            dto.getOverduePlanId(), dto.getCapitalAmount(), dto.getOverdueDay(), dto.getSpId());
        if (i % batchSize == 0) {
          entityManager.flush();
          entityManager.clear();
        }
        i++;
      }
    }
    if (CommonUtil.isNotEmpty(overdueItemDtoList)) {
      // overdueItemRepository.overdueItemBatchStore(overdueItemDtoList.<OverdueItemDto>toArray(new
      // OverdueItemDto[0]));
      if (overdueItemDtoList.isEmpty()) {
        return;
      }
      int batchSize = 300;
      int i = 0;
      for (OverdueItemDto dto : overdueItemDtoList) {
        overdueItemRepository.overdueItemBatchStore(dto.getEventInstId(), Math.toIntExact(dto.getSeq()),
            dto.getBillingCycleId(), dto.getAcctItemTypeId(), dto.getCharge(), dto.getOverdueCharge(),
            dto.getOverdueAdjustId(), dto.getAdjustCharge(), dto.getSpId());
        if (i % batchSize == 0) {
          entityManager.flush();
          entityManager.clear();
        }
        i++;
      }
    }
  }

  private void recordPayment() {
    RechargeDataBus rechargeDataBus = (RechargeDataBus) this.dataBus;
    PaymentDto paymentDto = rechargeDataBus.getPaymentDto();
    paymentDto.setSpId(this.spId);
    dealWithAcmCycle(paymentDto);
    paymentRepository.paymentStore(paymentDto.getPaymentId(), paymentDto.getReversedPaymentId(),
        paymentDto.getPaymentMethodId(), paymentDto.getVoucher(), paymentDto.getSubmitAmount(),
        paymentDto.getReturnAmount(), paymentDto.getRefAttr(), paymentDto.getBankId(), paymentDto.getCheckNbr(),
        paymentDto.getCheckOwnerName(),
        paymentDto.getCheckIssueDate(),
        paymentDto.getCheckExpDate(),
        paymentDto.getScratchCardPwd(), paymentDto.getPrefix(), paymentDto.getAccNbr(),
        paymentDto.getPaymentDate(), paymentDto.getSpId(),
        paymentDto.getOriAcctResId(), paymentDto.getDestAcctResId(), paymentDto.getDestAmount(),
        paymentDto.getRefundReasonId(), paymentDto.getOverpayAmount(), paymentDto.getReceiptNum());
    if (paymentDto.getPaymentMethodId() != null && paymentDto.getPaymentMethodId().longValue() == 11L &&
        !paymentDto.getVoucher().isEmpty()) {
      int resultCount = adjustNoteRepository.qryAdjustNoteByVoucherAndState(paymentDto.getVoucher(), "A");
      if (resultCount > 0) {
        adjustNoteRepository.updateAdjustNoteByVoucherAndState(paymentDto.getVoucher(), "U", "A");
      } else {
        // MessageService.getMessage("S-ACT-00377",
        // StringUtil.stringFormat("voucher=[{0}]", paymentDto.getVoucher()), 0);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("S-ACT-00377"));
      }
    }
    RechargeRequest req = (RechargeRequest) rechargeDataBus.getRequest();
    recordNotePayPayment(req.getNotePayPaymentList(), paymentDto.getPaymentId(), paymentDto.getSpId());
  }

  public void dealDepositList(List<ReCcInstBLData> reCcInstBLDataList) {
    logger.debug("dealDepositList called.");
    List<ReserveDepositBalDto> reserveDepositBalDtoList = new ArrayList<>();
    List<AcctDepositBalDto> acctDepositBalDtoList = new ArrayList<>();
    for (ReCcInstBLData reCcInstBLData : reCcInstBLDataList) {
      ReserveDepositBalDto reserveDepositBalDto = null;
      DepositInstData[] depositInstDataList = reCcInstBLData.getDepositInstDataList();
      if (depositInstDataList != null)
        for (DepositInstData depositInstData : depositInstDataList) {
          if (depositInstData != null && depositInstData.getReserveCharge() != null && depositInstData
              .getReserveCharge() > 0L) {
            Long reserveCharge = depositInstData.getReserveCharge();
            Long realAcctId = getRealAcct(depositInstData);
            List<AcctDepositBalDto> currentAcctDepositBalDtoList = acctDepositBalRepository
                .selectAcctDepositBalByAcctId(realAcctId).stream()
                .map(acctDepositBalMapper::toSelectAcctDepositBalByAcctIdResponseDto).toList();
            if (!currentAcctDepositBalDtoList.isEmpty())
              for (AcctDepositBalDto currentAcctDepositBalDto : currentAcctDepositBalDtoList) {
                if (reserveCharge <= 0L) {
                  logger.debug("reserveCharge is zero, break");
                  break;
                }
                reserveDepositBalDto = new ReserveDepositBalDto();
                Long remainBal = currentAcctDepositBalDto.getBal() + currentAcctDepositBalDto
                    .getReserveBal();
                if (remainBal + reserveCharge <= 0L) {
                  reserveDepositBalDto.setReserveBal(reserveCharge);
                  reserveCharge = 0L;
                } else {
                  reserveCharge = remainBal + reserveCharge;
                  reserveDepositBalDto.setReserveBal(remainBal * -1L);
                }
                reserveDepositBalDto.setDepositItemId(currentAcctDepositBalDto.getDepositItemId());
                reserveDepositBalDto.setEventInstId(reCcInstBLData.getEventInstId());
                reserveDepositBalDto.setState("N");
                reserveDepositBalDtoList.add(reserveDepositBalDto);
                currentAcctDepositBalDto.setReserveBal(reserveDepositBalDto.getReserveBal());
                acctDepositBalDtoList.add(currentAcctDepositBalDto);
              }
          }
        }
    }
    if (CommonUtil.isNotEmpty(reserveDepositBalDtoList))
      for (ReserveDepositBalDto dto : reserveDepositBalDtoList) {
        reserveDepositBalRepository.insertReserveDepositBal(dto.getEventInstId(), dto.getDepositItemId(),
            dto.getReserveBal(), dto.getOrderItemId(), dto.getState(), dto.getCOrderItemId());
      }
    if (CommonUtil.isNotEmpty(acctDepositBalDtoList))
      // getDataBusStoreDAO().updateAcctDepositBal(acctDepositBalDtoList.<AcctDepositBalDto>toArray(new
      // AcctDepositBalDto[0]));
      for (AcctDepositBalDto dto : acctDepositBalDtoList) {
        acctDepositBalRepository.updateAcctDepositBal(dto.getReserveBal(), dto.getDepositItemId());
      }
    logger.debug("dealDepositList end.");
  }

  private void recordReActionAsynCall(List<ReCcInstBLData> reCcInstBLDataList) {
    // for (ReCcInstBLData reCcInstBLData : reCcInstBLDataList) {
    // if (CommonUtil.isNotEmpty(reCcInstBLData.getAsynCallList())) {
    // IAsynCallDAO asynCallDAO = (IAsynCallDAO) DAOFactory.create("AsynCall",
    // JdbcUtil4BL.getDbCache());
    // for (AsynCallDto asynCallDto : reCcInstBLData.getAsynCallList())
    // asynCallDAO.insertAsynCall(asynCallDto);
    // }
    // }
  }

  private void recordReverse() {
    ReverseDataBus reverseDataBus = (ReverseDataBus) this.dataBus;
    PaymentDto paymentDto = reverseDataBus.getPaymentDto();
    if (paymentDto == null) {
      logger.debug("Fail to get paymentDto on reverse");
      return;
    }
    paymentDto.setSpId(this.spId);
    // IDataBusStoreDAO dao = getDataBusStoreDAO();
    paymentRepository.paymentStore(paymentDto.getPaymentId(), paymentDto.getReversedPaymentId(),
        paymentDto.getPaymentMethodId(), paymentDto.getVoucher(), paymentDto.getSubmitAmount(),
        paymentDto.getReturnAmount(), paymentDto.getRefAttr(), paymentDto.getBankId(), paymentDto.getCheckNbr(),
        paymentDto.getCheckOwnerName(),
        paymentDto.getCheckIssueDate(),
        paymentDto.getCheckExpDate(),
        paymentDto.getScratchCardPwd(), paymentDto.getPrefix(), paymentDto.getAccNbr(),
        paymentDto.getPaymentDate(), paymentDto.getSpId(),
        paymentDto.getOriAcctResId(), paymentDto.getDestAcctResId(), paymentDto.getDestAmount(),
        paymentDto.getRefundReasonId(), paymentDto.getOverpayAmount(), paymentDto.getReceiptNum());
    recordReverseFee();
    if (reverseDataBus.getReversePaymentSettDto() != null) {
      reverseDataBus.getReversePaymentSettDto().setSpId(this.spId);
      paymentSettRepository.insertPaymentSett(reverseDataBus.getReversePaymentSettDto().getPaymentSettId(),
          reverseDataBus.getReversePaymentSettDto().getAcctBookId(),
          reverseDataBus.getReversePaymentSettDto().getReversedPaymentSettId(),
          reverseDataBus.getReversePaymentSettDto().getCharge(),
          reverseDataBus.getReversePaymentSettDto().getCreatedDate(),
          reverseDataBus.getReversePaymentSettDto().getSpId(),
          reverseDataBus.getReversePaymentSettDto().getTolerance());
      if (reverseDataBus.getReversePaymentSettItemDtoList() != null)
        // dao.paymentSettItemBatchStore(reverseDataBus.getReversePaymentSettItemDtoList());
        for (PaymentSettItemDto dto : reverseDataBus.getReversePaymentSettItemDtoList()) {
          paymentSettItemRepository.paymentSettItemBatchStore(dto.getPaymentSettId(), dto.getAcctItemId(),
              dto.getSpId(), dto.getSettleCharge(), dto.getBillId());
        }
      acctItemRepository.reverseAcctItemStateAndSettleCharge("C", reverseDataBus
          .getReversePaymentSettDto().getReversedPaymentSettId());
    }
    Long oldPaymentId = reverseDataBus.getOldAcctBook().getAcctBookId();
    Long newPaymentId = reverseDataBus.getAcctBookData().getAcctBookId();
    paymentRepository.updatePaymentReverseRela(oldPaymentId, newPaymentId);
    if (paymentDto.getPaymentMethodId() != null && paymentDto.getPaymentMethodId() == 11L &&
        !paymentDto.getVoucher().isEmpty())
      adjustNoteRepository.updateAdjustNoteByVoucherAndState(paymentDto.getVoucher(), "A", null);
    Long[][] oldNotePayInfo = selectNotePayPayment(oldPaymentId);
    if (oldNotePayInfo != null) {
      notePayPaymentRepository.insertNotePayPayment(newPaymentId, this.spId, oldNotePayInfo[0], oldNotePayInfo[1]);
      debitNoteRepository.updateDebitNote(oldNotePayInfo[0], oldNotePayInfo[1]);
    }
  }

  public Long[][] selectNotePayPayment(Long oldPaymentId) {

    List<SelectNotePayPaymentProjection> rows = notePayPaymentRepository.selectNotePayPayment(oldPaymentId);

    if (rows.isEmpty()) {
      return null;
    }

    Long[] noteIds = rows.stream()
        .map(SelectNotePayPaymentProjection::getNoteId)
        .toArray(Long[]::new);

    Long[] charges = rows.stream()
        .map(SelectNotePayPaymentProjection::getCharge)
        .toArray(Long[]::new);

    return new Long[][] { noteIds, charges };
  }

  private void recordBalAdjust() {
    BalAdjustDataBus balAdjustDataBus = (BalAdjustDataBus) this.dataBus;
    AdjustDto[] adjustDtoList = balAdjustDataBus.getAdjustDtoList();
    if (adjustDtoList != null && adjustDtoList.length > 0) {
      for (AdjustDto adjustDto : adjustDtoList)
        recordBalAdjust(adjustDto);
    } else {
      recordBalAdjust(balAdjustDataBus.getAdjustDto());
    }
  }

  private void recordBalAdjust(AdjustDto adjustDto) {
    if (adjustDto != null) {
      adjustDto.setSpId(this.spId);
      adjustRepository.balAdjustStore(adjustDto.getAdjustId(), adjustDto.getAdjustReasonId(), adjustDto.getComments(),
          adjustDto.getSpId(), adjustDto.getReversedAdjustId(), adjustDto.getSubsId());
    }
  }

  private void recordBatchAcctBook() {
    BalAdjustDataBus balAdjustDataBus = (BalAdjustDataBus) this.dataBus;
    BatchAcctBookDto[] batchAcctBookDtoList = balAdjustDataBus.getBatchAcctBookDtoList();
    if (batchAcctBookDtoList != null && batchAcctBookDtoList.length > 0) {
      for (BatchAcctBookDto batchAcctBookDto : batchAcctBookDtoList) {
        if (batchAcctBookDto != null) {
          batchAcctBookDto.setSpId(this.spId);
        }
      }
      batchAcctBookStore(batchAcctBookDtoList);
    }
  }

  @Transactional
  public void batchAcctBookStore(BatchAcctBookDto[] dtoList) {
    for (BatchAcctBookDto dto : dtoList) {
      batchAcctBookRepository.batchAcctBookStore(
          dto.getAbBatchId(),
          dto.getAcctBookId(),
          dto.getSpId());
    }
  }

  private void recordPostInvoicingAdjust() {
    PostInvoicingAdjustDataBus postAdjustDataBus = (PostInvoicingAdjustDataBus) this.dataBus;
    AcctItemDto[] acctItemDtoList = postAdjustDataBus.getNewAcctItemList();
    for (AcctItemDto dto : acctItemDtoList) {
      dto.setSpId(spId);
    }
    for (AcctItemDto dto : acctItemDtoList) {
      acctItemRepository.acctItemBatchStore(dto.getAcctItemId(), dto.getBillingCycleId(), dto.getAcctId(),
          dto.getSubsId(), dto.getAcctItemTypeId(), dto.getCharge(), dto.getBasicCharge(),
          dto.getCreatedDate(), dto.getState(),
          dto.getStateDate(), dto.getBalId(),
          dto.getSettleCharge(), dto.getSpId(), dto.getAcctBookId());
    }
    AdjustDto adjustDto = postAdjustDataBus.getAdjustDto();
    if (adjustDto != null) {
      adjustDto.setSpId(this.spId);
      adjustRepository.balAdjustStore(adjustDto.getAdjustId(), adjustDto.getAdjustReasonId(), adjustDto.getComments(),
          adjustDto.getSpId(), adjustDto.getReversedAdjustId(), adjustDto.getSubsId());
    }
    AdjustItemDto[] adjustItemDtoArr = postAdjustDataBus.getAdjustItemDtoArr();
    if (adjustItemDtoArr != null) {
      for (AdjustItemDto adjustItem : adjustItemDtoArr)
        adjustItem.setSpId(this.spId);
      // int[] results = new int[adjustItemDtoArr.length];

      for (AdjustItemDto dto : adjustItemDtoArr) {
        adjustItemRepository.insertAdjustItem(dto.getAcctItemId(), dto.getAdjustId(), dto.getRefAcctItemId(),
            dto.getSpId(), dto.getRefBillId(), dto.getComments());
      }
    }
    PostInvoicingAdjustRequest request = (PostInvoicingAdjustRequest) postAdjustDataBus.getRequest();
    Long reverseAdjustId = request.getReverseAdjustId();
    Long newAdjustId = postAdjustDataBus.getAcctBookData().getAcctBookId();
    if (reverseAdjustId != null)
      adjustRepository.updateReverseAdjust(newAdjustId, reverseAdjustId);
  }

  private void recordDisputeOrProcessDispute() {
    DisputeDataBus disputeDataBus = (DisputeDataBus) this.dataBus;
    DisputeRequest disputeRequest = (DisputeRequest) disputeDataBus.getRequest();
    DisputeDto disputeDto = disputeDataBus.getDisputeDto();
    if (disputeDto != null) {
      disputeDto.setSpId(this.spId);
      disputeRepository.insertDispute(disputeDto.getDisputeId(), disputeDto.getProcessedDisputeId(),
          disputeDto.getComments(), disputeDto.getSpId());
    }
    Long processType = disputeRequest.getProcessType();
    if (processType == null) {
      Long disputeId = disputeDto.getDisputeId();
      Long[] acctItemIdList = disputeRequest.getAcctItemIdList();
      for (Long acctItemId : acctItemIdList) {
        disputeItemRepository.insertDisputeItem(acctItemId, disputeId, spId);
      }
      acctItemRepository.updateDisputeAcctItemState(disputeDto.getDisputeId(), "S");
    } else if (processType == 2L) {
      acctItemRepository.updateDisputeAcctItemState(disputeRequest.getDisputeId(), "C");
    } else if (processType == 1L) {
      acctItemRepository.updateDisputeAcctItemState(disputeRequest.getDisputeId(), "X");
    }
  }

  private void recordBalCancel() {
    BalCancelDataBus balCancelDataBus = (BalCancelDataBus) this.dataBus;
    AcctBookData[] balCancelAcctBookDataList = balCancelDataBus.getBalCancelAcctBookDataList();
    if (balCancelAcctBookDataList != null) {
      for (AcctBookData acctBook : balCancelAcctBookDataList) {
        acctBook.setSpId(this.spId);
        acctBookRepository.insertAcctBookDataBatch(acctBook.getAcctBookId(), acctBook.getAcctId(),
            acctBook.getAcctResId(), acctBook.getAcctBookType(),
            acctBook.getCreatedDate(),
            acctBook.getPreBalance(),
            acctBook.getPreExpDate(), acctBook.getCharge(),
            acctBook.getBillId(), acctBook.getPartyType(), acctBook.getPartyCode(), acctBook.getPreSuttleBal(),
            acctBook.getSeconds(), acctBook.getBalId(), acctBook.getContactChannelId(), acctBook.getEventPaymentId(),
            acctBook.getSpId(), acctBook.getPreEffDate(),
            acctBook.getEffSeconds());
      }
    }
    Optional<ConfigItemParamProjection> findIsInsertAcctBookReconcile = configItemRepository.findConfigItem("ACCT",
        "COMMON", "IS_INSERT_ACCT_BOOK_RECONCILE_FOR_BAL_CANCEL");
    String isInsertAcctBookReconcile = findIsInsertAcctBookReconcile.stream()
        .map(ConfigItemParamProjection::getDefaultValue).toString();
    if ("Y".equals(isInsertAcctBookReconcile)) {
      assert balCancelAcctBookDataList != null;
      for (AcctBookData acctBookdata : balCancelAcctBookDataList)
        recordReconcile(acctBookdata);
    }
  }

  private void recordLoan() {
    LoanDataBus loanDataBus = (LoanDataBus) this.dataBus;
    LoanRequest loanReq = loanDataBus.getRequest();
    String isCommChargeNotRet = loanReq.getHasCommission();
    Map<String, Object> extendDataMap = loanDataBus.getExtendDataMap();
    if (isCommChargeNotRet.isEmpty())
      isCommChargeNotRet = "Y";
    DebitBalDto debitBalDto = loanDataBus.getDebitBalDto();
    if (debitBalDto == null) {
      debitBalDto = new DebitBalDto();
      debitBalDto.acctId = loanDataBus.getAcct().getAcctId();
      debitBalDto.bal = loanDataBus.getLoanAmount();
      debitBalDto.spId = this.spId;
      debitBalDto.isCommChargeNotRet = isCommChargeNotRet;
      debitBalDto.lastDebitCharge = debitBalDto.bal;
      debitBalDto.lastDebitDate = loanDataBus.getDateNow();
      debitBalDto.lastRetCharge = 0L;
      debitBalDto.lastCommCharge = null;
      debitBalDto.lastCommRetCharge = 0L;
      debitBalDto.lastDebitBalId = loanDataBus.getAcctBookData().getBalId();
      debitBalDto.loanType = loanReq.getDebitLoanType();
      debitBalDto.pricePlanCode = loanReq.getIllimixPricePlanCode();
      loanDataBus.setDebitBalDto(debitBalDto);
      debitBalRepository.insertDebitBal(debitBalDto.getAcctId(), debitBalDto.getLoanType(), debitBalDto.getBal(),
          debitBalDto.getSpId(), debitBalDto.getCommissionCharge(), debitBalDto.getIsCommChargeNotRet(),
          debitBalDto.getLastDebitCharge(),
          debitBalDto.getLastDebitDate(),
          debitBalDto.getLastRetCharge(), debitBalDto.getLastCommCharge(), debitBalDto.getLastCommRetCharge(),
          debitBalDto.getLastDebitBalId(), debitBalDto.getPricePlanCode());
    } else {
      Optional<ConfigItemParamProjection> findIsRecordDebitBalHis = configItemRepository.findConfigItem("ACCT",
          "ACCOUNT_PUBLIC", "IS_RECORD_DEBIT_BAL_HIS");
      String isRecordDebitBalHis = findIsRecordDebitBalHis.stream().map(ConfigItemParamProjection::getDefaultValue)
          .toString();
      if ("Y".equals(isRecordDebitBalHis)) {
        DebitBalDto debitBalHisDto = debitBalRepository
            .selectDebitBal(
                loanDataBus.getAcct().getAcctId(),
                loanReq.getDebitLoanType())
            .map(selectDebitBalMapper::toSelectDebitBalResponseDto)
            .orElseThrow(() -> new RuntimeException("DebitBal not found"));

        debitBalHisRepository.insertDebitBalHis(debitBalHisDto.getAcctId(), debitBalHisDto.getLoanType(),
            debitBalHisDto.getBal(), debitBalHisDto.getSpId(), debitBalHisDto.getCommissionCharge(),
            debitBalHisDto.getIsCommChargeNotRet(), debitBalHisDto.getLastDebitCharge(),
            debitBalHisDto.getLastDebitDate(),
            debitBalHisDto.getLastRetCharge(),
            debitBalHisDto.getLastRetDate(),
            debitBalHisDto.getLastCommCharge(), debitBalHisDto.getLastCommRetCharge(),
            debitBalHisDto.getLastDebitBalId(), debitBalHisDto.getPricePlanCode(), LocalDateTime.now(),
            debitBalHisDto.getLastButNDebitInfo());
      }
      debitBalRepository.updateDebitBal(loanDataBus.getAcct().getAcctId(), loanReq.getDebitLoanType(),
          loanDataBus.getLoanAmount(), loanDataBus
              .getDateNow(),
          loanDataBus.getAcctBookData().getBalId(), isCommChargeNotRet);
      if (!loanReq.getIllimixPricePlanCode().isEmpty())
        debitBalRepository.updateDebitBalPricePlanCode(loanDataBus.getAcct().getAcctId(), loanReq.getDebitLoanType(),
            loanReq
                .getIllimixPricePlanCode());
    }
    DebitItemDto debitItemDto = new DebitItemDto();
    debitItemDto.acctId = loanDataBus.getAcct().getAcctId();
    debitItemDto.charge = loanDataBus.getLoanAmount();
    debitItemDto.createdDate = loanDataBus.getDateNow();
    debitItemDto.spId = this.spId;
    debitItemDto.pricePlanCode = loanReq.getIllimixPricePlanCode();
    if (extendDataMap != null && extendDataMap.containsKey("VALUE_DATA"))
      debitItemDto.setVolume((Long) extendDataMap.get("VALUE_DATA"));
    debitItemDto.loanType = loanReq.getDebitLoanType();
    // TODO : DIA MAKE SEQUENCE JADI NATIVE QUERY NYA HARUS DIUBAH
    DebitItem debitItem = new DebitItem();
    debitItem.setAcctId(debitItemDto.acctId);
    debitItem.setCharge(debitItemDto.charge);
    debitItem.setCreatedDate(debitItemDto.createdDate.toLocalDate());
    debitItem.setRatio(Math.toIntExact(debitItemDto.ratio));
    debitItem.setReversedItemId(debitItemDto.reversedItemId);
    debitItem.setReversedByItemId(debitItemDto.reversedByItemId);
    debitItem.setSpId(Math.toIntExact(debitItemDto.spId));
    debitItem.setAcctBookId(debitItemDto.acctBookId);
    debitItemRepository.save(debitItem);
    loanDataBus.setDebitItemDto(debitItemDto);
    DebitPaymentDto debitPaymentDto = new DebitPaymentDto();
    debitPaymentDto.acctBookId = loanDataBus.getAcctBookData().getAcctBookId();
    debitPaymentDto.debitItemId = debitItemDto.debitItemId;
    debitPaymentDto.spId = this.spId;
    debitPaymentRepository.insertDebitPayment(debitPaymentDto.getAcctBookId(), debitPaymentDto.getDebitItemId(),
        debitPaymentDto.getSpId(), debitPaymentDto.getRefAttr());
  }

  private void recordInstalment() {
    InstalmentDataBus instalmentDataBus = (InstalmentDataBus) this.dataBus;
    AcctItemDto[] acctItemDtoList = instalmentDataBus.getNewAcctItemList();
    if (acctItemDtoList != null && acctItemDtoList.length > 0) {
      for (AcctItemDto acctItem : acctItemDtoList)
        acctItem.setSpId(this.spId);
      for (AcctItemDto dto : acctItemDtoList) {
        acctItemRepository.acctItemBatchStore(dto.getAcctItemId(), dto.getBillingCycleId(), dto.getAcctId(),
            dto.getSubsId(), dto.getAcctItemTypeId(), dto.getCharge(), dto.getBasicCharge(),
            dto.getCreatedDate(), dto.getState(),
            dto.getStateDate(), dto.getBalId(),
            dto.getSettleCharge(), dto.getSpId(), dto.getAcctBookId());
      }
    }
    InstalmentDto instalmentDto = instalmentDataBus.getInstalmentDto();
    if (instalmentDto != null)
      instalmentRepository.insertInstalment(instalmentDto.getInstlmentId(), instalmentDto.getAcctItemId());
    AcctItemInstalmentDto[] acctItemInstalmentList = instalmentDataBus.getAcctItemInstalmentList();
    if (acctItemInstalmentList != null)
      for (AcctItemInstalmentDto acctItemInstalmentDto : acctItemInstalmentList)
        acctItemInstalmentRepository.insertAcctItemInstalment(acctItemInstalmentDto.getInstlmentId(),
            acctItemInstalmentDto.getSeq(), acctItemInstalmentDto.getAcctId(), acctItemInstalmentDto.getSubsId(),
            acctItemInstalmentDto.getCharge(), acctItemInstalmentDto.getAcctItemId(),
            acctItemInstalmentDto.getBillingCycleId(), acctItemInstalmentDto.getAcctItemTypeId(),
            acctItemInstalmentDto.getState(),
            acctItemInstalmentDto.getStateDate());
  }

  private void recordReconcile(AcctBookData acctBookData, String acctBookType) {
    if (acctBookData == null)
      return;
    if ("N".equals(acctBookType))
      return;
    if ("H".equals(acctBookType) && this.dataBus.getAcctBookDataList() != null && (this.dataBus
        .getAcctBookDataList()).length > 0) {
      recordReconcile(this.dataBus.getAcctBookDataList()[0]);
    } else {
      recordReconcile(acctBookData);
    }
  }

  private void recordReconcile(AcctBookData acctBookData) {
    if (acctBookData == null)
      return;
    AcctBookReconcileDto reconcileDto = acctBookData.getReconcileDto();
    if (reconcileDto != null) {
      reconcileDto.setSpId(this.spId);
      acctBookReconcileRepository.insertAcctBookReconcile(reconcileDto.getAcctBookId(),
          reconcileDto.getReconcileState(), reconcileDto.getLogId(), reconcileDto.getContactChannelId(),
          reconcileDto.getPartnerSn(), reconcileDto.getSpId(), reconcileDto.getAcctBookType(), reconcileDto.getCharge(),
          reconcileDto.getCreatedDate(),
          reconcileDto.getPaymentDate(),
          reconcileDto.getPartnerDetail(), reconcileDto.getTransactionType());
    }
  }

  private void recordReverseFee() {
    ReverseDataBus reverseDataBus = (ReverseDataBus) this.dataBus;
    if (reverseDataBus.getReversePresentAcctBookDataList() != null) {
      for (AcctBookData reverseAcctBook : reverseDataBus.getReversePresentAcctBookDataList()) {
        reverseAcctBook.setSpId(this.spId);
        acctBookRepository.insertAcctBookDataBatch(reverseAcctBook.getAcctBookId(), reverseAcctBook.getAcctId(),
            reverseAcctBook.getAcctResId(), reverseAcctBook.getAcctBookType(),
            reverseAcctBook.getCreatedDate(),
            reverseAcctBook.getPreBalance(),
            reverseAcctBook.getPreExpDate(),
            reverseAcctBook.getCharge(), reverseAcctBook.getBillId(), reverseAcctBook.getPartyType(),
            reverseAcctBook.getPartyCode(), reverseAcctBook.getPreSuttleBal(), reverseAcctBook.getSeconds(),
            reverseAcctBook.getBalId(), reverseAcctBook.getContactChannelId(), reverseAcctBook.getEventPaymentId(),
            reverseAcctBook.getSpId(),
            reverseAcctBook.getPreEffDate(),
            reverseAcctBook.getEffSeconds());
      }
    }
    if (reverseDataBus.getReverseAcmInstDataList() != null) {
      AcmInstData[] acmInstDataList = reverseDataBus.getReverseAcmInstDataList();
      if (CommonUtil.isNotEmpty((Object[]) acmInstDataList))
        for (AcmInstData acmInstData : acmInstDataList) {
          if (acmInstData != null) {
            Long routingId = null;
            if (acmInstData.getRoutingId() == null) {
              // routingId = Long.valueOf(RoutingHelper.getCurrentRoutingId().toString());
            } else {
              routingId = acmInstData.getRoutingId();
            }
            int count = AcmHelper.updateAcm(acmInstData, routingId);
            if (count == 0)
              AcmHelper.insertAcm(acmInstData, routingId);
          }
        }
    }
    if (reverseDataBus.getReverseBalDeductAcctBookDataList() != null) {
      for (AcctBookData reverseAcctBook : reverseDataBus.getReverseBalDeductAcctBookDataList()) {
        reverseAcctBook.setSpId(this.spId);
        acctBookRepository.insertAcctBookDataBatch(reverseAcctBook.getAcctBookId(), reverseAcctBook.getAcctId(),
            reverseAcctBook.getAcctResId(), reverseAcctBook.getAcctBookType(),
            reverseAcctBook.getCreatedDate(),
            reverseAcctBook.getPreBalance(),
            reverseAcctBook.getPreExpDate(),
            reverseAcctBook.getCharge(), reverseAcctBook.getBillId(), reverseAcctBook.getPartyType(),
            reverseAcctBook.getPartyCode(), reverseAcctBook.getPreSuttleBal(), reverseAcctBook.getSeconds(),
            reverseAcctBook.getBalId(), reverseAcctBook.getContactChannelId(), reverseAcctBook.getEventPaymentId(),
            reverseAcctBook.getSpId(),
            reverseAcctBook.getPreEffDate(),
            reverseAcctBook.getEffSeconds());
      }

    }
  }

  private void recordBalTransferReverse() {
    BalTransferReverseDataBus reverseDataBus = (BalTransferReverseDataBus) this.dataBus;
    recordBalTransferReverseFee();
    if (reverseDataBus.getReversePaymentSettDto() != null) {
      reverseDataBus.getReversePaymentSettDto().setSpId(this.spId);
      paymentSettRepository.insertPaymentSett(reverseDataBus.getReversePaymentSettDto().getPaymentSettId(),
          reverseDataBus.getReversePaymentSettDto().getAcctBookId(),
          reverseDataBus.getReversePaymentSettDto().getReversedPaymentSettId(),
          reverseDataBus.getReversePaymentSettDto().getCharge(),
          reverseDataBus.getReversePaymentSettDto().getCreatedDate(),
          reverseDataBus.getReversePaymentSettDto().getSpId(),
          reverseDataBus.getReversePaymentSettDto().getTolerance());
      if (reverseDataBus.getReversePaymentSettItemDtoList() != null)
        for (PaymentSettItemDto item : reverseDataBus.getReversePaymentSettItemDtoList()) {
          paymentSettItemRepository.paymentSettItemBatchStore(item.getPaymentSettId(), item.getAcctItemId(), spId,
              item.getSettleCharge(), item.getBillId());
        }
      acctItemRepository.reverseAcctItemStateAndSettleCharge("C", reverseDataBus
          .getReversePaymentSettDto().getReversedPaymentSettId());
    }
  }

  private void reverseClearLoanInstall(LoanRepayParam loanRepayParam) {
    DebitBalInstallDto[] debitBalInstallDtoArr = debitBalInstallRepository.selectDebitBalInstallByLoanType(this.dataBus
        .getAcct().getAcctId(), loanRepayParam.getDebitItemDto().getLoanType().toString(), false).stream()
        .map(debitBalInstallMapper::toDebitBalInstallDto).toArray(DebitBalInstallDto[]::new);
    if (CommonUtil.isEmpty((Object[]) debitBalInstallDtoArr))
      return;
    Long repayAmount = loanRepayParam.getLoanRepayAmount();
    Long commRepayAmount = loanRepayParam.getCommRepayAmount();
    DebitBalInstallDto debitBalInstallDto = null;
    for (int i = debitBalInstallDtoArr.length - 1; i >= 0; i--) {
      debitBalInstallDto = debitBalInstallDtoArr[i];
      if (debitBalInstallDto.getRetCharge() != 0L || debitBalInstallDto.getCommRetCharge() != 0L) {
        if (repayAmount >= 0L && commRepayAmount >= 0L)
          break;
        if (repayAmount < 0L) {
          Long retAmount = debitBalInstallDto.getRetCharge();
          if (repayAmount <= debitBalInstallDto.getRetCharge()) {
            debitBalInstallDto.setRetCharge(0L);
          } else {
            debitBalInstallDto.setRetCharge(-repayAmount + debitBalInstallDto.getRetCharge());
          }
          repayAmount = repayAmount - retAmount;
        }
        if (commRepayAmount < 0L) {
          Long commRetAmount = debitBalInstallDto.getCommRetCharge();
          if (commRepayAmount <= debitBalInstallDto.getCommRetCharge()) {
            debitBalInstallDto.setCommRetCharge(0L);
          } else {
            debitBalInstallDto.setCommRetCharge(-commRepayAmount + debitBalInstallDto.getCommRetCharge());
          }
          commRepayAmount = commRepayAmount - commRetAmount;
        }
        debitBalInstallRepository.updateDebitBalInstall(debitBalInstallDto.getBal(),
            debitBalInstallDto.getCommissionCharge(), debitBalInstallDto.getRetCharge(),
            debitBalInstallDto.getCommRetCharge(),
            debitBalInstallDto.getLastRetDate(),
            debitBalInstallDto.getAcctId(), debitBalInstallDto.getLoanType(), debitBalInstallDto.getInstallSeq());
      }
    }
  }

  private void updateClearLoanInstall(LoanRepayParam loanRepayParam) {
    DebitBalInstallDto[] debitBalInstallDtoArr = debitBalInstallRepository.selectDebitBalInstallByLoanType(this.dataBus
        .getAcct().getAcctId(), loanRepayParam.getDebitItemDto().getLoanType().toString(), false).stream()
        .map(debitBalInstallMapper::toDebitBalInstallDto).toArray(DebitBalInstallDto[]::new);
    if (CommonUtil.isEmpty((Object[]) debitBalInstallDtoArr))
      return;
    Long repayAmount = loanRepayParam.getLoanRepayAmount();
    Long commRepayAmount = loanRepayParam.getCommRepayAmount();
    for (DebitBalInstallDto debitBalInstallDto : debitBalInstallDtoArr) {
      if (repayAmount <= 0L && commRepayAmount <= 0L)
        break;
      if (repayAmount > 0L) {
        Long loanAmount = debitBalInstallDto.getBal() + debitBalInstallDto.getRetCharge();
        if (repayAmount >= loanAmount) {
          debitBalInstallDto.setRetCharge(-debitBalInstallDto.getBal());
        } else {
          debitBalInstallDto.setRetCharge(-repayAmount + debitBalInstallDto.getRetCharge());
        }
        repayAmount = repayAmount - loanAmount;
      }
      if (commRepayAmount > 0L) {
        Long commAmount = debitBalInstallDto.getCommissionCharge() + debitBalInstallDto.getCommRetCharge();
        if (commRepayAmount >= commAmount) {
          debitBalInstallDto.setCommRetCharge(-debitBalInstallDto.getCommissionCharge());
        } else {
          debitBalInstallDto.setCommRetCharge(-commRepayAmount + debitBalInstallDto.getCommRetCharge());
        }
        commRepayAmount = commRepayAmount - commAmount;
      }
      debitBalInstallDto.setLastRetDate(LocalDateTime.now());
      debitBalInstallRepository.updateDebitBalInstall(debitBalInstallDto.getBal(),
          debitBalInstallDto.getCommissionCharge(), debitBalInstallDto.getRetCharge(),
          debitBalInstallDto.getCommRetCharge(),
          debitBalInstallDto.getLastRetDate(),
          debitBalInstallDto.getAcctId(), debitBalInstallDto.getLoanType(), debitBalInstallDto.getInstallSeq());
    }
  }

  private void addBalAcctItemType(BalDto[] paramArrayOfBal) {
    if (paramArrayOfBal == null || paramArrayOfBal.length <= 0)
      return;
    ArrayList<BalAcctItemTypeDto> arrayList = new ArrayList<>();
    for (BalDto bal : paramArrayOfBal) {
      if (bal != null && bal.getBalAcctItemTypeList() != null && (bal
          .getBalAcctItemTypeList()).length > 0)
        for (byte b1 = 0; b1 < (bal.getBalAcctItemTypeList()).length; b1++) {
          BalAcctItemTypeDto balAcctItemTypeDto = bal.getBalAcctItemTypeList()[b1];
          balAcctItemTypeDto.balId = bal.getBalId();
          balAcctItemTypeDto.spId = this.spId;
          BalAcctItemTypeDto balAcctItemTypeDto2 = balAcctItemTypeRepository
              .selectBalAcctItemType(balAcctItemTypeDto.getBalId(), balAcctItemTypeDto.getAcctItemTypeId())
              .map(balAcctItemTypeMapper::toBalAcctItemTypeDto)
              .orElse(null);
          if (null == balAcctItemTypeDto2)
            arrayList.add(balAcctItemTypeDto);
        }
    }
    if (!arrayList.isEmpty())
      for (BalAcctItemTypeDto dto : arrayList) {
        balAcctItemTypeRepository.balAcctItemTypeBatchStore(dto.getBalId(), dto.getAcctItemTypeId(), dto.getSpId());
      }
  }

  public void dealWithAcmCycle(PaymentDto paymentDto) {
    Optional<ConfigItemParamProjection> findResourceMask38 = configItemRepository.findConfigItem("ACCT",
        "ACCOUNT_PUBLIC", "RESOURCE_MASK_38");
    Optional<ConfigItemParamProjection> findResourceMask18 = configItemRepository.findConfigItem("ACCT",
        "ACCOUNT_PUBLIC",
        "RESOURCE_MASK_18");
    String resourceMask18 = findResourceMask18
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse(null);
    String resourceMask38 = findResourceMask38
        .map(ConfigItemParamProjection::getDefaultValue)
        .orElse(null);
    if (resourceMask38 == null || resourceMask18 == null) {
      logger.debug("Config Item resourceMask18 or resourceMask38 is empty.");
      return;
    }
    AcctBookData acctBookData = this.dataBus.getAcctBookData();
    if (this.dataBus instanceof ReverseDataBus)
      return;
    if (this.dataBus instanceof BalTransferReverseDataBus)
      return;
    if (acctBookData == null || acctBookData.getReCcInstDataList() == null || acctBookData
        .getReCcInstDataList().size() <= 0)
      return;
    String refAttrAcm;
    List<ReCcInstBLData> reCcInstBLDataList = acctBookData.getReCcInstDataList();
    Long resourceId18 = Long.valueOf(resourceMask18);
    Long resourceId38 = Long.valueOf(resourceMask38);
    StringBuilder refAttrAcmBuilder = new StringBuilder();
    for (ReCcInstBLData reCcInstBLData : reCcInstBLDataList) {
      AcmInstData[] acmInstDataList = reCcInstBLData.getAcmInstDataList();
      if (acmInstDataList != null && acmInstDataList.length > 0) {
        logger.debug("begin view acmInstDataList not null");
        for (AcmInstData acmInstData : acmInstDataList) {
          logger.debug("acmInstData::[{}]", acmInstData);
          if (acmInstData != null) {
            Long resourceId = acmInstData.getResourceId();
            if (!resourceId.equals(resourceId38) && !resourceId.equals(resourceId18)) {
              logger.debug("resourceId not 18,38 ::[{}]", resourceId);
            } else {
              if (acmInstData.getRoutingId() == null) {
                // routingId = Long.valueOf(RoutingHelper.getCurrentRoutingId().toString());
              } else {
                acmInstData.getRoutingId();
              }
              QryAcmDict qryAcmDict = getQryAcmDict(resourceId, acctBookData);
              // qryAcmDict.set("ROUTING_ID", routingId);
              // qryAcmDict.set("ACM_TYPE", acmInstData.getAcmType());
              AcmHelper.qryAcmResult(qryAcmDict);
              logger.debug("qryAcmDict result ::[{}]", qryAcmDict);
              Long value = qryAcmDict.getAcmValue();
              if (value == null || value == 0L)
                value = 0L;
              Long currentVal = acmInstData.getAcmValue() + value;
              if (currentVal % 10L == 0L && currentVal != 0L) {
                currentVal = 10L;
              } else {
                currentVal = currentVal % 10L;
              }
              if (resourceId.equals(resourceId38)) {
                refAttrAcmBuilder.append("ACM_CYCLE_38 = ").append(currentVal).append(",");
              } else {
                refAttrAcmBuilder.append("ACM_CYCLE_18 = ").append(currentVal).append(",");
              }
            }
          }
        }
      }
    }
    refAttrAcm = refAttrAcmBuilder.toString();
    if (!refAttrAcm.isEmpty())
      refAttrAcm = refAttrAcm.substring(0, refAttrAcm.lastIndexOf(','));
    logger.debug("refAttrAcm ::[{}]", refAttrAcm);
    String oriRefAttr = paymentDto.getRefAttr();
    if (!oriRefAttr.isEmpty()) {
      oriRefAttr = refAttrAcm;
    } else if (!refAttrAcm.isEmpty()) {
      oriRefAttr = oriRefAttr + "," + refAttrAcm;
    }
    paymentDto.setRefAttr(oriRefAttr);
    logger.debug("ref attr after adding vc18,38:[{}]", oriRefAttr);
  }

  @NotNull
  private QryAcmDict getQryAcmDict(Long resourceId, AcctBookData acctBookData) {
    QryAcmDict qryAcmDict = new QryAcmDict();
    qryAcmDict.setResourceId(resourceId);
    SubsDto subs = this.dataBus.getSubs();
    if (subs != null) {
      // qryAcmDict.set("ACCT_ID", subs.getAcctId());
      qryAcmDict.setAcctId(subs.getAcctId());
      // qryAcmDict.set("SUBS_ID", subs.getSubsId());
      qryAcmDict.setSubsId(subs.getSubsId());
    } else {
      // qryAcmDict.set("ACCT_ID", acctBookData.getAcctId());
      qryAcmDict.setAcctId(acctBookData.getAcctId());
    }
    return qryAcmDict;
  }

  private void recordNotePayPayment(NotePayPaymentDto[] notePayPaymentList, Long paymentId, Long spId) {
    if (CommonUtil.isEmpty((Object[]) notePayPaymentList))
      return;
    int len = notePayPaymentList.length;
    Long[] noteIdArr = new Long[len];
    Long[] chargeArr = new Long[len];
    for (int i = 0; i < len; i++) {
      noteIdArr[i] = notePayPaymentList[i].getNoteId();
      chargeArr[i] = notePayPaymentList[i].getCharge();
    }
    // IDataBusStoreDAO dao = getDataBusStoreDAO();
    notePayPaymentRepository.insertNotePayPayment(paymentId, spId, noteIdArr, chargeArr);
    debitNoteRepository.updateDebitNote(noteIdArr, chargeArr);
  }

  private Long getRealAcct(DepositInstData depositInstData) {
    Long realAcctId = null;
    // DynamicDict acct =
    // getDataBusStoreDAO().qryAcctByAcctId(depositInstData.getAcctId());
    // if (acct != null && acct.getLong("PARENT_ACCT_ID") != null) {
    // realAcctId = acct.getLong("PARENT_ACCT_ID");
    // } else {
    realAcctId = depositInstData.getAcctId();
    // }
    return realAcctId;
  }

  private void recordBalTransferReverseFee() {
    BalTransferReverseDataBus reverseDataBus = (BalTransferReverseDataBus) this.dataBus;
    if (reverseDataBus.getReversePresentAcctBookDataList() != null) {
      for (AcctBookData reverseAcctBook : reverseDataBus.getReversePresentAcctBookDataList()) {
        reverseAcctBook.setSpId(this.spId);
        acctBookRepository.insertAcctBookDataBatch(reverseAcctBook.getAcctBookId(), reverseAcctBook.getAcctId(),
            reverseAcctBook.getAcctResId(), reverseAcctBook.getAcctBookType(),
            reverseAcctBook.getCreatedDate(),
            reverseAcctBook.getPreBalance(),
            reverseAcctBook.getPreExpDate(),
            reverseAcctBook.getCharge(), reverseAcctBook.getBillId(), reverseAcctBook.getPartyType(),
            reverseAcctBook.getPartyCode(), reverseAcctBook.getPreSuttleBal(), reverseAcctBook.getSeconds(),
            reverseAcctBook.getBalId(), reverseAcctBook.getContactChannelId(), reverseAcctBook.getEventPaymentId(),
            reverseAcctBook.getSpId(),
            reverseAcctBook.getPreEffDate(),
            reverseAcctBook.getEffSeconds());
      }
    }
    if (reverseDataBus.getReverseAcmInstDataList() != null) {
      AcmInstData[] acmInstDataList = reverseDataBus.getReverseAcmInstDataList();
      if (CommonUtil.isNotEmpty((Object[]) acmInstDataList))
        for (AcmInstData acmInstData : acmInstDataList) {
          if (acmInstData != null) {
            Long routingId = null;
            if (acmInstData.getRoutingId() == null) {
              // routingId = Long.valueOf(RoutingHelper.getCurrentRoutingId().toString());
            } else {
              routingId = acmInstData.getRoutingId();
            }
            int count = AcmHelper.updateAcm(acmInstData, routingId);
            if (count == 0)
              AcmHelper.insertAcm(acmInstData, routingId);
          }
        }
    }
    if (reverseDataBus.getReverseBalDeductAcctBookDataList() != null) {
      for (AcctBookData acctBook : reverseDataBus.getReversePresentAcctBookDataList()) {
        acctBook.setSpId(this.spId);
        acctBookRepository.insertAcctBookDataBatch(acctBook.getAcctBookId(), acctBook.getAcctId(),
            acctBook.getAcctResId(), acctBook.getAcctBookType(),
            acctBook.getCreatedDate(),
            acctBook.getPreBalance(),
            acctBook.getPreExpDate(), acctBook.getCharge(),
            acctBook.getBillId(), "A", "1", acctBook.getPreSuttleBal(),
            acctBook.getSeconds(), acctBook.getBalId(), acctBook.getContactChannelId(), acctBook.getEventPaymentId(),
            acctBook.getSpId(), acctBook.getPreEffDate(),
            acctBook.getEffSeconds());
      }
    }
  }

}
