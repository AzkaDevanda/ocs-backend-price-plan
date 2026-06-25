package com.ocs.portal.repository;

import com.ocs.portal.entity.AcctResFree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcctResFreeRepository extends JpaRepository<AcctResFree, Long> {

    @Query(value = "SELECT 1 FROM ACCT_RES_FREE WHERE ACCT_RES_ID = :acctResId FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<Integer> isReferencedInAcctRes(@Param("acctResId") Long acctResId);

}
