package com.sts.sinorita.repository;

import com.sts.sinorita.entity.ZoneMap;
import com.sts.sinorita.projection.ZoneProjection;
import com.sts.sinorita.projection.pricePlan.rateplan.QryActiveZoneMapProjection;
import com.sts.sinorita.projection.trigger.ZoneMapProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneMapRepository extends JpaRepository<ZoneMap, Integer> {

    @Query("""
            SELECT
            	A.id AS zoneMapId,
            	A.zoneMapName AS zoneMapName,
            	A.comments AS comments,
            	A.matchMode AS matchMode,
            	A.stdCode AS stdCode,
            	A.zoneMapType AS zoneMapType,
            	A.glTypeId AS glTypeId,
            	A.state AS state
            FROM
            	ZoneMap A
            WHERE
            	1 = 1
            	AND A.state = 'A'
            	AND (:ZONE_MAP_ID IS NULL OR A.id = :ZONE_MAP_ID)
            	AND (:ZONE_MAP_NAME IS NULL OR UPPER(A.zoneMapName) LIKE  CONCAT('%', UPPER(:ZONE_MAP_NAME), '%'))
            	AND (
                     :SP_ID IS NULL OR COALESCE(A.spId, 0) = :SP_ID OR :SP_ID = -1
                 )
            ORDER BY
            	A.zoneMapName
            """)
    List<QryActiveZoneMapProjection> qryActiveZoneMap(@Param("ZONE_MAP_ID") Integer zoneMapId,
                                                      @Param("ZONE_MAP_NAME") String zoneMapName,
                                                      @Param("SP_ID") Integer spId);


    @Query(value = """
            SELECT 
                A.ZONE_MAP_ID AS zoneMapId,
                A.ZONE_MAP_NAME AS zoneMapName,
                A.COMMENTS AS comments,
                A.MATCH_MODE AS matchMode,
                A.STD_CODE AS stdCode,
                A.ZONE_MAP_TYPE AS zoneMapType,
                A.GL_TYPE_ID AS glTypeId
            FROM STS.ZONE_MAP A
            WHERE A.STATE = 'A'
              AND (:zoneMapId IS NULL OR A.ZONE_MAP_ID = :zoneMapId)
              AND (:zoneMapName IS NULL OR UPPER(A.ZONE_MAP_NAME) LIKE CONCAT('%', UPPER(:zoneMapName), '%'))
              AND (NVL(A.SP_ID, 0) = :spId OR -1 = :spId)
            ORDER BY A.ZONE_MAP_NAME
            """, nativeQuery = true)
    List<ZoneMapProjection> qryZoneMap(
            @Param("zoneMapId") Long zoneMapId,
            @Param("zoneMapName") String zoneMapName,
            @Param("spId") Long spId
    );

    @Query(value = """
                SELECT 
                    A.ZONE_ID            AS zoneId,
                          A.ZONE_NAME          AS zoneName,
                          A.COMMENTS           AS zoneComments,
                          A.ZONE_CODE          AS zoneCode,
                          A.ZONE_MAP_ID        AS zoneMapId,
                          A.PARENT_ZONE_ID     AS parentZoneId,
            
                          C.ZONE_MAP_NAME      AS zoneMapName,
                          C.MATCH_MODE         AS matchMode,
                          C.STD_CODE           AS stdCode,
                          C.COMMENTS           AS zoneMapComments,
            
                          B.MATCH_MODE_NAME    AS matchModeName
            
                FROM ZONE A
                JOIN ZONE_MAP C ON A.ZONE_MAP_ID = C.ZONE_MAP_ID
                JOIN MATCH_MODE B ON C.MATCH_MODE = B.MATCH_MODE
            
                WHERE 1 = 1
                  AND (:zoneId IS NULL OR A.ZONE_ID = :zoneId)
                  AND (:parentZoneId IS NULL OR A.PARENT_ZONE_ID = :parentZoneId)
                  AND (:zoneMapId IS NULL OR A.ZONE_MAP_ID = :zoneMapId)
                  AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId)
            
                ORDER BY A.ZONE_MAP_ID, A.ZONE_ID
            """, nativeQuery = true)
    List<ZoneProjection> qryZoneByAll(
            @Param("zoneId") Long zoneId,
            @Param("parentZoneId") Long parentZoneId,
            @Param("zoneMapId") Long zoneMapId,
            @Param("spId") Long spId
    );


}
