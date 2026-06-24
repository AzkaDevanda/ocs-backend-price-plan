package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Re;
import com.sts.sinorita.projection.pricePlan.price.QryProdSubsReEventForSubsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReRepository extends JpaRepository<Re, Integer> {

    // parameter
    @Query(value = "SELECT r.RE_ID, ov.OFFER_VER_ID, r.RE_NAME FROM RE_PRICE_PLAN rpp \n" +
            "JOIN RE r ON rpp.RE_ID  = r.RE_ID \n" +
            "JOIN OFFER_VER ov ON rpp.OFFER_VER_ID = ov.OFFER_VER_ID \n" +
            "JOIN OFFER o ON ov.OFFER_ID  = o.OFFER_ID \n" +
            "WHERE r.RE_TYPE = :reType AND ov.OFFER_VER_ID = :offerVerId", nativeQuery = true)
    List<Object[]> getUsageEventByOfferVerId(@Param("offerVerId") Integer offerVerId, @Param("reType") Character reType);

    @Query(value = """
        SELECT
            A.RE_ID AS "reId",
            A.RE_NAME AS "reName",
            B.SUBS_EVENT_ID AS "subsEventId"
        FROM
            RE A
        JOIN SUBS_RE_EVENT B ON
            A.RE_ID = B.RE_ID
        WHERE
            B.SUBS_EVENT_ID NOT IN (71, 73)
            AND A.RE_ID NOT IN (
            SELECT
                T.RE_ID
            FROM
                RE_PRICE_PLAN T
            WHERE
                T.OFFER_VER_ID = :offerVerId
               )
            AND (NVL(A.SP_ID, 0) = :spId
                OR :spId = -1)
            AND A.RE_TYPE <> :notReType
        ORDER BY
            A.RE_NAME
    """, nativeQuery = true)
    List<QryProdSubsReEventForSubsProjection> qryProdSubsReEventForSubs(
            @Param("offerVerId") Integer offerVerId,
            @Param("spId") Integer spId,
            @Param("notReType") Character notReType
    );

    @Query(value = """
             SELECT ru.PARENT_RE_ID , r.*
            FROM RE r
            LEFT JOIN RE_USAGE ru ON ru.RE_ID = r.RE_ID
            WHERE r.RE_TYPE = :reType""", nativeQuery = true)
    List<Object[]> getEvent(@Param("reType") Character reType);

    @Query(value = " SELECT r.RE_ID ,r.RE_NAME, r.RE_TYPE  FROM RE r \n" +
            " WHERE r.RE_TYPE = '1'", nativeQuery = true)
    List<Object[]> getUsageEvent();

}
