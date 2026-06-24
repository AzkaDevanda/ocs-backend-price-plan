package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.dto.response.accountconfig.BillingCycleDto;
import com.sts.sinorita.helper.BillingSeqHelper;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.BillingTransactionRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.repository.EventTableRepository;
import com.sts.sinorita.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public abstract class BillingTransactionRecord implements IBillingTransactionRecord {
  private final Logger logger = LoggerFactory.getLogger(BillingTransactionRecord.class);

  protected FeeRecorder feeRecorder;

  protected IBillingTransactionRecord.IGlCodeMatcher matcher;

  protected GlCodeCfgDto busiGlCodeCfgDto;

  protected GlCodeCfgDto acctBookGlCodeCfgDto;

  protected Long subsEventId;
  protected Long seq = 0L;
  protected Long transactionId;
  @Autowired
  private BillingTransactionRepository billingTransactionRepository;
  @Autowired
  private ConfigItemRepository configItemRepository;
  @Autowired
  private EventTableRepository eventTableRepository;
  @Autowired
  private BillCycleService billCycleService;
  @Autowired
  private BillingSeqHelper billingSeqHelper;

  private Long acctId;
  private Long subsId;
  private Long spId;
  private LocalDateTime createdDate;

  @Override
  public void init(BillingBaseDataBus dataBus) {
    AcctDto acct = dataBus.getAcct();
    this.acctId = acct.getAcctId();
    this.spId = acct.getSpId();
    SubsDto subs = dataBus.getSubs();
    if (subs != null)
      this.subsId = subs.getSubsId();
    this.createdDate = dataBus.getDateNow();
  }

  @Override
  public void recordTransaction() {
    recordBusiInfo();
    recordAcctBook();
    if (this.feeRecorder != null)
      this.feeRecorder.recordFee();
  }

  protected abstract BillingTransactionDto prepare();

  protected abstract void recordAcctBook();

  protected void recordBusiInfo() {
    BillingTransactionDto dict = prepare();
    storeBillingTransaction(dict);
  }

  protected void doRecordAcctBook(AcctBookData acctBookData) {
    if (this.acctBookGlCodeCfgDto == null)
      logger.warn("can not match the GL_CODE_CFG.");
    BillingTransactionDto dict = new BillingTransactionDto();
    // dict.set("ACCT_BOOK_ID", acctBookData.getAcctBookId());
    dict.setAcctBookId(acctBookData.getAcctBookId());
    // dict.set("ACCT_BOOK_TYPE", acctBookData.getAcctBookType());
    dict.setAcctBookType(acctBookData.getAcctBookType());
    // dict.set("ACCT_RES_ID", acctBookData.getAcctResId());
    dict.setAcctResId(acctBookData.getAcctResId());
    // dict.set("PRE_BALANCE1", acctBookData.getPreBalance());
    dict.setPreBalance1(acctBookData.getPreBalance());
    if (this.acctBookGlCodeCfgDto != null) {
      // dict.set("GL_CODE1", this.acctBookGlCodeCfgDto.getGlCode());
      dict.setGlCode1(this.acctBookGlCodeCfgDto.getGlCode());
      // dict.set("GL_DIRECTION1", this.acctBookGlCodeCfgDto.getGlDirection());
      dict.setGlDirection(this.acctBookGlCodeCfgDto.getGlDirection());
      // dict.set("CHARGE1", acctBookData.getCharge().longValue() *
      // this.acctBookGlCodeCfgDto.getGlCoefficient().longValue());
      dict.setCharge1(acctBookData.getCharge() * this.acctBookGlCodeCfgDto.getGlCoefficient());
    } else {
      // dict.set("CHARGE1", acctBookData.getCharge());
      dict.setCharge1(acctBookData.getCharge());
    }
    if (acctBookData.getAcctId() != null)
      dict.setAcctId(acctBookData.getAcctId());
    // dict.set("ACCT_ID", acctBookData.getAcctId());
    // dict.set("CONTACT_CHANNEL_ID", acctBookData.getContactChannelId());
    dict.setContactChannelId(acctBookData.getContactChannelId());
    // dict.set("SUBS_EVENT_ID", this.subsEventId);
    dict.setSubsEventId(this.subsEventId);
    // dict.set("PARTNER_DATE", acctBookData.getCreatedDate());
    dict.setPartnerDate(this.createdDate);
    storeBillingTransaction(dict);
  }

  public void storeBillingTransaction(BillingTransactionDto dict) {
    String tableName = qryEventTable();
    if (tableName.isEmpty()) {
      logger.debug("table name is null.");
      return;
    }
    if (dict.getAcctId() == null)
      // dict.set("ACCT_ID", this.acctId);
      dict.setAcctId(this.acctId);
    if (dict.getSubsId() == null && this.subsId != null)
      // dict.set("SUBS_ID", this.subsId);
      dict.setSubsId(this.subsId);
    if (dict.getCreatedDate() == null)
      // dict.set("CREATED_DATE", this.createdDate);
      dict.setCreatedDate(this.createdDate);
    if (this.transactionId == null)
      this.transactionId = billingSeqHelper.getBillingSeq("TRANSACTION_ID_SEQ");
    // dict.set("SP_ID", this.spId);
    dict.setSpId(this.spId);
    // dict.set("TRANSACTION_ID", this.transactionId);
    dict.setTransactionId(this.transactionId);
    Long long_1 = this.seq, long_2 = this.seq = this.seq + 1L;
    // dict.set("SEQ", long_1);
    dict.setSeq(long_1);
    // dict.set("BILLING_TRANSACTION", tableName);
    dict.setBillingTransaction(tableName);
    // Long routingId = RoutingHelper.getRoutingIdByAcctId(dict.getLong("ACCT_ID"));
    // IBillingTransactionStoreDAO dao = (IBillingTransactionStoreDAO)
    // DAOFactory.createModuleDAO(
    // "BillingTransactionStore", "billing.fc.common.billingtransactionstore",
    // JdbcUtil4BL.getDbIdentifier(), routingId);
    billingTransactionRepository.insertBillingTransaction(dict.getTransactionId(), dict.getSeq(), dict.getAcctId(),
        dict.getSubsId(), dict.getSubsEventId(), dict.getAcctBookType(), dict.getPaymentMethodId(), (String) null,
        (Long) null, dict.getAcctResId(), dict.getReasonId(), "N", (Long) null, (String) null, (String) null,
        (String) null, (String) null, (Long) null, (String) null, (String) null, dict.getContactChannelId(),
        (Long) null, dict.getEventInstId(), (Long) null, dict.getAcctBookId(), (Long) null, (Long) null,
        dict.getCharge1(), dict.getGlCode1(), dict.getGlDirection1(), LocalDateTime.now(), dict.getPartnerDate(),
        Integer.valueOf(0));
  }

  private String qryEventTable() {
    Optional<ConfigItemParamProjection> findBillingCycleTypeId = configItemRepository.findConfigItem("ACCT", "COMMON",
        "BILLING_TRANSACTION_CYCLE_TYPE_ID");
    Long billingCycleTypeId = findBillingCycleTypeId
        .map(ConfigItemParamProjection::getDefaultValue)
        .filter(v -> v != null && !v.isBlank())
        .map(Long::valueOf)
        .orElse(null);
    // Long billingCycleTypeId =
    // BillingHelper.getLongFromConfig("ACCT.COMMON.BILLING_TRANSACTION_CYCLE_TYPE_ID",
    // Long.valueOf(0L));
    BillingCycleDto curBillingCycleByBillingCycle = billCycleService
        .selectCurBillingCycleByBillingCycleTypeId(billingCycleTypeId);
    // String[] subTableName =
    // eventTableRepository.getSubTableName(curBillingCycleByBillingCycle.getBillingCycleId(),
    // "BILLING_TRANSACTION");
    List<String> tables = eventTableRepository.getSubTableName(curBillingCycleByBillingCycle.getBillingCycleId(),
        "BILLING_TRANSACTION");
    String[] subTableName = tables.toArray(new String[0]);

    String eventTable = "";
    if (CommonUtil.isNotEmpty((Object[]) subTableName))
      eventTable = subTableName[0];
    return eventTable;
  }
}
