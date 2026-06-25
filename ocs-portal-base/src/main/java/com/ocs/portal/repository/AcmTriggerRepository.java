package com.ocs.portal.repository;

import com.ocs.portal.entity.AcmTrigger;
import com.ocs.portal.projection.pricePlan.trigger.AcmTriggerProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AcmTriggerRepository extends JpaRepository<AcmTrigger, Integer> {

    @Query("""
            SELECT CASE WHEN COUNT(a) > 0 THEN 1 ELSE 0 END
            FROM AcmTrigger a
            JOIN OfferVer ov ON a.offerVerId = ov.id
            JOIN RePricePlan rpp ON ov.id = rpp.id.offerVerId 
            JOIN RatePlan rp ON rpp.id.offerVerId = rp.offerVerId 
            JOIN PriceVer pv ON rp.id = pv.ratePlanId
            WHERE pv.id = :priceVerId
            """)
    int existsByPriceVerId(@Param("priceVerId") Integer priceVerId);

    @Query(value = """
            SELECT 
                at2.TRIGGER_ID as triggerId,
                at2.EFF_DATE as effDate,
                at2.EXP_DATE as expDate,
                at2.RESOURCE_ID as accumulationTypeId,
                rr.RESOURCE_NAME AS accumulationTypeName,
                rr.MASK as mask,
                at2.TRIGGER_TYPE as triggerModeId,
                tt.TRIGGER_TYPE_NAME AS triggerModeName,
                at2.STATE as state,
                at2.STATE_DATE as stateDate,
                at2.DESTINATION as destination
            FROM ACM_TRIGGER at2 
            LEFT JOIN RATABLE_RESOURCE rr ON at2.RESOURCE_ID = rr.RESOURCE_ID 
            LEFT JOIN TRIGGER_TYPE tt ON at2.TRIGGER_TYPE = tt.TRIGGER_TYPE
            WHERE at2.OFFER_VER_ID = :offerVerId
            """,
            nativeQuery = true)
    Page<AcmTriggerProjection> findAcmTriggerByOfferVerId(@Param("offerVerId") Integer offerVerId, Pageable pageable);
//    countQuery = "SELECT COUNT(*) FROM ACM_TRIGGER at2 WHERE at2.OFFER_VER_ID = :offerVerId",

    @Transactional
    @Modifying
    @Query(value = """
                DELETE FROM ACM_TRIGGER WHERE TRIGGER_ID= :id
            """, nativeQuery = true)
    public void deleteAcmTriggerById(@Param("id") Integer id);
}
