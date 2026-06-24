package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BillEx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BillExRepository extends JpaRepository<BillEx, Long> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO BILL_EX ( BILL_ID, SUBS_EVENT_CHARGE, PAY_IN_TIME ) VALUES ( :billId, :subsEventCharge, :payInTime )", nativeQuery = true)
  void insertBillEx (@Param("billId") Long billId, @Param("subsEventCharge") Long subsEventCharge, @Param("payInTime") String payInTime);

  @Transactional
  @Modifying
  @Query(value = "UPDATE BILL_EX SET SUBS_EVENT_CHARGE = :subsEventCharge WHERE BILL_ID = :billId", nativeQuery = true)
  int updateBillEx (@Param("subsEventCharge") Long subsEventCharge, @Param("billId") Long billId);

}
