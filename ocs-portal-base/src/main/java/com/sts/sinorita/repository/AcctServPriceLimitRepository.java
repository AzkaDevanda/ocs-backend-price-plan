package com.sts.sinorita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.AcctServPriceLimit;
import com.sts.sinorita.entity.embeddable.AcctServPriceLimitId;
import com.sts.sinorita.projection.balanceAdjustment.SelectAcctServPriceLimitListProjection;

@Repository
public interface AcctServPriceLimitRepository extends JpaRepository<AcctServPriceLimit, AcctServPriceLimitId> {
  @Query(value = "SELECT A.ACCT_BOOK_TYPE AS acctBookType, A.PRICE_PLAN_ID AS pricePlanId, A.SUITABLE_TYPE AS suitableType FROM ACCT_SERV_PRICE_LIMIT A WHERE A.ACCT_BOOK_TYPE = :acctBookType AND (:spId IS NULL OR A.SP_ID = :spId)", nativeQuery = true)
  List<SelectAcctServPriceLimitListProjection> selectAcctServPriceLimitList(
      @Param("acctBookType") String acctBookType,
      @Param("spId") Long spId);
}
