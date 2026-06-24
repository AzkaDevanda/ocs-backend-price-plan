package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.IBillingTransactionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RecordAndMacherFactory {
  private static final Logger logger = LoggerFactory.getLogger(RecordAndMacherFactory.class);

  private static final Map<String, Class<?>> classMap = new HashMap<>();

  static {
    classMap.put("H", BillingTransactionForBalAdjust.class);
    classMap.put("J", BillingTransactionForLoan.class);
    classMap.put("P", BillingTransactionForPayment.class);
    classMap.put("A", BillingTransactionForPostInvAdjust.class);
    classMap.put("TRANSFER_IN_OUT", BillingTransactionForTransOutIn.class);
    classMap.put("BAL_CHANGE_4_FEE", BillingTransactionForBalChangeFee.class);
    classMap.put("", BillingTransactionForBalChangeFee.class);
  }

  public static IBillingTransactionRecord getBillingTransactionRecord (String classKey) {
    try {
      Class<?> billingTransactionClass = classMap.get(classKey);
      if (billingTransactionClass != null)
        return (IBillingTransactionRecord) billingTransactionClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      logger.error("create class BillingTransaction failed.", e);
    }
    return null;
  }
}
