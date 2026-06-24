package com.sts.sinorita.repository;

import com.sts.sinorita.entity.TimeUnit;
import com.sts.sinorita.projection.pricePlan.price.QryTimeUnitListProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeUnitRepository extends JpaRepository<TimeUnit, Character> {

    @Query(value = """
            SELECT t.id as timeUnit ,t.timeUnitName as timeUnitName,t.comments as comments\s
              FROM TimeUnit t
             WHERE 1 = 1  \s
              AND 'Y' = :NOT_EXACT AND t.id != 'S'
            """)
    List<QryTimeUnitListProjection> qryTimeUnitList(@Param("NOT_EXACT") String NOT_EXACT);

}
