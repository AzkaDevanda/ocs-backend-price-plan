package com.sts.sinorita.balanceAdjustment.invoke.adjust;

import com.sts.sinorita.balanceAdjustment.AcctResService;
import com.sts.sinorita.balanceAdjustment.AcctService;
import com.sts.sinorita.balanceAdjustment.BillCycleService;
import com.sts.sinorita.balanceAdjustment.BillExService;
import com.sts.sinorita.balanceAdjustment.BillService;
import com.sts.sinorita.balanceAdjustment.ProdService;
import com.sts.sinorita.balanceAdjustment.SubsService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.entity.Acct;
import com.sts.sinorita.entity.BillEntity;
import com.sts.sinorita.entity.Subs;
import com.sts.sinorita.repository.BillRepository;
import com.sts.sinorita.repository.SubsRepository;
import com.sts.sinorita.repository.SystemParamRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class BasicInfoInquiryService {
  private final Logger log = LoggerFactory.getLogger(BasicInfoInquiryService.class);

  private final ProdService prodService;
  private final BillService billService;
  private final AcctResService acctResService;
  private final BillExService billExService;
  private final SubsService subsService;
  private final BillCycleService billCycleService;
  private final AcctService acctService;
  private final SubsRepository subsRepository;
  private final BillRepository billRepository;
  private final SystemParamRepository systemParamRepository;

  public String isSubsLimit;
  public Boolean isLockRetry;
  private String prefix;
  private String accNbr;
  private Long subsId;
  private Long acctId;
  private String acctNbr;
  private Long billId;
  private String billNbr;
  private Long acctResId;
  private String acctResCode;
  private Long spId;
  private Boolean acctLockFlag;
  private Acct acct;
  private BillEntity bill;
  private Subs subs;
  private AcctResDto acctResDto;
  private Map<String, Object> extMap;

  public void invoke () {
    qryBillInfo();
    qrySubsInfo();
    qryAcctInfo();
    qryAcctResInfo();
    dataFillUpForAcct();
    dataFillUpForSubs();
  }

  private void qryBillInfo () {
    if (this.bill == null)
      if (this.billId != null || !this.billNbr.isEmpty()) {
        if (this.billId != null) {
          this.bill = billRepository.selectBillByBillId(this.billId);
        } else {
          this.bill = billRepository.selectBillByBillNbr(this.billNbr);
        }
        if (this.bill == null) {
          if (!this.billNbr.isEmpty()) {
            log.warn("bill number is [{}].", this.billNbr);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00157"));
          }
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00071"));
        }
      }
  }

  private void qrySubsInfo () {
    if (this.subs == null)
      if (this.subsId != null || !this.accNbr.isEmpty()) {
        if (this.subsId != null) {
          this.subs = subsRepository.selectSubsBySubsId(this.subsId);
        } else {
          this.subs = subsRepository.selectSubsByAccNbrAndSpId(this.prefix, this.accNbr, this.spId);
        }
        if (this.subs == null)
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00140"));
        if (this.spId == null)
          this.spId = this.subs.getSpId();
      }
    }

  private void qryAcctInfo () {
    if (this.acct == null) {
      boolean isThrow = false;
      if (this.bill != null && this.bill.getAcctId() != null) {
        isThrow = true;
        this.acct = acctService.getAcctByAcctId(this.bill.getAcctId(), this.acctLockFlag, this.isLockRetry);
      } else if (this.subs != null && this.subs.getAcctId() != null) {
        isThrow = true;
        this.acct = acctService.getAcctByAcctId(this.subs.getAcctId(), this.acctLockFlag, this.isLockRetry);
      } else if (this.acctId != null) {
        isThrow = true;
        this.acct = acctService.getAcctByAcctId(this.acctId, this.acctLockFlag, this.isLockRetry);
      } else if (!this.acctNbr.isEmpty()) {
        isThrow = true;
        this.acct = acctService.getAcctByAcctNbr(this.acctNbr, this.acctLockFlag, this.isLockRetry);
      }
      if (this.spId == null && this.acct != null)
        this.spId = this.acct.getSpId();
      if (this.acct == null && isThrow) {
        if (!this.acctNbr.isEmpty()) {
          log.warn("Account number is [{}].", this.acctNbr);
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00158"));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00072"));
      }
    } else if (this.acctLockFlag != null && this.acctLockFlag) {
      acctService.getAcctByAcctId(this.acct.getAcctId(), Boolean.TRUE, this.isLockRetry);
    }
  }

  private void qryAcctResInfo () {
    if (this.acctResDto == null)
      if (this.acctResId != null || !this.acctResCode.isEmpty()) {
        if (this.acctResId != null) {
          this.acctResDto = acctResService.getAcctResById(this.acctResId);
        } else {
          this.acctResDto = acctResService.getAcctResByCode(this.acctResCode, null);
        }
      } else {
        Long defaultAcctResId = Long.valueOf(systemParamRepository.selectSystemParam("DEFAULT_ACCT_RES_ID"));
        this.acctResDto = acctResService.getAcctResById(defaultAcctResId);
      }
    if (this.acctResDto == null)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00074"));
  }

  private void dataFillUpForAcct () {
    if (this.acct == null)
      return;
    if (this.acct.getAllSubsList() == null || (this.acct.getAllSubsList()).length <= 0)
      this.acct.setAllSubsList(subsService.selectAllSubsListByAcctId(this.acctId));
    if (this.acct.getAllBalList() == null || (this.acct.getAllBalList()).length <= 0) {
      Long filterSubsId = null;
      if (!this.isSubsLimit.isEmpty() && "Y".equalsIgnoreCase(this.isSubsLimit))
        filterSubsId = this.subsId;
      if (acctService.isBigAccount(this.acct.getAcctId()).booleanValue()) {
        if (this.subs != null) {
          this.acct.setAllBalList(acctService.qryAllBalListForBigAcct(this.subs.getSubsId(), this.acct.getRoutingId(), this.acct.getAcctId()));
        } else {
          this.acct.setAllBalList(acctService.qryAllBalListForBigAcct(filterSubsId, this.acct.getRoutingId(), this.acct.getAcctId()));
        }
      } else {
        this.acct.setAllBalList(acctService.qryAllBalList(filterSubsId, this.acct.getRoutingId(), this.acct.getAcctId()));
      }
      this.acct.setNewBalList(this.acct.getAllBalList());
      this.acct.setOldBalList(this.acct.getAllBalList());
    }
    if (this.acct.getRecordBill() == null)
      this.acct.setRecordBill(acctService.isRecordBill(this.acct.getPostpaid()));
    if (this.acct.getBill() == null && this.acct.getRecordBill().booleanValue()) {
      BillEntity currentBill = billService.qryBillInfobyAcctId(this.acct.getAcctId());
      this.acct.setBill(currentBill);
    }
    if (this.acct.getCurrentBillingCycleId() == null)
      this.acct.setCurrentBillingCycleId(billCycleService.qryCurrentBillingCycleId(this.acct.getBillingCycleTypeId()));
  }

  private void dataFillUpForSubs () {
    if (this.subs == null)
      return;
    if (this.subs.getDefaultPricePlanId() == null)
      this.subs.setDefaultPricePlanId(subsService.qryDefaultPricePlanId(this.subs.getSubsId()));
    if (this.subs.getProd() == null)
      this.subs.setProd(prodService.qryProdBySubsId(this.subs.getSubsId()));
  }


}
