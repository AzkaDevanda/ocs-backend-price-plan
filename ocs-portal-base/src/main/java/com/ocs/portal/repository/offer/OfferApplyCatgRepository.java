package com.ocs.portal.repository.offer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.OfferApplyCatg;
import com.ocs.portal.projection.offer.OfferApplyCatgProjection;

@Repository
public interface OfferApplyCatgRepository extends JpaRepository<OfferApplyCatg, Integer> {

  @Query(value = """
          SELECT
              O.OFFER_ID       AS offerId,
              O.CATG_ID        AS catgId,
              O.SP_ID          AS spId,
              O.EXCLUDE_FLAG   AS excludeFlag,
              C.CATG_TYPE      AS catgType,
              C.CATG_DEF_TYPE  AS catgDefType,
              C.CATG_NAME      AS catgName,
              C.CATG_NAME      AS conditionName,
              C.COMMENTS       AS comments,
              C.CREATED_DATE   AS createdDate
          FROM OFFER_APPLY_CATG O,CATG C
          WHERE O.CATG_ID = C.CATG_ID
            AND C.STATE = :state
            AND O.OFFER_ID = :offerId
            AND NVL(O.SP_ID,0) = :spId
      """, nativeQuery = true)
  Optional<OfferApplyCatgProjection> findOfferApplyCategory(@Param("state") Character state, @Param("offerId") Integer offerId, @Param("spId") Integer spId);
}
