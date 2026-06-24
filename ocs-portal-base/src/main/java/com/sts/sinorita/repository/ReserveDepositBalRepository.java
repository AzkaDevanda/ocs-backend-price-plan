package com.sts.sinorita.repository;

import com.sts.sinorita.entity.ReserveDepositBal;
import com.sts.sinorita.entity.ReserveDepositBalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReserveDepositBalRepository extends JpaRepository<ReserveDepositBal, ReserveDepositBalId> {
  @Transactional
  @Modifying
  @Query(value = """
    INSERT INTO RESERVE_DEPOSIT_BAL (
        EVENT_INST_ID,
        DEPOSIT_ITEM_ID,
        RESERVE_BAL,
        ORDER_ITEM_ID,
        CREATED_DATE,
        STATE,
        STATE_DATE,
        C_ORDER_ITEM_ID
    ) VALUES (
        :eventInstId,
        :depositItemId,
        :reserveBal,
        :orderItemId,
        SYSDATE,
        :state,
        SYSDATE,
        :cOrderItemId
    )
    """,
    nativeQuery = true)
  void insertReserveDepositBal (
    @Param("eventInstId") Long eventInstId,
    @Param("depositItemId") Long depositItemId,
    @Param("reserveBal") Long reserveBal,
    @Param("orderItemId") Long orderItemId,
    @Param("state") String state,
    @Param("cOrderItemId") Long cOrderItemId
  );
}
