package com.sts.sinorita.repository;

import com.sts.sinorita.entity.ExchangeRate;
import com.sts.sinorita.entity.ExchangeRateId;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, ExchangeRateId> {
    @Query (value = "SELECT 1 FROM EXCHANGE_RATE WHERE SRC_ACCT_RES_ID = :accResId FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<Integer> isReferencedInSrcAcctRes(@Param("accResId") Long accResId);

    @Query (value = "SELECT 1 FROM EXCHANGE_RATE WHERE OBJ_ACCT_RES_ID = :accResId FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<Integer> isReferencedInObjAcctRes(@Param("accResId") Long accResId);

}
