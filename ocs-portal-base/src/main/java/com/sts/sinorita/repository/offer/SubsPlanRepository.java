package com.sts.sinorita.repository.offer;

import com.sts.sinorita.entity.SubsPlan;
import com.sts.sinorita.projection.offer.OfferSubsPlanProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubsPlanRepository extends JpaRepository<SubsPlan, Integer> {
    @Query(value = """
            SELECT A.SUBS_PLAN_ID AS subsPlanId,
                AO.OFFER_NAME AS subsPlanName,
                A.INDEP_PROD_SPEC_ID AS indepProdSpecId,
                B.OFFER_VER_ID AS offerVerId,
                B.EFF_DATE AS effDate,
                B.EXP_DATE AS expDate,
                C.OFFER_GROUP_ID AS offerGroupId
            FROM SUBS_PLAN A
            JOIN OFFER_VER B ON A.SUBS_PLAN_ID = B.OFFER_ID
            JOIN OFFER AO ON AO.OFFER_ID = B.OFFER_ID
            JOIN SUBS_PLAN_OFFER_SELECT C ON B.OFFER_VER_ID = C.OFFER_VER_ID
            WHERE C.OFFER_GROUP_ID = :offerGroupId AND NVL(A.SP_ID, 0) = :spId
            """,nativeQuery = true)
    public Page<OfferSubsPlanProjection> getListSubsPlanByOfferGroupId(@Param("offerGroupId")Integer offerGroupId,
                                                                       @Param("spId")Integer spId, Pageable pageable);
}
