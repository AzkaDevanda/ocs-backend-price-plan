package com.sts.sinorita.repository;

import com.sts.sinorita.entity.RatePlanZone;
import com.sts.sinorita.projection.pricePlan.rateplan.QryRatePlanZoneAndMappingUnitProjection;
import com.sts.sinorita.projection.pricePlan.rateplan.QryRatePlanZoneProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatePlanZoneRepository extends JpaRepository<RatePlanZone, Integer> {

    @Query(value = " SELECT rpz.priority, msm.MAPPING_SRC_TYPE_NAME , ra.RE_ATTR_NAME, mdtm.MAPPING_DES_TYPE_NAME, zm.ZONE_MAP_NAME, rpz.LABEL_SHOW  FROM RATE_PLAN_ZONE rpz\n" +
            " JOIN ZONE_MAP zm ON rpz.MAPPING_DES_VALUE = zm.ZONE_MAP_ID \n" +
            " JOIN RE_ATTR ra ON rpz.MAPPING_SRC_VALUE = ra.RE_ATTR\n" +
            " JOIN MAPPING_SRC_TYPE_MASTER msm ON rpz.MAPPING_SRC_TYPE  = msm.MAPPING_SRC_TYPE \n" +
            " JOIN MAPPING_DES_TYPE_MASTER mdtm ON rpz.MAPPING_DES_TYPE  = mdtm.MAPPING_DES_TYPE" +
            " WHERE rpz.RATE_PLAN_ID = :ratePlanId ORDER BY rpz.priority ASC ", nativeQuery = true)
    List<Object[]> findByRatePlanId(@Param("ratePlanId") Integer ratePlanId);

    @Query("SELECT COALESCE(MAX(rp.priority), 0) + 1 FROM RatePlanZone rp WHERE rp.ratePlanId = :ratePlanId")
    Integer findNextPriority(@Param("ratePlanId") Integer ratePlanId);

    @Query(value = """
            SELECT A.RATE_PLAN_ZONE_ID AS ratePlanZoneId,
                       A.RATE_PLAN_ID AS ratePlanId,
                       A.MAPPING_SRC_TYPE AS mappingSrcType,
                       A.MAPPING_SRC_VALUE AS mappingSrcValue,
                       A.MAPPING_DES_TYPE AS mappingDesType,
                       A.MAPPING_DES_VALUE AS mappingDesValue,
                       A.PRIORITY AS priority,
                       A.LABEL_SHOW AS labelShow,
                       B.RE_ATTR_NAME AS reAttrName,
                       C.ZONE_MAP_NAME AS zoneMapName
                  FROM RATE_PLAN_ZONE A
                  LEFT JOIN RE_ATTR B ON TO_CHAR(B.RE_ATTR) = A.MAPPING_SRC_VALUE
                  LEFT JOIN ZONE_MAP C ON TO_CHAR(C.ZONE_MAP_ID) = A.MAPPING_DES_VALUE
                 WHERE A.RATE_PLAN_ID = :ratePlanId
                   AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId)
                 ORDER BY A.PRIORITY
            """, nativeQuery = true)
    List<QryRatePlanZoneProjection> qryRatePlanZone(@Param("ratePlanId") Integer ratePlanId, @Param("spId") Integer spId);

    @Query(value = """
            SELECT
            	A.RATE_PLAN_ZONE_ID as ratePlanZoneId,
            	A.RATE_PLAN_ID as ratePlanId,
            	A.MAPPING_SRC_TYPE as mappingSrcType,
            	A.MAPPING_SRC_VALUE as mappingSrcValue,
            	A.MAPPING_DES_TYPE as mappingDesType,
            	A.MAPPING_DES_VALUE as mappingDesValue,
            	A.PRIORITY as priority,
            	A.LABEL_SHOW as labelShow,
            	B.RE_ATTR_NAME as reAttrName,
            	C.ZONE_MAP_NAME as zoneMapName,
            	D.MAPPING_ID as mappingId,
            	D.MAPPING_NAME as mappingName,
            	E.MAPPING_MATCH_TYPE as mappingMatchType,
            	E.MAPPING_TYPE as mappingType,
            	E.MAPPING_VALUE as mappingValue
            FROM
            	RATE_PLAN_ZONE A
            LEFT JOIN RE_ATTR B ON
            	A.MAPPING_SRC_VALUE = TO_CHAR(B.RE_ATTR)
            LEFT JOIN ZONE_MAP C ON
            	A.MAPPING_DES_VALUE = C.ZONE_MAP_ID
            JOIN MAPPING D ON
            	D.MAPPING_ID = :MAPPING_ID
            	AND D.RATE_PLAN_ID = A.RATE_PLAN_ID
            JOIN MAPPING_UNIT E ON
            	D.MAPPING_ID = E.MAPPING_ID
            	AND E.RATE_PLAN_ZONE_ID = A.RATE_PLAN_ZONE_ID
            WHERE
            	A.RATE_PLAN_ID = :RATE_PLAN_ID
            	AND (:SP_ID IS NULL OR A.SP_ID = :SP_ID)
            ORDER BY
            	A.PRIORITY
            """, nativeQuery = true)
    List<QryRatePlanZoneAndMappingUnitProjection> qryRatePlanZoneAndMappingUnit(@Param("MAPPING_ID") Integer mappingId, @Param("RATE_PLAN_ID") Integer ratePlanId, @Param("SP_ID") Integer spId);

    @Modifying
    @Query(value = """
            DELETE FROM RATE_PLAN_ZONE A WHERE A.RATE_PLAN_ID = :ratePlanId
            """, nativeQuery = true)
    int delRatePlanZoneByRatePlanId(@Param("ratePlanId") Integer ratePlanId);
}
