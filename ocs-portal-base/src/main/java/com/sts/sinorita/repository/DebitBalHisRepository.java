package com.sts.sinorita.repository;

import com.sts.sinorita.entity.DebitBalHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface DebitBalHisRepository extends JpaRepository<DebitBalHis, Long> {
  @Modifying
  @Transactional
  @Query(value = """
    INSERT INTO DEBIT_BAL_HIS (
        ACCT_ID,
        LOAN_TYPE,
        BAL,
        SP_ID,
        COMMISSION_CHARGE,
        IS_COMM_CHARGE_NOT_RET,
        LAST_DEBIT_CHARGE,
        LAST_DEBIT_DATE,
        LAST_RET_CHARGE,
        LAST_RET_DATE,
        LAST_COMM_CHARGE,
        LAST_COMM_RET_CHARGE,
        LAST_DEBIT_BAL_ID,
        PRICE_PLAN_CODE,
        CREATED_DATE,
        LAST_BUT_N_DEBIT_INFO
    ) VALUES (
        :acctId,
        :loanType,
        :bal,
        :spId,
        :commissionCharge,
        :isCommChargeNotRet,
        :lastDebitCharge,
        :lastDebitDate,
        :lastRetCharge,
        :lastRetDate,
        :lastCommCharge,
        :lastCommRetCharge,
        :lastDebitBalId,
        :pricePlanCode,
        :createdDate,
        :lastButNDebitInfo
    )
    """, nativeQuery = true)
  int insertDebitBalHis (
    @Param("acctId") Long acctId,
    @Param("loanType") Long loanType,
    @Param("bal") Long bal,
    @Param("spId") Long spId,
    @Param("commissionCharge") Long commissionCharge,
    @Param("isCommChargeNotRet") String isCommChargeNotRet,
    @Param("lastDebitCharge") Long lastDebitCharge,
    @Param("lastDebitDate") LocalDateTime lastDebitDate,
    @Param("lastRetCharge") Long lastRetCharge,
    @Param("lastRetDate") LocalDateTime lastRetDate,
    @Param("lastCommCharge") Long lastCommCharge,
    @Param("lastCommRetCharge") Long lastCommRetCharge,
    @Param("lastDebitBalId") Long lastDebitBalId,
    @Param("pricePlanCode") String pricePlanCode,
    @Param("createdDate") LocalDateTime createdDate,
    @Param("lastButNDebitInfo") String lastButNDebitInfo
  );
}
