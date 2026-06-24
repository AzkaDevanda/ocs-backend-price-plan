package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.balanceAdjustment.invoke.adjust.DealOweEventChargeService;
import com.sts.sinorita.dto.Information;
import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.dto.response.balanceAdjustment.RechargeRecentDto;
import com.sts.sinorita.mapper.balanceAdjustment.AcctBookMapper;
import com.sts.sinorita.mapper.balanceAdjustment.AcctRecentOperMapper;
import com.sts.sinorita.mapper.balanceAdjustment.PaymentMapper;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.*;
import com.sts.sinorita.util.CompareUtils;
import com.sts.sinorita.util.DateUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class RecordAcctRecentOperateService {
  private final Logger logger = LoggerFactory.getLogger(RecordAcctRecentOperateService.class);
  private final Set<String> acctBookTypeForRecentOper = new HashSet<>();
  private int recordNumForRecentOper = 0;

  //  private IAcctRecentOperDAO acctRecentOperDAO = null;
  //  @DataMapping(mapping = "this", required = true)
  private BillingBaseDataBus dataBus;
  @Autowired
  private ConfigItemRepository configItemRepository;
  @Autowired
  private AcctRecentOperRepository acctRecentOperRepository;
  @Autowired
  private AcctBookRepository acctBookRepository;
  @Autowired
  private PaymentRepository paymentRepository;
  @Autowired
  private ReAbInstRepository reAbInstRepository;
  @Autowired
  private AcctRecentOperMapper acctRecentOperMapper;
  @Autowired
  private AcctBookMapper acctBookMapper;
  @Autowired
  private PaymentMapper paymentMapper;
  @Autowired
  private BalAdjustCdrRecordService balAdjustCdrRecordService;
  @Autowired
  private DealOweEventChargeService dealOweEventChargeService;

  private Boolean isAdjustTrue;

//  public BillingBaseDataBus getDataBus () {
//    return this.dataBus;
//  }

  public void invoke () {
    logger.info("recordAcctRecentOperateService invoked.");
    if (this.dataBus.getAcctBookType().isEmpty()) {
      logger.debug("AcctBookType is empty. return.");
      return;
    }
    Optional<ConfigItemParamProjection> findRecordNumConfig = configItemRepository.findConfigItem("ACCT", "COMMON", "RECORD_NUM_FOR_RECENT_OPER");
    String recordNumConfig = findRecordNumConfig.map(ConfigItemParamProjection::getParamValue).orElse(null);
//    String recordNumConfig = ConfigItemCache.instance().getString("ACCT.COMMON.RECORD_NUM_FOR_RECENT_OPER", null);
    if (recordNumConfig != null)
      try {
        this.recordNumForRecentOper = Integer.parseInt(recordNumConfig.trim());
      } catch (NumberFormatException ex) {
        logger.debug("account.RecordNumForRecentOper[" + recordNumConfig + "].It is supposed to be an number");
      }
    if (this.recordNumForRecentOper != 0) {
      Optional<ConfigItemParamProjection> findAcctBookTypeConfig = configItemRepository.findConfigItem("ACCT", "COMMON", "ACCT_BOOK_TYPE_FOR_RECENT_OPER");
      String acctBookTypeConfig = findAcctBookTypeConfig.map(ConfigItemParamProjection::getParamValue).orElse("");
//      String acctBookTypeConfig = ConfigItemCache.instance().getString("ACCT.COMMON.ACCT_BOOK_TYPE_FOR_RECENT_OPER", "");
      String[] temp = acctBookTypeConfig.split(",");
      for (String type : temp) {
        String typeTemp = type.trim();
        if (CompareUtils.withinIgnoreCase(typeTemp, "A", "B", "H", "E", "J", "M", "G")) {
          this.acctBookTypeForRecentOper.add(typeTemp);
        } else if (CompareUtils.withinIgnoreCase(typeTemp, "D", "Q", "F", "W", "I", "P", "U")) {
          this.acctBookTypeForRecentOper.add(typeTemp);
        } else if (CompareUtils.withinIgnoreCase(typeTemp, "T", "L", "K")) {
          this.acctBookTypeForRecentOper.add(typeTemp);
        } else {
          logger.debug("billing.RecordAcctRecentOperate contains invalid acct_book_type:[{}]", typeTemp);
        }
      }
    }
    recordAcctRecentOperation(this.dataBus.getAcctBookData(), this.dataBus.getAcctBookType());
    logger.info("recordAcctRecentOperateService passed.");
    if(isAdjustTrue == true){
        dealOweEventChargeService.invoke();
    }
    else{
        balAdjustCdrRecordService.setAcctBookData(this.dataBus.getAcctBookData());
        balAdjustCdrRecordService.invoke();
    }
  }

  public Information getInformation () {
    return null;
  }

  private void recordAcctRecentOperation (AcctBookData acctBookDto, String acctBookType) {
    if ("H".equals(acctBookType) && this.dataBus.getAcctBookDataList() != null && (this.dataBus
      .getAcctBookDataList()).length > 0) {
      for (int i = 0; i < (this.dataBus.getAcctBookDataList()).length; i++)
        recordAcctRecentOperation(this.dataBus.getAcctBookDataList()[i]);
    } else if ("T".equals(acctBookType)) {
      BalExchangeDataBus balExDataBus = (BalExchangeDataBus) this.dataBus;
      recordAcctRecentOperation(balExDataBus.getAcctBookDataOut());
      recordAcctRecentOperation(balExDataBus.getAcctBookDataIn());
    } else {
      recordAcctRecentOperation(acctBookDto);
    }
  }

  public void recordAcctRecentOperation (AcctBookData acctBookDto) {
    logger.debug("Start calling AcctRecentOperMgr.recordAcctRecentOperation.");
    if (acctBookDto == null)
      return;
    if (this.recordNumForRecentOper == 0 || !this.acctBookTypeForRecentOper.contains(acctBookDto.getAcctBookType())) {
      logger
        .debug("Quit recording recent account operation for params missing:[RecordNumForRecentOper],[AcctBookTypeForRecentOper]. Please check config file Busi_default.xml");
      return;
    }
    Map<String, Queue<String>> nodeMap = null;
    AcctRecentOperDto fetchedRecentOperDto = acctRecentOperRepository.selectAcctRecentOper(acctBookDto.getAcctId()).map(acctRecentOperMapper::toAcctRecentOperDto).orElse(null);
    if (fetchedRecentOperDto != null) {
      String recentOper = fetchedRecentOperDto.getRecentOper();
      nodeMap = parseNodeMap(recentOper);
    } else {
      nodeMap = new HashMap<>();
    }
    boolean isReverse = false;
    if ("P".equalsIgnoreCase(acctBookDto.getAcctBookType()))
      isReverse = removeReversedRecentAcctOper(acctBookDto, nodeMap);
    AcctRecentOperDto archiveDto = buildAcctRecentOperDto(acctBookDto, nodeMap, isReverse);
    if (fetchedRecentOperDto != null) {
      acctRecentOperRepository.updateAcctRecentOper(archiveDto.recentOper, archiveDto.updateDate, archiveDto.acctId);
      logger.debug("Succeed in updating one AcctRecentOper record.");
    } else {
      acctRecentOperRepository.insertAcctRecentOper(archiveDto.acctId, archiveDto.recentOper, archiveDto.updateDate);
      logger.debug("Succeed in adding one AcctRecentOper record.");
    }
  }

  public Map<String, Queue<String>> parseNodeMap (String recentOper) {
    Map<String, Queue<String>> nodeMap = new HashMap<>();
    Queue<String> recordQueue = null;
    String acctBookType = null;
    if (recentOper != null && !recentOper.isEmpty()) {
      String[] items = recentOper.split(";");
      for (String item : items) {
        String[] node = item.split("=");
        if (node.length == 2) {
          acctBookType = node[0];
          recordQueue = new LinkedList<>();
          String[] records = node[1].split("\\|");
          for (String record : records) {
            if (!record.isEmpty())
              recordQueue.add(record);
          }
          nodeMap.put(acctBookType, recordQueue);
        }
      }
    }
    return nodeMap;
  }

  private String recordQueueToString (Queue<String> recordQueue) {
    if (recordQueue == null)
      return "";
    StringBuilder recordBuff = new StringBuilder();
    for (String record : recordQueue) {
      if (!recordBuff.isEmpty())
        recordBuff.append("|");
      recordBuff.append(record);
    }
    return recordBuff.toString();
  }

  private AcctRecentOperDto buildAcctRecentOperDto (AcctBookData acctBookDto, Map<String, Queue<String>> nodeMap, boolean isReverse) {
    if (!isReverse) {
      Queue<String> recordQueue = null;
      if (nodeMap.containsKey(acctBookDto.getAcctBookType())) {
        recordQueue = nodeMap.get(acctBookDto.getAcctBookType());
      } else {
        recordQueue = new LinkedList<>();
        nodeMap.put(acctBookDto.getAcctBookType(), recordQueue);
      }
      recordQueue.offer(buildRecord(acctBookDto));
      if (recordQueue.size() > this.recordNumForRecentOper)
        recordQueue.poll();
    }
    AcctRecentOperDto acctRecentOperDto = new AcctRecentOperDto();
    acctRecentOperDto.setAcctId(acctBookDto.getAcctId());
    acctRecentOperDto.setUpdateDate(DateUtil.GetDBDateTime());
    acctRecentOperDto.setRecentOper(buildAcctOper(nodeMap));
    return acctRecentOperDto;
  }

  private String buildRecord (AcctBookData acctBookDto) {
    StringBuffer sb = new StringBuffer();
    Object temp = null;
    temp = acctBookDto.getAcctBookId();
    sb.append((temp != null) ? temp.toString() : "");
    sb.append(",");
    temp = acctBookDto.getAcctBookType();
    sb.append((temp != null) ? temp.toString() : "");
    sb.append(",");
    temp = acctBookDto.getAcctResId();
    sb.append((temp != null) ? temp.toString() : "");
    sb.append(",");
    temp = acctBookDto.getPreBalance();
    sb.append((temp != null) ? temp.toString() : "");
    sb.append(",");
    temp = acctBookDto.getCharge();
    sb.append((temp != null) ? temp.toString() : "");
    sb.append(",");
    temp = acctBookDto.getPreExpDate();
    sb.append((temp != null) ? DateUtil.date2String((LocalDateTime) temp, "yyyyMMddHHmmss") : "");
    sb.append(",");
    temp = acctBookDto.getSeconds();
    sb.append((temp != null) ? temp.toString() : "");
    sb.append(",");
    temp = acctBookDto.getCreatedDate();
    sb.append((temp != null) ? DateUtil.date2String((LocalDateTime) temp, "yyyyMMddHHmmss") : "");
    if ("P".equals(acctBookDto.getAcctBookType()))
      sb = extendRechargeRecord(acctBookDto.getAcctBookId(), sb);
    return sb.toString();
  }

  private StringBuffer extendRechargeRecord (Long acctBookId, StringBuffer inSb) {
    StringBuffer sb = inSb;
    Object temp = null;
//    IPaymentQryDAO paymentQryDAO = (IPaymentQryDAO) DAOFactory.createModuleDAO("PaymentQry", "billing.fc.common.recordacctrecentopt",
//      JdbcUtil4BL.getDbBackService());
    RechargeRecentDto rechargeRecentDto = acctBookRepository.qryRechargePaymentInfo(acctBookId)
      .map(acctBookMapper::toRechargeRecentDto).orElse(null);
    if (null != rechargeRecentDto) {
      sb.append(",");
      temp = rechargeRecentDto.getTradeSn();
      sb.append((temp != null) ? temp.toString() : "");
      sb.append(",");
      temp = rechargeRecentDto.getTradeTime();
      sb.append((temp != null) ? DateUtil.date2String((LocalDateTime) temp, "yyyyMMddHHmmss") : "");
      sb.append(",");
      temp = rechargeRecentDto.getTradeMethod();
      sb.append((temp != null) ? temp.toString() : "");
      sb.append(",");
      temp = rechargeRecentDto.getTradeType();
      sb.append((temp != null) ? temp.toString() : "");
      sb.append(",");
      temp = rechargeRecentDto.getAccountCode();
      sb.append((temp != null) ? temp.toString() : "");
      sb.append(",");
      temp = rechargeRecentDto.getVcPin();
      sb.append((temp != null) ? temp.toString() : "");
      sb.append(",");
      temp = rechargeRecentDto.getAmount();
      sb.append((temp != null) ? temp.toString() : "");
      sb.append(",");
      temp = rechargeRecentDto.getExtendDays();
      sb.append((temp != null) ? temp.toString() : "");
    }
    return sb;
  }

  private String buildAcctOper (Map<String, Queue<String>> nodeMap) {
    StringBuilder acctOper = new StringBuilder();
    Queue<String> recordQueue = null;
    for (String acctBookType : this.acctBookTypeForRecentOper) {
      recordQueue = nodeMap.get(acctBookType);
      if (recordQueue != null) {
        if (!acctOper.isEmpty())
          acctOper.append(";");
        acctOper.append(acctBookType);
        acctOper.append("=");
        acctOper.append(recordQueueToString(recordQueue));
      }
    }
    return acctOper.toString();
  }

  private void removeRecentAcctOperRecordByAcctBookTypeAndId (AcctBookData acctBookDto, Map<String, Queue<String>> nodeMap) {
    Queue<String> recordQueue = nodeMap.get(acctBookDto.getAcctBookType());
    if (recordQueue != null) {
      String temp = null;
      for (String record : recordQueue) {
        temp = getAcctBookIdFieldFromRecord(record);
        if (temp.equals(acctBookDto.getAcctBookId().toString())) {
          String acctBookId = getAcctBookIdFieldFromRecord(recordQueue.peek());
          Long bottomAcctIdInQueue = Long.parseLong(acctBookId);
          recordQueue.remove(record);
          if (recordQueue.size() >= this.recordNumForRecentOper - 1)
            appendLastRecentValidRecord(acctBookDto, bottomAcctIdInQueue, recordQueue);
          break;
        }
      }
    }
  }

  private void appendLastRecentValidRecord (AcctBookData acctBookData, Long beforeAcctBookId, Queue<String> recordQueue) {
//    IRecordAcctRecentOperateDAO acctBookDAO = (IRecordAcctRecentOperateDAO) DAOFactory.createModuleDAO("RecordAcctRecentOperate", "billing.fc.common.recordacctrecentopt");
    acctBookRepository.selectLastRecentValidAcctBook(acctBookData.getAcctBookType(), acctBookData
      .getAcctId(), beforeAcctBookId).map(acctBookMapper::toAcctBookData).ifPresent(lastRecentAcctBookData -> recordQueue.offer(buildRecord(lastRecentAcctBookData)));
  }

  public String getAcctBookIdFieldFromRecord (String record) {
    String acctBookId = null;
    if (record != null) {
      int p = record.indexOf(",");
      if (p != -1)
        acctBookId = record.substring(0, p);
    }
    return acctBookId;
  }

  public LocalDateTime getCreatedDateFieldFromRecord (String record) {
    String createdDateStr = getFieldFromRecordBySeq(record, 8);
    LocalDateTime createdDate = null;
    if (createdDateStr != null && !createdDateStr.isEmpty())
      try {
        return DateUtil.string2SQLDate(createdDateStr);
      } catch (IllegalArgumentException ex) {
        logger.debug("The Created Date Field in Record is not an valid date String[" + createdDateStr + "]");
      }
    return createdDate;
  }

  public Long getChargeFieldFromRecord (String record) {
    String chargeStr = getFieldFromRecordBySeq(record, 5);
    Long charge = null;
    if (chargeStr != null && chargeStr.length() > 0)
      try {
        charge = Long.valueOf(Long.parseLong(chargeStr));
      } catch (NumberFormatException ex) {
        logger.debug("The Charge Field in Record is not an valid Number[" + chargeStr + "]");
      }
    return charge;
  }

  public String getFieldFromRecordBySeq (String record, int seq) {
    if (record == null || seq < 0 || seq > 8)
      return null;
    String[] fields = record.split(",");
    if (fields.length != 8)
      return null;
    return fields[seq - 1];
  }

  private PaymentDto getReversedPayment (Long paymentId) {
    if (paymentId == null)
      return null;
//    IRecordAcctRecentOperateDAO acctBookDAO = (IRecordAcctRecentOperateDAO) DAOFactory.createModuleDAO("RecordAcctRecentOperate", "billing.fc.common.recordacctrecentopt");
    PaymentDto reversedPaymentDto = null;
    PaymentDto currentPayment = paymentRepository.selectPayment(paymentId).map(paymentMapper::toPaymentDto).orElse(null);
    if (currentPayment != null && currentPayment.getReversedPaymentId() != null)
      reversedPaymentDto = paymentRepository.selectPayment(currentPayment.getReversedPaymentId()).map(paymentMapper::toPaymentDto).orElse(null);
    return reversedPaymentDto;
  }

  public AcctBookData[] getBenefitAcctBook4Payment (Long paymentId) {
    if (paymentId == null)
      return null;
    AcctBookData[] benefitAcctBooks = null;
//    IRecordAcctRecentOperateDAO acctBookDAO = (IRecordAcctRecentOperateDAO) DAOFactory.createModuleDAO("RecordAcctRecentOperate", "billing.fc.common.recordacctrecentopt");
    Long eventInstId = reAbInstRepository
      .selectEventInstIdByPaymentId(paymentId)
      .orElse(null);
    if (eventInstId != null)
      benefitAcctBooks = selectBenefitAcctBookList(eventInstId);
    return benefitAcctBooks;
  }

  public AcctBookData[] getOneOffFeeAcctBook4Payment (Long paymentId) {
    if (paymentId == null)
      return null;
    AcctBookData[] OneOffFeeAcctBooks;
//    IRecordAcctRecentOperateDAO acctBookDAO = (IRecordAcctRecentOperateDAO) DAOFactory.createModuleDAO("RecordAcctRecentOperate", "billing.fc.common.recordacctrecentopt");
    OneOffFeeAcctBooks = selectOneOffFeeAcctBookList(paymentId);
    return OneOffFeeAcctBooks;
  }

  public AcctBookData[] selectOneOffFeeAcctBookList (Long acctBookId) {
    List<AcctBookData> list =
      acctBookRepository.selectOneOffFeeAcctBookList(acctBookId);

    return list.toArray(new AcctBookData[0]);
  }

  public AcctBookData[] selectBenefitAcctBookList (Long eventInstId) {
    List<AcctBookData> list =
      acctBookRepository.selectBenefitAcctBookList(eventInstId);

    return list.toArray(new AcctBookData[0]);
  }

  private boolean removeReversedRecentAcctOper (AcctBookData acctBookDto, Map<String, Queue<String>> nodeMap) {
    boolean isReverse = false;
    PaymentDto reversedPayment = getReversedPayment(acctBookDto.getAcctBookId());
    if (reversedPayment != null) {
      isReverse = true;
      AcctBookData reverseDto = new AcctBookData();
      reverseDto.setAcctId(acctBookDto.getAcctId());
      reverseDto.setAcctBookType(acctBookDto.getAcctBookType());
      reverseDto.setAcctBookId(reversedPayment.getPaymentId());
      removeRecentAcctOperRecordByAcctBookTypeAndId(reverseDto, nodeMap);
      AcctBookData[] benefitAcctBooks = getBenefitAcctBook4Payment(reversedPayment.getPaymentId());
      if (benefitAcctBooks != null)
        for (AcctBookData dto : benefitAcctBooks)
          removeRecentAcctOperRecordByAcctBookTypeAndId(dto, nodeMap);
      AcctBookData[] oneoffFeeAcctBooks = getOneOffFeeAcctBook4Payment(reversedPayment.getPaymentId());
      if (oneoffFeeAcctBooks != null)
        for (AcctBookData dto : oneoffFeeAcctBooks)
          removeRecentAcctOperRecordByAcctBookTypeAndId(dto, nodeMap);
    }
    return isReverse;
  }
}
