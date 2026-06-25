package com.ocs.portal.repository;

import com.ocs.portal.entity.DebitNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitNoteRepository extends JpaRepository<DebitNote, Long> {
  @Modifying
  @Query(value = "UPDATE DEBIT_NOTE SET PAYMENT_CHARGE = PAYMENT_CHARGE + :charge STATE = 'B' WHERE NOTE_ID = :noteId", nativeQuery = true)
  void updateDebitNote (@Param("noteId") Long[] noteId, @Param("charge") Long[] charge);

}
