package com.ocs.portal.repository;

import com.ocs.portal.entity.Payment;
import com.ocs.portal.entity.embeddable.PaymentId;
import com.ocs.portal.projection.balanceAdjustment.SelectPaymentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO PAYMENT (PAYMENT_ID, REVERSED_PAYMENT_ID, PAYMENT_METHOD_ID, VOUCHER, SUBMIT_AMOUNT, RETURN_AMOUNT, REF_ATTR, BANK_ID, CHECK_NBR, CHECK_OWNER_NAME, CHECK_ISSUE_DATE, CHECK_EXP_DATE, SCRATCH_CARD_PWD, PREFIX, ACC_NBR, PAYMENT_DATE, SP_ID, ORI_ACCT_RES_ID, DEST_ACCT_RES_ID, DEST_AMOUNT, REFUND_REASON_ID, OVERPAY_AMOUNT, RECEIPT_NUM) VALUES (:paymentId, :reversedPaymentId, :paymentMethodId, :voucher, :submitAmount, :returnAmount, :refAttr, :bankId, :checkNbr, :checkOwnerName, :checkIssueDate, :checkExpDate, :scratchCardPwd, :prefix, :accNbr, :paymentDate, :spId, :oriAcctResId, :destAcctResId, :destAmount, :refundReasonId, :overpayAmount, :receiptNum)", nativeQuery = true)
  void paymentStore (@Param("paymentId") Long paymentId, @Param("reversedPaymentId") Long reversedPaymentId, @Param("paymentMethodId") Long paymentMethodId, @Param("voucher") String voucher, @Param("submitAmount") Long submitAmount, @Param("returnAmount") Long returnAmount, @Param("refAttr") String refAttr, @Param("bankId") Long bankId, @Param("checkNbr") String checkNbr, @Param("checkOwnerName") String checkOwnerName, @Param("checkIssueDate") LocalDateTime checkIssueDate, @Param("checkExpDate") LocalDateTime checkExpDate, @Param("scratchCardPwd") String scratchCardPwd, @Param("prefix") String prefix, @Param("accNbr") String accNbr, @Param("paymentDate") LocalDateTime paymentDate, @Param("spId") Long spId, @Param("oriAcctResId") Long oriAcctResId, @Param("destAcctResId") Long destAcctResId, @Param("destAmount") Long destAmount, @Param("refundReasonId") Long refundReasonId, @Param("overpayAmount") Long overpayAmount, @Param("receiptNum") String receiptNum);

  @Transactional
  @Modifying
  @Query(value = "UPDATE PAYMENT T SET T.REVERSED_BY_PAYMENT_ID = :reversePaymentId WHERE T.PAYMENT_ID = :oldPaymentId", nativeQuery = true)
  void updatePaymentReverseRela (@Param("oldPaymentId") Long oldPaymentId, @Param("reversePaymentId") Long reversePaymentId);

  @Query(value = "SELECT PAYMENT_ID AS paymentId, REVERSED_PAYMENT_ID AS reversedPaymentId, PAYMENT_METHOD_ID AS paymentMethodId, VOUCHER AS voucher, SUBMIT_AMOUNT AS submitAmount, RETURN_AMOUNT AS returnAmount, REF_ATTR AS refAttr, BANK_ID AS bankId, CHECK_NBR AS checkNbr, CHECK_OWNER_NAME AS checkOwnerName, CHECK_ISSUE_DATE AS checkIssueDate, CHECK_EXP_DATE AS checkExpDate, SCRATCH_CARD_PWD AS scratchCardPwd, PREFIX AS prefix, ACC_NBR AS accNbr, PAYMENT_DATE AS paymentDate, SP_ID AS spId, ORI_ACCT_RES_ID AS oriAcctResId, DEST_ACCT_RES_ID AS destAcctResId, DEST_AMOUNT AS destAmount FROM PAYMENT WHERE PAYMENT_ID = :paymentId", nativeQuery = true)
  Optional<SelectPaymentProjection> selectPayment (@Param("paymentId") Long paymentId);
}
