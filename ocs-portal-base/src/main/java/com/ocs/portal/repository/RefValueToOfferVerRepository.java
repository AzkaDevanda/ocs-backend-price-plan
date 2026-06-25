package com.ocs.portal.repository;

import com.ocs.portal.dto.request.RefValueToOfferVerDto;
import com.ocs.portal.entity.RefValueToOfferVer;
import com.ocs.portal.entity.RefValueToOfferVerId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefValueToOfferVerRepository extends JpaRepository<RefValueToOfferVer, RefValueToOfferVerId> {
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM REF_VALUE_TO_OFFER_VER
        WHERE REF_VALUE_ID = :refValueId
          AND (:offerVerId IS NULL OR OFFER_VER_ID = :offerVerId)
        """, nativeQuery = true)
    int delRefValueToOfferVer(
            @Param("refValueId") Long refValueId,
            @Param("offerVerId") Long offerVerId);

    @Query(value = """
        SELECT
            REF_VALUE_ID  AS refValueId,
            OFFER_VER_ID  AS offerVerId,
            SP_ID         AS spId
        FROM REF_VALUE_TO_OFFER_VER
        WHERE REF_VALUE_ID = :refValueId
          AND OFFER_VER_ID = :offerVerId
        """, nativeQuery = true)
    RefValueToOfferVerDto qryRefValueToOfferVer(
            @Param("refValueId") Long refValueId,
            @Param("offerVerId") Long offerVerId
    );
}
