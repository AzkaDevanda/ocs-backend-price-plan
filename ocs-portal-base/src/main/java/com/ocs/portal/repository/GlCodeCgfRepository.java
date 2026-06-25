package com.ocs.portal.repository;

import com.ocs.portal.entity.GlCodeCfg;
import com.ocs.portal.projection.balanceAdjustment.QryGlCodeCfgDtoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlCodeCgfRepository extends JpaRepository<GlCodeCfg, Integer> {

  @Query(value = "SELECT PRIORITY, SUBS_EVENT_ID, ACCT_BOOK_TYPE, PAYMENT_METHOD_ID, LOAN_TYPE, DEPOSIT_TYPE_ID, ACCT_RES_ID, REASON_ID, ACCT_ITEM_TYPE_ID, GL_DIRECTION, GL_COEFFICIENT, GL_CODE, SP_ID, LEDGER_TYPE FROM GL_CODE_CFG ORDER BY PRIORITY DESC", nativeQuery = true)
  List<QryGlCodeCfgDtoProjection> qryGlCodeCfgDto ();
}
