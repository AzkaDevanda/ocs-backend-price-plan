package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.util.CommonUtil;

import java.util.HashMap;
import java.util.Map;

public class BillingTransactionForBalAdjust extends BillingTransactionRecord {
  // private static final long SUBS_EVENT_ID_ADJUST = 352L;

  private BalAdjustDataBus dataBus;

  private AcctBookData acctBookData;

  private AdjustDto adjustDto;

  private AdjustDto[] adjustDtoList;

  private AcctBookData[] acctBookDataList;

  private Map<Long, AcctBookData> acctBookDataMap;

  public void init (BillingBaseDataBus databus) {
    super.init(databus);
    this.subsEventId = Long.valueOf(352L);
    this.dataBus = (BalAdjustDataBus) databus;
    this.acctBookData = this.dataBus.getAcctBookData();
    this.adjustDto = this.dataBus.getAdjustDto();
    this.adjustDtoList = this.dataBus.getAdjustDtoList();
    this.acctBookDataList = this.dataBus.getAcctBookDataList();
    this.matcher = new BalAdjustMatcher();
    this.busiGlCodeCfgDto = this.matcher.matcherBusiInfo(this.subsEventId, this.adjustDto.getAdjustReasonId().toString());
    this.acctBookGlCodeCfgDto = this.matcher.matcherAcctBook(this.acctBookData.getAcctBookType(), this.acctBookData.getAcctResId().toString());
    this.feeRecorder = null;
    this.acctBookDataMap = new HashMap<>();
    if (CommonUtil.isEmpty((Object[]) this.acctBookDataList))
      return;
    for (AcctBookData data : this.acctBookDataList)
      this.acctBookDataMap.put(data.getAcctBookId(), data);
  }

  @Override
  public void recordAcctBook () {
    if (CommonUtil.isEmpty((Object[]) this.acctBookDataList)) {
      doRecordAcctBook(this.acctBookData);
      return;
    }
    for (AcctBookData tmpAcctBookData : this.acctBookDataList) {
      this.acctBookGlCodeCfgDto = this.matcher.matcherAcctBook(tmpAcctBookData.getAcctBookType(), tmpAcctBookData.getAcctResId().toString());
      doRecordAcctBook(tmpAcctBookData);
    }
  }

  public void recordBusiInfo () {
    if (CommonUtil.isEmpty((Object[]) this.adjustDtoList)) {
      storeBillingTransaction(prepare());
      return;
    }
    for (AdjustDto adjustDto : this.adjustDtoList) {
      this.adjustDto = adjustDto;
      this.busiGlCodeCfgDto = this.matcher.matcherBusiInfo(this.subsEventId, adjustDto.getAdjustReasonId().toString());
      this.acctBookData = this.acctBookDataMap.get(adjustDto.getAdjustId());
      if (this.acctBookData != null)
        storeBillingTransaction(prepare());
    }
  }

  @Override
  protected BillingTransactionDto prepare () {
    BillingTransactionDto dict = new BillingTransactionDto();
//    dict.set("SUBS_EVENT_ID", this.subsEventId);
    dict.setSubsEventId(this.subsEventId);
//    dict.set("CONTACT_CHANNEL_ID", this.acctBookData.getContactChannelId());
    dict.setContactChannelId(this.acctBookData.getContactChannelId());
//    dict.set("REASON_ID", this.adjustDto.getAdjustReasonId());
    dict.setReasonId(this.adjustDto.getAdjustReasonId());
    if (this.busiGlCodeCfgDto != null) {
//      dict.set("CHARGE1", this.acctBookData.getCharge() * this.busiGlCodeCfgDto.getGlCoefficient());
      dict.setCharge1(this.acctBookData.getCharge() * this.busiGlCodeCfgDto.getGlCoefficient());
//      dict.set("GL_CODE1", this.busiGlCodeCfgDto.getGlCode());
      dict.setGlCode1(this.busiGlCodeCfgDto.getGlCode());
//      dict.set("GL_DIRECTION1", this.busiGlCodeCfgDto.getGlDirection());
      dict.setGlDirection1(this.busiGlCodeCfgDto.getGlDirection());
    } else {
//      dict.set("CHARGE1", this.acctBookData.getCharge());
      dict.setCharge1(this.acctBookData.getCharge());
    }
//    dict.set("PARTNER_DATE", this.acctBookData.getCreatedDate());
    dict.setPartnerDate(this.acctBookData.getCreatedDate());
    return null;
  }

  static class BalAdjustMatcher extends AbsGlCodeMatcher {
    public GlCodeCfgDto matcherBusiInfo (Long subsEventId, String busiId) {
      for (int i = 0; i < allGlCode.length; i++) {
        GlCodeCfgDto glCodeCfgDto = allGlCode[i];
        String reasons = glCodeCfgDto.getReasonId();
        if (CommonUtil.isInCommaText(reasons, busiId, ','))
          return glCodeCfgDto;
      }
      return null;
    }

    @Override
    public GlCodeCfgDto matcherAcctBook (String param1String1, String param1String2) {
      return null;
    }
  }
}
