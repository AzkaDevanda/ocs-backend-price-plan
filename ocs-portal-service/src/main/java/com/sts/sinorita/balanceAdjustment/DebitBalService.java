package com.sts.sinorita.balanceAdjustment;

import com.sts.sinorita.dto.request.balanceAdjustment.LoanRepayParam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DebitBalService {
  @PersistenceContext
  private EntityManager em;

  @Transactional
  public void updateClearDebitBal (Long acctId, LoanRepayParam param) {

    StringBuilder sql = new StringBuilder("""
          UPDATE DEBIT_BAL
          SET BAL = BAL + :REPAY_AMOUNT,
              IS_COMM_CHARGE_NOT_RET = :IS_COMM_CHARGE_NOT_RET,
              LAST_RET_CHARGE = LAST_RET_CHARGE + :REPAY_AMOUNT,
              LAST_COMM_RET_CHARGE = LAST_COMM_RET_CHARGE + :COMM_REPAY_AMOUNT
      """);

    // optional dynamic fields
    if (param.getLastRetDate() != null) {
      sql.append(", LAST_RET_DATE = :LAST_RET_DATE");
    }
    if (param.getLastCommCharge() != null) {
      sql.append(", LAST_COMM_CHARGE = :LAST_COMM_CHARGE");
    }

    sql.append("""
          WHERE ACCT_ID = :ACCT_ID
            AND LOAN_TYPE = :LOAN_TYPE
      """);

    Query query = em.createNativeQuery(sql.toString());

    // required params
    query.setParameter("REPAY_AMOUNT", -1L * param.getLoanRepayAmount());
    query.setParameter("COMM_REPAY_AMOUNT", -1L * param.getCommRepayAmount());
    query.setParameter("ACCT_ID", acctId);

    Long loanType = param.getDebitItemDto().getLoanType();
    query.setParameter("LOAN_TYPE", loanType != null ? loanType : 1L);

    // IS_COMM_CHARGE_NOT_RET
    String flag = param.getIsCommissionCleared() ? "N" : "Y";
    query.setParameter("IS_COMM_CHARGE_NOT_RET", flag);

    // optional values
    if (param.getLastRetDate() != null) {
      query.setParameter("LAST_RET_DATE", param.getLastRetDate());
    }
    if (param.getLastCommCharge() != null) {
      query.setParameter("LAST_COMM_CHARGE", param.getLastCommCharge());
    }

    query.executeUpdate();
  }
}
