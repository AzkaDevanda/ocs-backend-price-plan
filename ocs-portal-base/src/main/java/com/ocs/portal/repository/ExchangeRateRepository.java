package com.ocs.portal.repository;

import com.ocs.portal.entity.ExchangeRate;
import com.ocs.portal.entity.ExchangeRateId;
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
