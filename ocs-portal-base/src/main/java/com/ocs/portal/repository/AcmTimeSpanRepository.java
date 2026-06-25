package com.ocs.portal.repository;

import com.ocs.portal.entity.AcmTimeSpan;
import com.ocs.portal.projection.pricePlan.price.QryAcmTimeSpanProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AcmTimeSpanRepository extends JpaRepository<AcmTimeSpan, Integer> {
    @Query("SELECT COALESCE(MAX(t.priority), 0) + 1 FROM AcmTimeSpan t WHERE t.priceVerId = :priceVerId")
    Integer findNextPriority(@Param("priceVerId") Integer priceVerId);

    @Modifying
    @Transactional
    @Query("update AcmTimeSpan set rate = :refValueId where id = :id")
    void insertValue(@Param("refValueId") Integer refValueId, @Param("id") Integer id);

    @Query("""
            SELECT
                A.id as acmTimeSpanId,
                A.priceVerId as priceVerId,
                B.timeSpanName as timeSpanName,
                A.adjustMethod as adjustMethod,
                A.timeSpanId as timeSpanId,
                C.valueString as valueString,
                A.priority as priority,
                A.rum as rum
            FROM AcmTimeSpan A
            LEFT JOIN TimeSpan B ON A.timeSpanId = B.id
            JOIN RefValue C ON A.rate = C.id
            WHERE
                A.priceVerId = :PRICE_VER_ID
                AND NVL(A.spId, 0) = :SP_ID
            ORDER BY
                A.priority ASC
            """)
    List<QryAcmTimeSpanProjection> qryAcmTimeSpan(@Param("PRICE_VER_ID") Integer priceVerId, @Param("SP_ID") Integer spId);

    @Modifying
    @Query(value = """
            DELETE FROM AcmTimeSpan A WHERE A.priceVerId = :PRICE_VER_ID
            """)
    void deleteAcmTimeSpan(@Param("PRICE_VER_ID") Integer priceVerId);


}
