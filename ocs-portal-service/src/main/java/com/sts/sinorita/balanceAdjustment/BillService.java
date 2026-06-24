package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.BillEx;
import com.sts.sinorita.entity.BillEntity;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import com.sts.sinorita.repository.BillRepository;
import com.sts.sinorita.repository.ConfigItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillService {
  private final BillExService billExService;
  private final BillRepository billRepository;
  private final ConfigItemRepository configItemRepository;

  public BillEntity qryBillInfobyAcctId (Long acctId) {
    BillEntity bill = billRepository.selectBillByAcctId(acctId);
    if (bill != null) {
      Optional<ConfigItemParamProjection> findIsWriteBillEx = configItemRepository.findConfigItem("ACCT", "COMMON", "IS_WRITE_BILL_EX_WHILE_BAL_DEDUCT");
      String isWriteBillEx = findIsWriteBillEx
              .map(ConfigItemParamProjection::getDefaultValue)
              .orElse("");
      if ("Y".equals(isWriteBillEx)) {
        BillEx billEx = billExService.selectBillExByBillId(bill.getBillId());
        if (billEx == null) {
          billEx = new BillEx();
          billEx.setBillId(bill.getBillId());
          billEx.setSubsEventCharge(Long.valueOf(0L));
        }
        bill.setBillEx(billEx);
      }
    }
    return bill;
  }
}
