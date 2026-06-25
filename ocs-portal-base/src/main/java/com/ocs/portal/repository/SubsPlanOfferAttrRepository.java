package com.ocs.portal.repository;

import com.ocs.portal.entity.SubsPlanOfferAttr;
import com.ocs.portal.projection.balanceAdjustment.SelectSubsPlanOfferAttrByAttrCodeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubsPlanOfferAttrRepository extends JpaRepository<SubsPlanOfferAttr, Long> {

  @Query(value = """
          SELECT A.SUBS_PLAN_OFFER_ATTR_ID AS subsPlanOfferAttrId, A.OFFER_VER_ID AS offerVerId, A.OFFER_ID AS offerId, A.ATTR_ID AS attrId, A.DEFAULT_VALUE AS defaultValue, A.SP_ID AS spId FROM SUBS_PLAN_OFFER_ATTR A JOIN OFFER_VER B ON A.OFFER_VER_ID = B.OFFER_VER_ID WHERE B.OFFER_ID = :offerId AND B.EFF_DATE <= SYSDATE AND (B.EXP_DATE > SYSDATE OR B.EXP_DATE IS NULL) AND A.OFFER_ID = :offerId AND A.ATTR_ID = :attrId
          """, nativeQuery = true)
  Optional<SelectSubsPlanOfferAttrByAttrCodeProjection> selectSubsPlanOfferAttrByAttrCode (@Param("subsPlanId") Long subsPlanId, @Param("offerId") Long offerId, @Param("attrId") Long attrId);
}
