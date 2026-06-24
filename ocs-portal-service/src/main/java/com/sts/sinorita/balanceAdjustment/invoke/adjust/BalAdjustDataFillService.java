package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookData;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctBookDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AcctDto;
import com.sts.sinorita.dto.request.balanceAdjustment.AdjustDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BalDto;
import com.sts.sinorita.dto.request.balanceAdjustment.BillDto;
import com.sts.sinorita.dto.request.balanceAdjustment.SubsDto;
import com.sts.sinorita.entity.Bal;
import com.sts.sinorita.helper.BalHelper;
import com.sts.sinorita.helper.BillingSeqHelper;
import com.sts.sinorita.mapper.balanceAdjustment.AcctBookMapper;
import com.sts.sinorita.mapper.balanceAdjustment.AdjustReasonMapper;
import com.sts.sinorita.projection.balanceAdjustment.FindAcctBookByPartnerSnProjection;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.AcctBookRepository;
import com.sts.sinorita.repository.AdjustReasonRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.SystemParamRepository;
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
public class BalAdjustDataFillService {

  private final BalAdjustTriggerDataFillService balAdjustTriggerDataFillService;

  private final BillingSeqHelper billingSeqHelper;

  private final SystemParamRepository systemParamRepository;

  private final AcctBookRepository acctBookRepository;

  private final AcctBookMapper acctBookMapper;

  private final AdjustReasonRepository adjustReasonRepository;

  private final AdjustReasonMapper adjustReasonMapper;

  private final ConfigItemRepository configItemRepository;

  private Long spId;

  private Long contactChannelId;

  private Long acctItemTypeId;

  private String comments;

  private Long adjustReasonId;

  private String partnerSnOld;

  private Long reversedAdjustId;

  private Long acctResId;

  private BalDto[] newBalList;

  private AcctBookData acctBookData;

  private Bal modBal;

  private BillDto bill;

  private AcctDto acct;

  private AdjustDto adjustDto;

  private BalDto balBeforeAdjust;

  private BalDto balAfterAdjust;

  private String deductFlag;

  private SubsDto subs;

  public Information getInformation() {
    return null;
  }

  public void invoke() {
    this.balAfterAdjust = BalHelper.getBalFromListByBalId(this.newBalList, this.modBal.getBalId());
    recordAdjust();
    updateBill();
    setBalOverdraftCheckFlag();
    balAdjustTriggerDataFillService.invoke();
  }

  private void setBalOverdraftCheckFlag() {
    BalDto updateBal = BalHelper.getBalFromListByBalId(this.acct.getUpdateBalList(), this.modBal.getBalId());
    if ("0".equals(this.deductFlag)) {
      if (updateBal != null)
        updateBal.setCheckMode("1|1|1");
    } else {
      Optional<ConfigItemParamProjection> findBalCheckMode = configItemRepository.findConfigItem("ACCT",
          "ACCT_BILLING", "BAL_ADJUST_CHECK_MODE");
      String balCheckMode = findBalCheckMode.map(ConfigItemParamProjection::getDefaultValue).orElse(null);
      if (StringUtil.isNotEmpty(balCheckMode) && updateBal != null)
        updateBal.setCheckMode(balCheckMode);
    }
  }

  public void recordAdjust() {
    if (this.adjustDto == null)
      this.adjustDto = new AdjustDto();
    this.adjustDto.setAdjustId(this.acctBookData.getAcctBookId());
    this.adjustDto.setSpId(this.spId);
    if (this.subs != null)
      this.adjustDto.setSubsId(this.subs.getSubsId());
    fillupAdjustComment();
    if (this.adjustReasonId != null) {
      // IBalAdjustDataFillDAO balAdjustDataFillDAO = (IBalAdjustDataFillDAO)
      // DAOFactory.createModuleDAO(
      // "BalAdjustDataFill", "billing.fc.balance.baladjustdatafill",
      // JdbcUtil4BL.getDbBackService());
      AdjustDto tmpAdjustDto = adjustReasonRepository.selectAdjustReason(this.adjustReasonId)
          .map(adjustReasonMapper::toAdjustDto).orElse(null);
      if (tmpAdjustDto == null || tmpAdjustDto.getAdjustReasonId() == null) {
        log.info(StringUtil.stringFormat("The adjustReasonId = [{0}]", this.adjustReasonId.toString()));
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00053"));
        // ExceptionHandler.publish("BL-S-ACT-00053", msg, 0);
      }
    }
    this.adjustDto.setAdjustReasonId(this.adjustReasonId);
    if (this.reversedAdjustId == null && StringUtil.isNotEmpty(this.partnerSnOld)) {
      // IBalAdjustDataFillDAO balAdjustDataFillDAO = (IBalAdjustDataFillDAO)
      // DAOFactory.createModuleDAO(
      // "BalAdjustDataFill", "billing.fc.balance.baladjustdatafill",
      // JdbcUtil4BL.getDbBackService());
      List<FindAcctBookByPartnerSnProjection> projections = acctBookRepository.findAcctBookByPartnerSn(
          this.partnerSnOld,
          this.contactChannelId);
      AcctBookDto acctBookDto = projections.stream()
          .findFirst()
          .map(acctBookMapper::toAcctBookDtoForFindAcctBookByPartnerSn)
          .orElse(null);
      if (acctBookDto != null)
        this.reversedAdjustId = acctBookDto.getAcctBookId();
    }
    if (this.reversedAdjustId != null)
      this.adjustDto.setReversedAdjustId(this.reversedAdjustId);
  }

  private void fillupAdjustComment() {
    if (this.acctItemTypeId != null) {
      this.adjustDto.setComments(this.acctItemTypeId.toString());
    } else if (StringUtil.isNotEmpty(this.comments)) {
      this.adjustDto.setComments(this.comments);
    } else {
      BalDto oldBal = this.balBeforeAdjust;
      BalDto newBal = this.balAfterAdjust;
      String tmpComments = StringUtil.stringFormat(
          "Before adjustDto: charge=[{0}],exp_date=[{1}]\r\nAfter adjustDto:charge=[{2}],exp_date=[{3}]",
          (oldBal != null) ? oldBal

              .getRealBal() : null,
          (oldBal != null && oldBal.getExpDate() != null) ? oldBal
              .getExpDate().toString() : null,
          (newBal != null) ? newBal.getRealBal() : null, (newBal != null && newBal
              .getExpDate() != null) ? newBal.getExpDate().toString() : null);
      this.adjustDto.setComments(tmpComments);
    }
  }

  public void updateBill() {
    if (this.acctResId.equals(systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID"))
        && this.acct.getRecordBill().booleanValue())
      if (this.bill == null) {
        BalDto oldBal = this.balBeforeAdjust;
        this.bill = new BillDto();
        this.bill.setBillId(billingSeqHelper.getBillingSeq("BILL_ID_SEQ"));
        this.bill.setBillNbr(this.bill.getBillId().toString());
        this.bill.setAcctId(this.acctBookData.getAcctId());
        this.bill.setBillingCycleId(this.acct.getCurrentBillingCycleId());
        this.bill.setPreBalance(oldBal.getGrossBal());
        this.bill.setDue(Long.valueOf(0L));
        this.bill.setAdjustCharge(this.acctBookData.getCharge());
        this.bill.setDisputeCharge(Long.valueOf(0L));
        this.bill.setRecvCharge(Long.valueOf(0L));
        this.bill.setOperationType("A");
      } else {
        Long adjustCharge = Long
            .valueOf((this.bill.getAdjustCharge() == null) ? 0L : this.bill.getAdjustCharge().longValue());
        this.bill.setAdjustCharge(Long.valueOf(adjustCharge.longValue() + this.acctBookData.getCharge().longValue()));
        if (!"A".equals(this.bill.getOperationType()))
          this.bill.setOperationType("M");
      }
  }

}
