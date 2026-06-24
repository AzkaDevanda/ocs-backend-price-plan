package com.sts.sinorita.repository;

import com.sts.sinorita.entity.CardProfileRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardProfileRuleRepository extends JpaRepository<CardProfileRule,Integer> {
    @Query(value = "SELECT 1 FROM CARD_PROFILE_RULE WHERE ACCT_RES_ID = :acctResId FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<Integer> isReferencedInAcctRes(@Param("acctResId") Long acctResId);
}
