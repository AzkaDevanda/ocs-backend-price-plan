package com.ocs.portal.repository;

import com.ocs.portal.entity.AcmCalc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AcmCalcRepository extends JpaRepository<AcmCalc, Integer> {

    @Query(value = "SELECT rr.RESOURCE_ID, rr.RESOURCE_NAME , ac.RUM, ac.TIME_SPAN_UP_ID, ts.TIME_SPAN_NAME, tsu.PRIORITY    FROM ACM_CALC ac \n" +
            "JOIN RATABLE_RESOURCE rr ON ac.RESOURCE_ID = rr.RESOURCE_ID \n" +
            "JOIN PRICE p  ON ac.UP_ID = p.PRICE_ID \n" +
            "LEFT JOIN TIME_SPAN_UP tsu ON ac.TIME_SPAN_UP_ID = tsu.TIME_SPAN_UP_ID \n" +
            "LEFT JOIN TIME_SPAN ts ON tsu.TIME_SPAN_ID = ts.TIME_SPAN_ID \n" +
            "WHERE ac.UP_ID = :priceId", nativeQuery = true)
    List<Object[]> getAcmCalcByPriceId(@Param("priceId") Integer priceId);

    @Modifying
    @Query(value = "DELETE FROM ACM_CALC ac WHERE ac.UP_ID = :upId", nativeQuery = true)
    void deleteByUpId(@Param("upId") Integer upId);

}
