package com.ocs.portal.repository;

import com.ocs.portal.entity.TimeSpanUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSpanUpRepository extends JpaRepository<TimeSpanUp, Integer> {

    @Query(value = "SELECT tsu.TIME_SPAN_UP_ID, tsu.PRIORITY , ts.TIME_SPAN_NAME  ,tsu.ADJUST_METHOD, rv.VALUE_STRING , tsu.RUM   FROM TIME_SPAN_UP tsu \n" +
            "JOIN TIME_SPAN ts ON tsu.TIME_SPAN_ID = ts.TIME_SPAN_ID \n" +
            "JOIN UP u ON tsu.UP_ID = u.UP_ID \n" +
            "JOIN PRICE p ON u.UP_ID = p.PRICE_ID \n" +
            "JOIN REF_VALUE rv ON tsu.RATE = rv.REF_VALUE_ID \n" +
            "WHERE tsu.UP_ID = :priceId ORDER BY tsu.PRIORITY ASC", nativeQuery = true)
    List<Object[]> getTimeSpanUpByPriceId(@Param("priceId") Integer priceId);

    @Query("SELECT COALESCE(MAX(t.priority), 0) + 1 FROM TimeSpanUp t WHERE t.upId = :upId")
    Integer findNextPriority(@Param("upId") Integer UpId);

    @Modifying
    @Query("DELETE FROM TimeSpanUp tsu WHERE tsu.upId = :upId")
    void deleteByUpId(Integer upId);
}
