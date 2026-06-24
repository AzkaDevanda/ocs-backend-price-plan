package com.sts.sinorita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.AcctServProdLimit;
import com.sts.sinorita.entity.embeddable.AcctServPriceLimitId;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcctServProdLimitListProjection;

@Repository
public interface AcctServProdLimitRepository extends JpaRepository<AcctServProdLimit, AcctServPriceLimitId>{

  @Query(value = """
      SELECT
        A.ACCT_BOOK_TYPE AS acctBookType,
        A.PROD_SPEC_ID AS prodSpecId,
        A.SUITABLE_TYPE AS suitableType,
        A.SP_ID AS spId
      FROM STS.ACCT_SERV_PROD_LIMIT A
      WHERE A.ACCT_BOOK_TYPE = :acctBookType
        AND (:spId IS NULL OR A.SP_ID = :spId)
      """, nativeQuery = true)
  List<SelectAcctServProdLimitListProjection> selectAcctServProdLimitList(
      @Param("acctBookType") String acctBookType,
      @Param("spId") Long spId
  );

}
