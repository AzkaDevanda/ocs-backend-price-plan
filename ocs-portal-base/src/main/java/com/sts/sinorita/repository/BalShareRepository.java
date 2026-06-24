package com.sts.sinorita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.BalShare;

@Repository
public interface BalShareRepository extends JpaRepository<BalShare, Long> {

  @Query(value = """
      SELECT DISTINCT G.SUBS_ID
      FROM BAL_SHARE A
      JOIN BAL_SHARE_DETAIL G
          ON A.BAL_SHARE_ID = G.BAL_SHARE_ID
      WHERE A.ACCT_ID = :acctId
        AND A.EFF_DATE <= SYSDATE
        AND (A.EXP_DATE > SYSDATE OR A.EXP_DATE IS NULL)
      """, nativeQuery = true)
  List<Long> selectAllSubsId(
      @Param("acctId") Long acctId);
}
