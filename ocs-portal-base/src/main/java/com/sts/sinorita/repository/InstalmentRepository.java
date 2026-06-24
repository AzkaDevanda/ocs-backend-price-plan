package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Instalment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InstalmentRepository extends JpaRepository<Instalment, Long> {
  @Transactional
  @Modifying
  @Query(value = "UPDATE INSTALLMENT SET PAIED_AMOUNT = NVL(PAIED_AMOUNT, 0) + :PAIED_AMOUNT WHERE INSTALLMENT_ID = :INSTALLMENT_ID AND (:SP_ID IS NULL OR NVL(SP_ID,0) = :SP_ID)", nativeQuery = true)
  void updateInstallmentPaiedAmountById (@Param("INSTALLMENT_ID") Long installmentId, @Param("PAIED_AMOUNT") Long paiedAmount, @Param("SP_ID") Long spId);

  @Modifying
  @Transactional
  @Query(
    value = "INSERT INTO INSTALMENT (INSTLMENT_ID, ACCT_ITEM_ID) VALUES (:instalmentId, :acctItemId)", nativeQuery = true)
  void insertInstalment (@Param("instalmentId") Long instalmentId, @Param("acctItemId") Long acctItemId);

}
