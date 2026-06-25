package com.ocs.portal.dto.request.balanceAdjustment;

import java.util.List;

public interface IBillingTransactionRecord {
  void init(BillingBaseDataBus paramBillingBaseDataBus);

  void recordTransaction();

  interface IBillingTransactionForFee {
    boolean hasFee();

    List<BillingTransactionDto> prepairFeeItem();
  }

  interface IGlCodeMatcher {
    GlCodeCfgDto matcherBusiInfo(Long param1Long, String param1String);

    GlCodeCfgDto matcherAcctBook(String param1String1, String param1String2);
  }
}
