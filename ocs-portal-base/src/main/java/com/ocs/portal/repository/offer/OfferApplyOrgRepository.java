package com.ocs.portal.repository.offer;

import com.ocs.portal.entity.OfferApplyOrg;
import com.ocs.portal.projection.offer.offerapplyorg.OfferApplyOrgProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferApplyOrgRepository extends JpaRepository<OfferApplyOrg, Integer> {
    @Query(value = """
            SELECT O.OFFER_ID offerId,
                   O.ORG_ID orgId,
                   O.SP_ID spId,
                   O.EXCLUDE_FLAG excludeFlag,
                   R.PARENT_ORG_ID parentOrgId,
                   R.AREA_ID areaId,
                   R.ORG_NAME orgName,
                   R.ORG_NAME AS conditionName,
                   R.ORG_TYPE orgType,
                   R.STATE state,
                   R.STATE_DATE stateDate,
                   R.ORG_CODE orgCode
              FROM OFFER_APPLY_ORG O, POT.BFM_ORG R
              WHERE O.ORG_ID = R.ORG_ID
              AND O.OFFER_ID = :OFFER_ID
              AND NVL(O.SP_ID,0)= :SP_ID
            """, nativeQuery = true)
    List<OfferApplyOrgProjection> qryOfferApplyOrg(@Param("OFFER_ID") Integer offerId, @Param("SP_ID") Integer spId);

}
