package com.sts.sinorita.repository.offer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.OfferApplyArea;
import com.sts.sinorita.projection.offer.offerapplyarea.OfferApplyAreaProjection;

@Repository
public interface OfferApplyAreaRepository extends JpaRepository<OfferApplyArea, Integer> {
  @Query(value = """
      SELECT
        O.OFFER_ID as offerId,
        O.AREA_ID as areaId,
        O.SP_ID as spId,
        O.EXCLUDE_FLAG as excludeFlag,
        A.PARENT_ID as parentId,
        A.AREA_NAME as areaName,
        A.AREA_NAME AS conditionName,
        A.COMMENTS as comments,
        A.AREA_CODE as areaCode
      FROM OFFER_APPLY_AREA O, POT.BFM_AREA A
      WHERE O.AREA_ID = A.AREA_ID
        AND O.OFFER_ID = :OFFER_ID
        AND NVL(O.SP_ID,0)= :SP_ID
      """, nativeQuery = true)
  Optional<OfferApplyAreaProjection> findOfferApplyArea(@Param("OFFER_ID") Integer offerId, @Param("SP_ID") Integer spId);
}
