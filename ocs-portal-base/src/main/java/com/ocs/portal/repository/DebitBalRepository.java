package com.ocs.portal.repository;

import com.ocs.portal.entity.DebitBal;
import com.ocs.portal.entity.DebitBalId;
import com.ocs.portal.projection.balanceAdjustment.SelectDebitBalProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DebitBalRepository extends JpaRepository<DebitBal, DebitBalId> {

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO DEBIT_BAL( ACCT_ID, LOAN_TYPE, BAL, SP_ID, COMMISSION_CHARGE, IS_COMM_CHARGE_NOT_RET, LAST_DEBIT_CHARGE, LAST_DEBIT_DATE, LAST_RET_CHARGE, LAST_COMM_CHARGE, LAST_COMM_RET_CHARGE, LAST_DEBIT_BAL_ID, PRICE_PLAN_CODE ) VALUES ( :acctId, :loanType, :bal, :spId, :commissionCharge, :isCommChargeNotRet, :lastDebitCharge, :lastDebitDate, :lastRetCharge, :lastCommCharge, :lastCommRetCharge, :lastDebitBalId, :pricePlanCode )", nativeQuery = true)
  void insertDebitBal (@Param("acctId") Long acctId, @Param("loanType") Long loanType, @Param("bal") Long bal, @Param("spId") Long spId, @Param("commissionCharge") Long commissionCharge, @Param("isCommChargeNotRet") String isCommChargeNotRet, @Param("lastDebitCharge") Long lastDebitCharge, @Param("lastDebitDate") LocalDateTime lastDebitDate, @Param("lastRetCharge") Long lastRetCharge, @Param("lastCommCharge") Long lastCommCharge, @Param("lastCommRetCharge") Long lastCommRetCharge, @Param("lastDebitBalId") Long lastDebitBalId, @Param("pricePlanCode") String pricePlanCode);

  @Query(value = "SELECT ACCT_ID, BAL, SP_ID, COMMISSION_CHARGE, IS_COMM_CHARGE_NOT_RET, LAST_DEBIT_CHARGE, LOAN_TYPE, LAST_DEBIT_DATE, LAST_RET_CHARGE, LAST_RET_DATE, LAST_COMM_CHARGE, LAST_COMM_RET_CHARGE, LAST_DEBIT_BAL_ID, PRICE_PLAN_CODE, LAST_BUT_N_DEBIT_INFO FROM DEBIT_BAL WHERE ACCT_ID = :acctId AND LOAN_TYPE = :loanType", nativeQuery = true)
  Optional<SelectDebitBalProjection> selectDebitBal (@Param("acctId") Long acctId, @Param("loanType") Long loanType);

  @Transactional
  @Modifying
  @Query(
    value = """
      UPDATE DEBIT_BAL 
      SET BAL = BAL + :loanAmount, 
          IS_COMM_CHARGE_NOT_RET = :isCommNotRet, 
          LAST_DEBIT_CHARGE = :loanAmount, 
          LAST_DEBIT_DATE = :loanDate, 
          LAST_RET_CHARGE = 0, 
          LAST_COMM_CHARGE = NULL, 
          LAST_COMM_RET_CHARGE = 0, 
          LAST_RET_DATE = NULL, 
          LAST_DEBIT_BAL_ID = :lastDebitBalId 
      WHERE ACCT_ID = :acctId 
        AND LOAN_TYPE = :loanType
      """,
    nativeQuery = true
  )
  void updateDebitBal (
    @Param("acctId") Long acctId,
    @Param("loanType") Long loanType,
    @Param("loanAmount") Long loanAmount,
    @Param("loanDate") LocalDateTime loanDate,
    @Param("lastDebitBalId") Long lastDebitBalId,
    @Param("isCommNotRet") String isCommNotRet
  );

  @Transactional
  @Modifying
  @Query(
    value = "UPDATE DEBIT_BAL " +
      "SET PRICE_PLAN_CODE = :pricePlanCode " +
      "WHERE ACCT_ID = :acctId " +
      "  AND LOAN_TYPE = :loanType",
    nativeQuery = true
  )
  void updateDebitBalPricePlanCode (
    @Param("acctId") Long acctId,
    @Param("loanType") Long loanType,
    @Param("pricePlanCode") String pricePlanCode
  );

}
