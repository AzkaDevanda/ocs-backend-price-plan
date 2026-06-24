package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.*;
import com.sts.sinorita.util.AssertUtil;
import com.sts.sinorita.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class BillingTransactionForDeposit implements IBillingTransactionRecord.IBillingTransactionForFee {
  List<? extends ReCcInstData> reCcInstDataList;

  IBillingTransactionRecord.IGlCodeMatcher matcher;

  public BillingTransactionForDeposit (List<? extends ReCcInstData> reCcInstDataList) {
    this.reCcInstDataList = reCcInstDataList;
    this.matcher = new DepositMatcher();
  }

  public boolean hasFee () {
    return true;
  }

  public List<BillingTransactionDto> prepairFeeItem () {
    List<BillingTransactionDto> ret = new ArrayList<>();
    for (ReCcInstData reCcInstBLData : this.reCcInstDataList) {
      if (reCcInstBLData.getQryFeeParam() == null)
        continue;
      Long subsEventId = reCcInstBLData.getQryFeeParam().getSubsEventId();
      AssertUtil.isNotNull(subsEventId, "SUBS_EVENT_ID is null");
      DepositInstData[] depositInstDataList = reCcInstBLData.getDepositInstDataList();
      if (CommonUtil.isEmpty((Object[]) depositInstDataList))
        continue;
      ret.addAll(processDepositInstDataList(depositInstDataList, subsEventId));
    }
    return ret;
  }

  private List<BillingTransactionDto> processDepositInstDataList (DepositInstData[] depositInstDataList,
                                                                  Long subsEventId) {
    List<BillingTransactionDto> ret = new ArrayList<>();
    BillingTransactionDto dict = null;
    GlCodeCfgDto depositGlCode = null;
    for (DepositInstData depositInstData : depositInstDataList) {
      Long depositTypeId = depositInstData.getDepositTypeId();
      if (depositTypeId != null)
        depositGlCode = this.matcher.matcherBusiInfo(subsEventId, depositTypeId.toString());
      dict = new BillingTransactionDto();
      // dict.set("EVENT_INST_ID", depositInstData.getEventInstId());
      dict.setEventInstId(depositInstData.getEventInstId());
      if (depositGlCode != null) {
        // dict.set("GL_CODE1", depositGlCode.getGlCode());
        dict.setGlCode1(depositGlCode.getGlCode());
//        dict.set("GL_DIRECTION1", depositGlCode.getGlDirection());
        dict.setGlDirection1(depositGlCode.getGlDirection());
//        dict.set("CHARGE1", depositInstData.getCharge().longValue() * depositGlCode.getGlCoefficient().longValue());
        dict.setCharge1(depositInstData.getCharge() * depositGlCode.getGlCoefficient());
      } else {
//        dict.set("CHARGE1", depositInstData.getCharge());
        dict.setCharge1(depositInstData.getCharge());
      }
//      dict.set("SUBS_EVENT_ID", subsEventId);
      dict.setSubsEventId(subsEventId);
//      dict.set("PARTNER_DATE", depositInstData.getStateDate());
      dict.setPartnerDate(depositInstData.getStateDate());
      ret.add(dict);
    }
    return ret;
  }

  class DepositMatcher extends AbsGlCodeMatcher {
    public GlCodeCfgDto matcherBusiInfo (Long subsEventId, String busiId) {
      for (int i = 0; i < allGlCode.length; i++) {
        GlCodeCfgDto glCodeCfgDto = allGlCode[i];
        String depositTypeIds = glCodeCfgDto.getDepositTypeId();
        if (CommonUtil.isInCommaText(depositTypeIds, busiId, ','))
          return glCodeCfgDto;
      }
      return null;
    }
  }

}
