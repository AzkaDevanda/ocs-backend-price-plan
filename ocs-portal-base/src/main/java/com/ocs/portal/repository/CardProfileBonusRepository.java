package com.ocs.portal.repository;

import com.ocs.portal.entity.CardProfileBonus;
import com.ocs.portal.entity.CardProfileBonusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardProfileBonusRepository extends JpaRepository<CardProfileBonus, CardProfileBonusId> {

    @Query(value = "SELECT 1 FROM CARD_PROFILE_BONUS WHERE ACCT_RES_ID = :acctResId FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<Integer> isReferencedInAcctRes(@Param("acctResId") Long acctResId);

}
