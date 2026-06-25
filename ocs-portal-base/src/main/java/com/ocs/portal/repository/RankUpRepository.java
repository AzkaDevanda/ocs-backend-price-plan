package com.ocs.portal.repository;

import com.ocs.portal.entity.RankUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankUpRepository extends JpaRepository<RankUp, Integer> {

    @Query(value = "SELECT ru.RANK_UP_ID ,ru.TIME_SPAN_UP_ID , ts.TIME_SPAN_NAME , ru.OFFSET , ru.DURATION , ru.ADJUST_METHOD , rv.VALUE_STRING , ru.RUM ,tsu.PRIORITY FROM RANK_UP ru \n" +
            "JOIN PRICE p ON ru.UP_ID = p.PRICE_ID \n" +
            "JOIN REF_VALUE rv ON ru.RATE = rv.REF_VALUE_ID \n" +
            "LEFT JOIN TIME_SPAN_UP tsu ON ru.TIME_SPAN_UP_ID = tsu.TIME_SPAN_UP_ID \n" +
            "LEFT JOIN TIME_SPAN ts ON tsu.TIME_SPAN_ID = ts.TIME_SPAN_ID \n" +
            "WHERE ru.UP_ID = :priceId", nativeQuery = true)
    List<Object[]> getRankUpByPriceId(@Param("priceId") Integer priceId);


    @Modifying
    @Query(value = "DELETE FROM RankUp ru WHERE ru.upId = :upId")
    void deleteByUpId(@Param("upId") Integer upId);
}
