package com.ocs.portal.repository;

import com.ocs.portal.dto.request.priceplan.RatePlanMappingDto;
import com.ocs.portal.storeProcedure.RatePlanMappingStoreProcedure;
import com.ocs.portal.entity.RatePlanMapping;
import com.ocs.portal.entity.RatePlanMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatePlanMappingRepository extends JpaRepository<RatePlanMapping, RatePlanMappingId>, RatePlanMappingStoreProcedure {

    @Query("SELECT COALESCE(MAX(r.priority), 0) FROM RatePlanMapping r")
    Integer getMaxPriority();

    @Query("SELECT COUNT(r) FROM RatePlanMapping r WHERE r.id.reId = :reId AND r.id.offerVerId = :offerVerId")
    Long countByReIdAndOfferVerId(Integer reId, Integer offerVerId);

    @Modifying
    @Query("UPDATE RatePlanMapping r SET r.priority = r.priority + 1 " +
            "WHERE r.priority >= :newPriority AND r.priority < :oldPriority")
    void shiftPriorityUp(@Param("newPriority") Integer newPriority,
                         @Param("oldPriority") Integer oldPriority);

    @Modifying
    @Query("UPDATE RatePlanMapping r SET r.priority = r.priority - 1 " +
            "WHERE r.priority > :oldPriority AND r.priority <= :newPriority")
    void shiftPriorityDown(@Param("oldPriority") Integer oldPriority,
                           @Param("newPriority") Integer newPriority);

    @Modifying
    @Query("DELETE FROM RatePlanMapping r WHERE r.id.reId = :reId AND r.id.offerVerId = :offerVerId")
    void deleteRatePlanMappingByOfferVerIdAndReId(@Param("offerVerId") Integer offerVerId, @Param("reId") Integer reId);

    @Query("SELECT r FROM RatePlanMapping r WHERE r.id.ratePlanId = :ratePlanId")
    RatePlanMapping findByRatePlanId(Integer ratePlanId);

    @Query(value = """
        SELECT
            RATE_PLAN_ID   AS ratePlanId,
            RE_ID          AS reId,
            OFFER_VER_ID   AS offerVerId,
            PRIORITY       AS priority,
            SP_ID          AS spId
        FROM RATE_PLAN_MAPPING
        WHERE RATE_PLAN_ID = :ratePlanId
        """, nativeQuery = true)
    RatePlanMappingDto[] qryRatePlanMappingByRatePlanId(
            @Param("ratePlanId") Long ratePlanId
    );
}
