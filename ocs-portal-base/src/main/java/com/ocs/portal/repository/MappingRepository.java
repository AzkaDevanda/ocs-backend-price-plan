package com.ocs.portal.repository;

import com.ocs.portal.projection.pricePlan.rateplan.QryMappingProjection;
import com.ocs.portal.storeProcedure.MappingStoreProcedure;
import com.ocs.portal.dto.response.mapping.FlatRecordMapping;
import com.ocs.portal.entity.Mapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MappingRepository extends JpaRepository<Mapping, Integer>, MappingStoreProcedure {

    @Query("SELECT COALESCE(MAX(m.priority)+1, 1) FROM Mapping m")
    Integer getMaxPriority();

    @Query(value = "SELECT \n" +
            "    m.MAPPING_ID as mappingId,\n" +
            "    m.MAPPING_NAME as mappingName,\n" +
            "    pv.PRICE_VER_ID as priceVerId,\n" +
            "    pv.EFF_DATE as effDate,\n" +
            "    pv.EXP_DATE as expDate,\n" +
            "    p.PRICE_ID as priceId,\n" +
            "    p.PRICE_NAME as priceName,\n" +
            "    ait.ACCT_ITEM_TYPE_NAME as acctItemTypeName,\n" +
            "    rv.VALUE_STRING as valueString,\n" +
            "    p.RUM as rum,\n" +
            "    ra.RE_ATTR_NAME as reAttrName\n" +
            "FROM MAPPING m\n" +
            "LEFT JOIN MAPPING_UNIT mu ON m.MAPPING_ID = mu.MAPPING_ID \n" +
            "LEFT JOIN RATE_PLAN_ZONE rpz ON mu.RATE_PLAN_ZONE_ID  = rpz.RATE_PLAN_ZONE_ID \n" +
            "LEFT JOIN RATE_PLAN rp ON rpz.RATE_PLAN_ID = rp.RATE_PLAN_ID \n" +
            "LEFT JOIN PRICE_VER pv ON rp.RATE_PLAN_ID = pv.RATE_PLAN_ID\n" +
            "LEFT JOIN PRICE p ON pv.PRICE_VER_ID = p.PRICE_VER_ID\n" +
            "LEFT JOIN REF_VALUE rv ON p.VALUE = rv.REF_VALUE_ID\n" +
            "LEFT JOIN RE_ATTR ra ON p.RE_ATTR = ra.RE_ATTR\n" +
            "LEFT JOIN ACCT_ITEM_TYPE ait ON p.ACCT_ITEM_TYPE_ID = ait.ACCT_ITEM_TYPE_ID\n" +
            "WHERE rp.RATE_PLAN_ID = :ratePlanId ", nativeQuery = true)
    List<FlatRecordMapping> getMappingPriceVerAndPrice(@Param("ratePlanId") Integer ratePlanId);

    @Query(value = """
            SELECT
            	A.id as mappingId,
            	A.mappingName as mappingName,
            	A.priority as priority
            FROM
            	Mapping A
            WHERE
            	A.ratePlanId = :RATE_PLAN_ID
            	AND (
                     :SP_ID IS NULL OR COALESCE(A.spId, 0) = :SP_ID
                 )
            ORDER BY
            	A.priority
            """)
    List<QryMappingProjection> qryMapping(@Param("RATE_PLAN_ID") Integer ratePlanId, @Param("SP_ID") Integer spId);

    @Query(value = """
            SELECT *
            FROM MAPPING A WHERE A.RATE_PLAN_ID = :ratePlanId
            """, nativeQuery = true)
    List<Mapping> selectMappingByRatePlanId(@Param("ratePlanId") Integer ratePlanId);

    @Modifying
    @Query("UPDATE Mapping r SET r.priority = r.priority + 1 " +
            "WHERE r.priority >= :newPriority AND r.priority < :oldPriority")
    void shiftPriorityUp(@Param("newPriority") Integer newPriority,
                         @Param("oldPriority") Integer oldPriority);

    @Modifying
    @Query("UPDATE Mapping r SET r.priority = r.priority - 1 " +
            "WHERE r.priority > :oldPriority AND r.priority <= :newPriority")
    void shiftPriorityDown(@Param("oldPriority") Integer oldPriority,
                           @Param("newPriority") Integer newPriority);

}
