package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcmRef;
import com.sts.sinorita.projection.pricePlan.price.QryAcmRefProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AcmRefRepository extends JpaRepository<AcmRef, Integer> {

    @Modifying
    @Transactional
    @Query("update AcmRef set rate = :refValueId where id = :id")
    void insertValue(@Param("refValueId") Integer refValueId, @Param("id") Integer id);

    //    C.ADJUST_METHOD_NAME,
    //    ADJUST_METHOD C
//    A.ADJUST_METHOD = :ADJUST_METHOD
    @Query("""
            SELECT
                A.id AS acmRefId,
                A.priceVerId AS priceVerId,
                A.resourceId AS resourceId,
                B.resourceName AS resourceName,
                A.adjustMethod AS adjustMethod,
                A.acmTimeSpanId AS acmTimeSpanId,
                D.valueString AS rate,
                A.rum AS rum,
                A.effValue AS effValue,
                A.expValue AS expValue,
                C.priority AS acmTimeSpanPriority
            FROM AcmRef A
            JOIN RatableResource B ON A.resourceId = B.id
            LEFT JOIN AcmTimeSpan C ON A.acmTimeSpanId = C.id
            JOIN RefValue D ON A.rate = D.id
            WHERE
                (:PRICE_VER_ID IS NULL OR A.priceVerId = :PRICE_VER_ID)
                AND (:SP_ID IS NULL OR NVL(A.spId, 0) = :SP_ID)
            ORDER BY
                A.effValue
            """)
    List<QryAcmRefProjection> QryAcmRef(@Param("PRICE_VER_ID") Integer priceVerId, @Param("SP_ID") Integer spId);

    @Modifying
    @Query("""
            DELETE FROM AcmRef ar WHERE ar.priceVerId = :priceVerId
            """)
    void deleteAcmRef(@Param("priceVerId") Integer priceVerId);

}
