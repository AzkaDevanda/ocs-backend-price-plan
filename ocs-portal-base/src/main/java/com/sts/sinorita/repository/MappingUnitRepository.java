package com.sts.sinorita.repository;

import com.sts.sinorita.entity.MappingUnit;
import com.sts.sinorita.entity.MappingUnitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MappingUnitRepository extends JpaRepository<MappingUnit, MappingUnitId> {

    @Query(value = """
            SELECT COUNT(*)
            FROM MAPPING_UNIT mu
            JOIN RATE_PLAN_ZONE rpz ON mu.RATE_PLAN_ZONE_ID = rpz.RATE_PLAN_ZONE_ID
            JOIN RATE_PLAN rp ON rpz.RATE_PLAN_ID = rp.RATE_PLAN_ID
            WHERE rp.RATE_PLAN_ID = :ratePlanId
            """, nativeQuery = true)
    int countMappingUnitByRatePlanId(@Param("ratePlanId") Integer ratePlanId);

    @Query("SELECT mu FROM MappingUnit mu WHERE mu.id.mappingId = :mappingId")
    List<MappingUnit> findByMappingId(@Param("mappingId") Integer mappingId);


}
