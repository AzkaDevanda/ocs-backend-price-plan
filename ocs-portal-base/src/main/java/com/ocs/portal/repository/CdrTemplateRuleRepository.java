package com.ocs.portal.repository;

import com.ocs.portal.entity.CdrTemplateRule;
import com.ocs.portal.projection.balanceAdjustment.SelectAllCdrTemplateRuleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CdrTemplateRuleRepository extends JpaRepository<CdrTemplateRule, Integer> {
  @Query(value = "SELECT A.CDR_TEMPLATE_RULE_ID AS cdrTemplateRuleId, A.CDR_TEMPLATE_ID AS cdrTemplateId, A.CONTACT_CHANNEL_ID AS contactChannelId, A.PAYMENT_METHOD AS paymentMethod, A.EVENT_FORMAT_ID AS eventFormatId, A.SP_ID AS spId, A.EVENT_TYPE AS eventType, A.SUBS_EVENT_ID AS subsEventId FROM CDR_TEMPLATE_RULE A", nativeQuery = true)
  List<SelectAllCdrTemplateRuleProjection> selectAllCdrTemplateRule ();
}
