package com.ocs.portal.repository;

import com.ocs.portal.entity.Zone;
import com.ocs.portal.projection.pricePlan.rateplan.QryZoneByAllProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer> {

    //    AND A.ZONE_ID =:ZONE_ID
//    AND A.PARENT_ZONE_ID & PARENT_ZONE_ID &
    //    AND (NVL(A.SP_ID, 0) = :SP_ID
//    OR -1 =:SP_ID)
    @Query(value = """
            SELECT
                A.id as zoneId,
                A.zoneName as zoneName,
                A.comments as zoneComments,
                A.zoneCode as zoneCode,
                A.zoneMapId as zoneMapId,
                A.parentZoneId as parentZoneId,
                C.zoneMapName as zoneMapName,
                C.matchMode as matchMode,
                C.stdCode as stdCode,
                C.comments as zoneMapComments
            FROM Zone A
            JOIN ZoneMap C ON A.zoneMapId = C.id
            AND A.zoneMapId =:ZONE_MAP_ID
            ORDER BY C.id, A.id
            """)
    List<QryZoneByAllProjection> qryZoneByAll(@Param("ZONE_MAP_ID") Integer zoneMapId);
}
