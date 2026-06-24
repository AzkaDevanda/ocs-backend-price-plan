package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.GlCodeCfgDto;
import com.sts.sinorita.util.CommonUtil;

public class PaymentMatcher extends AbsGlCodeMatcher {
  public GlCodeCfgDto matcherBusiInfo (Long subsEventId, String busiId) {
    for (int i = 0; i < allGlCode.length; i++) {
      GlCodeCfgDto glCodeCfgDto = allGlCode[i];
      String paymentMethodIds = glCodeCfgDto.getPaymentMethodId();
      if (CommonUtil.isInCommaText(paymentMethodIds, busiId, ','))
        return glCodeCfgDto;
    }
    return null;
  }
}
