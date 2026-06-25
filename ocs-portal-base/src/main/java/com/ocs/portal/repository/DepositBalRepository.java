package com.ocs.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ocs.portal.entity.DepositBal;

public interface DepositBalRepository extends JpaRepository<DepositBal, Long> {

    @Modifying
    @Transactional
    @Query(
        value = """
            UPDATE STS.DEPOSIT_BAL
            SET BAL = BAL + :depositPaymentAmount
            WHERE SUBS_ID = :subsId
        """,
        nativeQuery = true
    )
    int updateDepositBal(
            @Param("subsId") Long subsId,
            @Param("depositPaymentAmount") Long depositPaymentAmount
    );

}
