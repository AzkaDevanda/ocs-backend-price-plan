package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.response.accountconfig.BillingCycleDto;
import com.sts.sinorita.repository.BillingCycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillCycleService {

  private final BillingCycleRepository billingCycleRepository;

  public Long qryCurrentBillingCycleId (Long billCycleId) {
    Long billingCycleId = null;
    BillingCycleDto billingCycleDto = selectCurBillingCycleByBillingCycleTypeId(billCycleId);
    if (billingCycleDto != null)
      billingCycleId = billingCycleDto.getBillingCycleId().longValue();
    return billingCycleId;
  }

  public BillingCycleDto selectCurBillingCycleByBillingCycleTypeId (Long billingCycleTypeId) {
    List<Object[]> result = billingCycleRepository.selectCurBillingCycleByBillingCycleTypeId(billingCycleTypeId);

    if (result.isEmpty()) {
      return null;
    }

    Object[] row = result.get(0);
    return new BillingCycleDto(
            ((Number) row[0]).intValue(),
            ((Number) row[1]).intValue(),
            ((String) row[4]).charAt(0),
            (Date) row[2],
            (Date) row[3],
            (Date) row[5],
            (Date) row[7],
            (Date) row[10],
            null,
            (Date) row[8],
            (Date) row[9],
            null,
            ((Number) row[6]).intValue()
    );
  }

}
