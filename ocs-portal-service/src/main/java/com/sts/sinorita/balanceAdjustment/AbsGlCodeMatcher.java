package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.GlCodeCfgDto;
import com.sts.sinorita.dto.request.balanceAdjustment.IBillingTransactionRecord;
import com.sts.sinorita.projection.balanceAdjustment.QryGlCodeCfgDtoProjection;
import com.sts.sinorita.repository.GlCodeCgfRepository;
import com.sts.sinorita.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class AbsGlCodeMatcher implements IBillingTransactionRecord.IGlCodeMatcher {
  private static final Logger logger = LoggerFactory.getLogger(AbsGlCodeMatcher.class);
  public static AbsGlCodeMatcher nonBusiMatcher = new AbsGlCodeMatcher() {
    public GlCodeCfgDto matcherBusiInfo (Long subsEventId, String busiId) {
      return null;
    }
  };
  protected static GlCodeCfgDto[] allGlCode;
  @Autowired
  private static GlCodeCgfRepository glCodeCgfRepository;

  static {
   try {
    allGlCode = loadAllGlCodeCfg();
   }
   catch (Exception e) {
     allGlCode = new GlCodeCfgDto[0];
     logger.warn("select glCode error", (Throwable) e);
   }
  }

  private static GlCodeCfgDto[] loadAllGlCodeCfg () {
    GlCodeCfgDto[] dtoArray = qryGlCodeCfgDto();
    if (CommonUtil.isEmpty((Object[]) dtoArray))
      return new GlCodeCfgDto[0];
    return dtoArray;
  }

  public static AbsGlCodeMatcher getPaymentMatcher () {
    return new PaymentMatcher();
  }

  public static GlCodeCfgDto[] qryGlCodeCfgDto () {
    List<QryGlCodeCfgDtoProjection> list = glCodeCgfRepository.qryGlCodeCfgDto();

    if (list == null || list.isEmpty()) {
      return null;
    }

    return list.toArray(new GlCodeCfgDto[0]);
  }

  public GlCodeCfgDto matcherAcctBook (String acctBookType, String acctResId) {
    for (int i = 0; i < allGlCode.length; i++) {
      GlCodeCfgDto glCodeCfgDto = allGlCode[i];
      String acctBookTypes = glCodeCfgDto.getAcctBookType();
      String acctResIds = glCodeCfgDto.getAcctResId();
      if (CommonUtil.isInCommaText(acctBookTypes, acctBookType, ',') &&
        CommonUtil.isInCommaText(acctResIds, acctResId, ','))
        return glCodeCfgDto;
    }
    return null;
  }
}
