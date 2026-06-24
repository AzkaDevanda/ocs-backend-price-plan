package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.BillEx;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillExService {

  @PersistenceContext
  private final EntityManager entityManager;

  @Transactional(readOnly = true)
  public BillEx selectBillExByBillId (Long billId) {
    String sql = """
            SELECT T.BILL_ID, T.SUBS_EVENT_CHARGE, T.PAY_IN_TIME FROM BILL_EX T WHERE T.BILL_ID = ?1
            """;
    Query query = entityManager.createNativeQuery(sql);
    query.setParameter(1, billId);

    List<Object[]> results = query.getResultList();
    if (results.isEmpty()) {
      return null;
    }

    Object[] row = results.get(0);

    BillEx dto = new BillEx();
    dto.setBillId(row[0] != null ? ((Number) row[0]).longValue() : null);
    dto.setSubsEventCharge(row[1] != null ? ((Number) row[1]).longValue() : null);
    dto.setPayInTime((String) row[2]);

    return dto;
  }
}
