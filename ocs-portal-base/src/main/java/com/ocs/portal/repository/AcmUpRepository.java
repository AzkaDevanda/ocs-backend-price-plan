package com.ocs.portal.repository;

import com.ocs.portal.entity.AcmUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcmUpRepository extends JpaRepository<AcmUp, Integer> {

    @Query(value = "SELECT au.TIME_SPAN_UP_ID as timeSpanUpId,ts.TIME_SPAN_NAME as timeSpanName, " +
            "au.EFF_VALUE as effValue, " +
            "au.EXP_VALUE as expValue, " +
            "rr.RESOURCE_ID as acctItemTypeId, " +
            "rr.RESOURCE_NAME as accumulationType, " +
            "au.ADJUST_METHOD as adjustMethod, " +  // fix this
            "rv.VALUE_STRING as price, " +
            "au.RUM as calculateUnit," +
            "tsu.PRIORITY as priority " +            // double check this
            "FROM ACM_UP au " +
            "JOIN RATABLE_RESOURCE rr ON au.RESOURCE_ID = rr.RESOURCE_ID " +
            "JOIN PRICE p ON au.UP_ID = p.PRICE_ID " +
            "LEFT JOIN TIME_SPAN_UP tsu ON au.TIME_SPAN_UP_ID = tsu.TIME_SPAN_UP_ID " +
            "LEFT JOIN TIME_SPAN ts ON tsu.TIME_SPAN_ID = ts.TIME_SPAN_ID " +
            "JOIN REF_VALUE rv ON au.RATE = rv.REF_VALUE_ID " +
            "WHERE au.UP_ID = :priceId", nativeQuery = true)
    List<Object[]> getListAcmPriceByPriceId(@Param("priceId") Integer priceId);

    @Modifying
    @Query(value = "DELETE FROM AcmUp au WHERE au.upId = :upId")
    void deleteByUpId(@Param("upId") Integer upId);
}
