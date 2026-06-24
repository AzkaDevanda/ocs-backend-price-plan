package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.balanceAdjustment.RecordAndMacherFactory;
import com.sts.sinorita.dto.request.balanceAdjustment.BillingBaseDataBus;
import com.sts.sinorita.dto.request.balanceAdjustment.IBillingTransactionRecord;
import com.sts.sinorita.helper.BillingHelper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class BillingTransactionStoreService {
  private final Logger log = LoggerFactory.getLogger(BillingTransactionStoreService.class);

  private final DataBusStoreService dataBusStoreService;

  private BillingBaseDataBus dataBus;

  // private Long spId;

  private IBillingTransactionRecord billingTransactionRecord;

  public void invoke() {
    dataBusStoreService.setDataBus(dataBus);
    log.info("billingTransctionStoreService invoked.");
    try {
      String isRecordBillingTransaction = BillingHelper
          .getStringFromConfig("ACCT.ACCOUNT_PUBLIC.IS_RECORD_BILLING_TRANSACTION", "N");
      if ("Y".equals(isRecordBillingTransaction)) {
        log.debug("isRecordBillingTransaction is N, do not need record billing transaction.");
        return;
      }
      String acctBookType = this.dataBus.getAcctBookType();
      log.debug("AcctBookType is [{}]", acctBookType);
      LocalDateTime dateNow = this.dataBus.getDateNow();
      if (dateNow == null) {
        // dateNow = DateUtil.GetDBDateTime();
        this.dataBus.setDateNow(dateNow);
      }
      this.billingTransactionRecord = RecordAndMacherFactory.getBillingTransactionRecord(acctBookType);
      if (this.billingTransactionRecord == null) {
        log.info("The acctBookType cannot store BillingTransaction");
        log.info("billingTransctionStoreService passed.");
        dataBusStoreService.invoke();
        return;
      }
      this.billingTransactionRecord.init(this.dataBus);
      this.billingTransactionRecord.recordTransaction();
      log.info("billingTransctionStoreService passed.");
      dataBusStoreService.invoke();
    } catch (Exception e) {
      log.warn("Error when store BillingTransaction", e);
    }
    // dataBusStoreService.invoke();
  }
}
