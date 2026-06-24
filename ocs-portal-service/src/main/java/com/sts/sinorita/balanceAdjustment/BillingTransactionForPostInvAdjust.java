package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.util.CommonUtil;

public class BillingTransactionForPostInvAdjust extends BillingTransactionRecord {
  // private static final long SUBS_EVENT_ID_POSTINVADJUST = 352L;

  private PostInvoicingAdjustDataBus dataBus;

  private AcctBookData acctBookData;

  private AcctItemDto acctItemDto;

  public void init (BillingBaseDataBus databus) {
    super.init(databus);
    this.subsEventId = 352L;
    this.acctBookData = databus.getAcctBookData();
    this.dataBus = (PostInvoicingAdjustDataBus) databus;
    this.matcher = new PostInvAdjustGlCodeMatcher();
    this.acctBookGlCodeCfgDto = this.matcher.matcherAcctBook(this.acctBookData.getAcctBookType(), this.acctBookData.getAcctResId().toString());
    this.feeRecorder = null;
  }

  public void recordBusiInfo () {
    AdjustItemDto[] adjustItemDtoArr = this.dataBus.getAdjustItemDtoArr();
    AcctItemDto[] newAcctItemList = this.dataBus.getNewAcctItemList();
    if (CommonUtil.isNotEmpty((Object[]) adjustItemDtoArr))
      for (AdjustItemDto adjustItem : adjustItemDtoArr) {
        this.acctItemDto = getAcctItemDto(adjustItem, newAcctItemList);
        if (this.acctItemDto != null) {
          this.busiGlCodeCfgDto = this.matcher.matcherBusiInfo(this.subsEventId, this.acctItemDto.getAcctItemTypeId().toString());
          storeBillingTransaction(prepare());
        }
      }
  }

  private AcctItemDto getAcctItemDto (AdjustItemDto adjustItem, AcctItemDto[] newAcctItemList) {
    AcctItemDto retAcctItemDto = null;
    if (CommonUtil.isEmpty((Object[]) newAcctItemList))
      return retAcctItemDto;
    for (AcctItemDto acctItemDto : newAcctItemList) {
      if (acctItemDto.getAcctItemId().equals(adjustItem.getRefAcctItemId())) {
        retAcctItemDto = acctItemDto;
        break;
      }
    }
    return retAcctItemDto;
  }

  public void recordAcctBook () {
    doRecordAcctBook(this.acctBookData);
  }

  protected BillingTransactionDto prepare () {
    BillingTransactionDto dict = new BillingTransactionDto();
    if (this.busiGlCodeCfgDto != null) {
//      dict.set("GL_CODE1", this.busiGlCodeCfgDto.getGlCode());
      dict.setGlCode1(this.busiGlCodeCfgDto.getGlCode());
//      dict.set("GL_DIRECTION1", this.busiGlCodeCfgDto.getGlDirection());
      dict.setGlDirection1(this.busiGlCodeCfgDto.getGlDirection());
//      dict.set("CHARGE1", this.acctItemDto.getCharge().longValue() * this.busiGlCodeCfgDto.getGlCoefficient().longValue());
      dict.setCharge1(this.acctItemDto.getCharge().longValue() * this.busiGlCodeCfgDto.getGlCoefficient().longValue());
    } else {
//      dict.set("CHARGE1", this.acctItemDto.getCharge());
      dict.setCharge1(this.acctItemDto.getCharge());
    }
//    dict.set("SUBS_EVENT_ID", this.subsEventId);
    dict.setSubsEventId(this.subsEventId);
//    dict.set("PARTNER_DATE", this.acctItemDto.getCreatedDate());
    dict.setPartnerDate(this.acctItemDto.getCreatedDate());
    return dict;
  }

  class PostInvAdjustGlCodeMatcher extends AbsGlCodeMatcher {
    public GlCodeCfgDto matcherBusiInfo (Long subsEventId, String busiId) {
      for (GlCodeCfgDto glCodeCfgDto : allGlCode) {
        String acctItemTypeIds = glCodeCfgDto.getAcctItemTypeId();
        if (CommonUtil.isInCommaText(acctItemTypeIds, busiId, ','))
          return glCodeCfgDto;
      }
      return null;
    }
  }
}
