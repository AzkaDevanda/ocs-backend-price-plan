package com.sts.sinorita.balanceAdjustment.invoke.add;

import com.sts.sinorita.balanceAdjustment.EventCdrService;
import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.dto.response.balanceAdjustment.AcctResDto;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.ConfigItemRepository;
import com.sts.sinorita.util.CommonUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@Service
public class BalAdjustCdrRecordService {
  private final Logger logger = LoggerFactory.getLogger(BalAdjustCdrRecordService.class);

  private SubsDto subs;

  private String balAdjustTriggerSource;

  private Long balAdjustAmount;

  private AcctDto acct;

  private AdjustDto adjustDto;

  private AcctResDto acctResDto;

  private AcctBookData acctBookData;

  private Date dateNow;

  private String partnerSn;

  private Map<String, Object> extMap;

  @Autowired
  private ConfigItemRepository configItemRepository;
  @Autowired
  private EventCdrService eventCdrService;

  public void invoke () {
      logger.info("balAdjustCdrRecordService invoked.");
    try {
//      if (!PublicCheck4Fiji.isTELKOMCELProject().booleanValue()) {
//        logger.debug("No need to record cdr for this project.");
//        return;
//      }
      Optional<ConfigItemParamProjection> findAcctResIdStr = configItemRepository.findConfigItem("ACCT", "ACCOUNT_PUBLIC",
        "IS_NEED_RECORD_CDR_ACCT_RES");
//      String acctResIdStr = ConfigItemCache.instance().getString("ACCT.ACCOUNT_PUBLIC.IS_NEED_RECORD_CDR_ACCT_RES");
      String acctResIdStr = findAcctResIdStr.map(ConfigItemParamProjection::getDefaultValue).orElse(null);
      if (!CommonUtil.isInCommaText(acctResIdStr, String.valueOf(this.acctBookData.getAcctResId()), ',')) {
        logger.debug("No need to record cdr for this acctResId [{}].", this.acctBookData.getAcctResId());
        return;
      }
      CdrDict cdrDict = new CdrDict();
//      cdrDict.serviceName = "EventCdrService";
      CdrDataDict cdrDataDict = new CdrDataDict();
      if (this.subs == null)
        this.subs = this.acct.getAllSubsList()[0];
      if (this.subs != null) {
//        cdrDict.set("ACC_NBR", this.subs.getAccNbr());
        cdrDict.setAccNbr(this.subs.getAccNbr());
//        cdrDict.set("SUBS_ID", this.subs.getSubsId());
        cdrDict.setSubsId(this.subs.getSubsId());
//        cdrDict.set("PREFIX", this.subs.getPrefix());
        cdrDict.setPrefix(this.subs.getPrefix());
//        cdrDataDict.set("SUBS_ID", this.subs.getSubsId());
        cdrDataDict.setSubsId(this.subs.getSubsId());
      } else {
        SubsDto[] subsList = this.acct.getAllSubsList();
        if (CommonUtil.isNotEmpty((Object[]) subsList)) {
//          cdrDict.set("ACC_NBR", subsList[0].getAccNbr());
          cdrDict.setAccNbr(subsList[0].getAccNbr());
//          cdrDict.set("SUBS_ID", subsList[0].getSubsId());
          cdrDict.setSubsId(subsList[0].getSubsId());
//          cdrDict.set("PREFIX", subsList[0].getPrefix());
          cdrDict.setPrefix(subsList[0].getPrefix());
//          cdrDataDict.set("SUBS_ID", subsList[0].getSubsId());
          cdrDataDict.setSubsId(this.subs.getSubsId());

        }
      }
//      cdrDataDict.set("ACCT_BOOK_ID", this.acctBookData.getAcctBookId());
      cdrDataDict.setAcctBookId(this.acctBookData.getAcctBookId());
//      cdrDataDict.set("CHARGE", -this.acctBookData.getCharge().longValue());
      cdrDataDict.setCharge(this.acctBookData.getCharge());
//      cdrDataDict.set("CHANNEL", this.acctBookData.getContactChannelId());
      cdrDataDict.setChannel(this.acctBookData.getContactChannelId());
//      cdrDataDict.set("TRANSACTIONSN", this.partnerSn);
      cdrDataDict.setTransaction(this.partnerSn);
//      cdrDataDict.set("CREATE_DATE", DateUtil.date2String(this.acctBookData.getCreatedDate(), "yyyy-MM-dd HH:mm:ss"));
      cdrDataDict.setCreatedDate(this.acctBookData.getCreatedDate());
//      cdrDict.set("CDR_DATA", cdrDataDict);
      cdrDict.setCdrDataDict(cdrDataDict);
//      cdrDict.set("CDR_SERVICE_EVENT_CODE", "TELKOMCEL_BALANCE_ADJUST_CDR");
      cdrDict.setCdrServiceEventCode("TELKOMCEL_BALANCE_ADJUST_CDR");
//      ServiceFlow.callService(cdrDict);
      eventCdrService.perform(cdrDict);

    } catch (Exception e) {
      logger.warn("Fail to record cdr.", e);
//      ExceptionHandler.publish("BL-S-ACT-00110", 0);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageService.getMessage("BL-S-ACT-00110"));
    }
    logger.info("balAdjustCdrRecordService passed.");
  }

}
