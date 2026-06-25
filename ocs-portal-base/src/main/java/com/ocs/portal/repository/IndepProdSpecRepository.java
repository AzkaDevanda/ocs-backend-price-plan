package com.ocs.portal.repository;

import com.ocs.portal.dto.request.offer.OfferVerForSubsPlanDto;
import com.ocs.portal.entity.IndepProdSpec;
import com.ocs.portal.projection.balanceAdjustment.SelectIndepProdSpecProjection;
import com.ocs.portal.projection.offer.SubsPlanByIndepProdProjection;
import com.ocs.portal.projection.pricePlan.IndepProdSpecProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IndepProdSpecRepository extends JpaRepository<IndepProdSpec, Long> {

    @Query(value = """
            SELECT
                A.INDEP_PROD_SPEC_ID AS indepProdSpecId,
                A.BRAND_PRICE_PLAN_ID AS brandPricePlanId,
                A.SERV_TYPE AS servType,
                A.PAID_FLAG AS paidFlag,
                AST.NETWORK_TYPE AS networkType,
                AST.PAID_FLAG AS servPaidFlag,
                B.OFFER_ID AS offerId,
                B.OFFER_NAME AS offerName,
                B.OFFER_CODE AS offerCode,
                B.SALE_LIST_PRICE AS saleListPrice,
                B.RENT_LIST_PRICE AS rentListPrice,
                B.EFF_DATE AS effDate,
                B.EXP_DATE AS expDate,
                B.CREATED_DATE AS createdDate,
                B.STATE AS state,
                B.STATE_DATE AS stateDate,
                B.EFF_TYPE AS effType,
                B.AUTO_CONTINUE_FLAG AS autoContinueFlag,
                B.CYCLE_QUANTITY AS cycleQuantity,
                B.TIME_UNIT AS timeUnit,
                B.DUPLICATE_FLAG AS duplicateFlag,
                B.COMMENTS AS comments,
                B.PROD_TYPE AS prodType,
                C.LIFECYCLE_TYPE AS lifecycleType
            FROM
                INDEP_PROD_SPEC A
            JOIN
                OFFER B ON A.INDEP_PROD_SPEC_ID = B.OFFER_ID
            LEFT JOIN
                LIFECYCLE_APPLY C ON A.INDEP_PROD_SPEC_ID = C.OFFER_ID
            JOIN
                ALL_SERV_TYPE AST ON A.SERV_TYPE = AST.SERV_TYPE
            WHERE
                B.STATE = 'A'
                AND A.INDEP_PROD_SPEC_ID = :indepProdSpecId
                AND NVL(A.SP_ID, 0) = :spId
            """, nativeQuery = true)
    List<IndepProdSpecProjection> findProductDetails(
            @Param("indepProdSpecId") Long indepProdSpecId,
            @Param("spId") Integer spId);

    @Query(value = """
            SELECT
                A.SUBS_PLAN_ID AS subsPlanId,
                A.INDEP_PROD_SPEC_ID AS indepProdSpecId,
                A.PRIORITY AS priority,
                A.SALE_FLAG AS saleFlag,
                A.IS_BUNDLE_FLAG AS isBundleFlag,
                A.SP_ID AS subsPlanSpId,
                AO.OFFER_ID AS offerId,
                AO.OFFER_TYPE AS offerType,
                AO.OFFER_NAME AS offerName,
                AO.COMMENTS AS comments,
                AO.OFFER_CODE AS offerCode,
                AO.SALE_LIST_PRICE AS saleListPrice,
                AO.RENT_LIST_PRICE AS rentListPrice,
                AO.EFF_DATE AS effDate,
                AO.EXP_DATE AS expDate,
                AO.CREATED_DATE AS createdDate,
                AO.STATE AS state,
                AO.EFF_TYPE AS effType,
                AO.EXP_OFF AS expOff,
                AO.TIME_UNIT AS timeUnit,
                AO.AUTO_CONTINUE_FLAG AS autoContinueFlag,
                AO.CYCLE_QUANTITY AS cycleQuantity,
                AO.DUPLICATE_FLAG AS duplicateFlag,
                AO.SP_ID AS offerSpId,
                AO.EXP_TIME_UNIT AS expTimeUnit,
                AO.AGREEMENT_EFF_TYPE AS agreementEffType,
                AO.SALES_RULE_SCRIPT AS salesRuleScript,
                AO.PROD_TYPE AS prodType
            FROM SUBS_PLAN A
            JOIN OFFER AO ON AO.OFFER_ID = A.SUBS_PLAN_ID
            WHERE AO.STATE = 'A'
              AND A.INDEP_PROD_SPEC_ID = :indepProdSpecId
            ORDER BY A.PRIORITY, AO.OFFER_NAME
            """, nativeQuery = true)
    List<SubsPlanByIndepProdProjection> findSubsPlanByIndepProdId(@Param("indepProdSpecId") Long indepProdSpecId);

    // @Query(value = """
    // SELECT
    // O.OFFER_VER_ID AS id,
    // O.SEQ AS seq,
    // O.EFF_DATE AS effDate,
    // O.OFFER_ID AS offerId
    // FROM OFFER_VER O
    // WHERE O.OFFER_ID = :offerId
    // """, nativeQuery = true)
    // List<OfferVerForSubsPlanDto> findOfferVerByOfferId(@Param("offerId") Integer
    // offerId);

    @Query("SELECT new com.sts.sinorita.dto.request.offer.OfferVerForSubsPlanDto(o.id, o.seq, o.effDate, o.offerId) FROM OfferVer o WHERE o.offerId = :offerId")
    List<OfferVerForSubsPlanDto> findOfferVerByOfferId(@Param("offerId") Integer offerId);

    @Query(value = "SELECT INDEP_PROD_SPEC_ID AS indepProdSpecId, SERV_TYPE AS servType, PAID_FLAG AS paidFlag, SP_ID AS spId FROM INDEP_PROD_SPEC WHERE INDEP_PROD_SPEC_ID = :indepProdSpecId", nativeQuery = true)
    Optional<SelectIndepProdSpecProjection> selectIndepProdSpec(@Param("indepProdSpecId") Long indepProdSpecId);

}
