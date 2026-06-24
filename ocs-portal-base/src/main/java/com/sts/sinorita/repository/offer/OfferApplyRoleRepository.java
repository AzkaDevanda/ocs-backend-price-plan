package com.sts.sinorita.repository.offer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.OfferApplyRole;
import com.sts.sinorita.projection.offer.offerapplyrole.OfferApplyRoleForFishProjection;

@Repository
public interface OfferApplyRoleRepository extends JpaRepository<OfferApplyRole, Integer> {
  @Query(value = """
            SELECT
              O.OFFER_ID as offerId,
              O.ROLE_ID as roleId,
              O.SP_ID as spId,
              O.EXCLUDE_FLAG as excludeFlag,
              R.ROLE_NAME as roleName,
              R.ROLE_NAME AS conditionName,
              R.NAME as name,
              R.ROLE_CODE as roleCode,
              R.APP_ID as appId,
              R.IS_LOCKED as isLocked
            FROM OFFER_APPLY_ROLE O, POT.BFM_ROLE R
            WHERE O.ROLE_ID = R.ID
              AND O.OFFER_ID = :OFFER_ID
              AND NVL(O.SP_ID,0)= :SP_ID
            """, nativeQuery = true)
  Optional<OfferApplyRoleForFishProjection> findOfferApplyRoleForFish(@Param("OFFER_ID") Integer offerId, @Param("SP_ID") Integer spId);
}
