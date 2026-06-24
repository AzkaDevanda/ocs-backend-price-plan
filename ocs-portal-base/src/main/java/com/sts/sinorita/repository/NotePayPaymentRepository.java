package com.sts.sinorita.repository;

import com.sts.sinorita.entity.NotePayPayment;
import com.sts.sinorita.entity.NotePayPaymentId;
import com.sts.sinorita.projection.balanceAdjustment.SelectNotePayPaymentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotePayPaymentRepository extends JpaRepository<NotePayPayment, NotePayPaymentId> {

  @Query(value = "SELECT NOTE_ID AS noteId, -CHARGE AS charge FROM NOTE_PAY_PAYMENT WHERE PAYMENT_ID = :paymentId", nativeQuery = true)
  List<SelectNotePayPaymentProjection> selectNotePayPayment (@Param("paymentId") Long paymentId);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO NOTE_PAY_PAYMENT ( PAYMENT_ID, NOTE_ID, CHARGE, CREATED_DATE, SP_ID, PRE_BALANCE ) VALUES ( :paymentId, :noteId, :charge, SYSDATE, :spId, (SELECT - (T.CHARGE + T.PAYMENT_CHARGE) FROM DEBIT_NOTE T WHERE T.NOTE_ID = :noteId) )", nativeQuery = true)
  void insertNotePayPayment (@Param("paymentId") Long paymentId, @Param("spId") Long spId, @Param("noteId") Long[] noteId, @Param("charge") Long[] charge);

}
