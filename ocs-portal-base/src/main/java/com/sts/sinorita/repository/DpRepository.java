package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Dp;
import com.sts.sinorita.projection.pricePlan.discount.DpProjection;
import com.sts.sinorita.projection.pricePlan.discount.QryDpByPkProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DpRepository extends JpaRepository<Dp, Integer> {

    @Query(value = "SELECT NVL(MAX(t.PRIORITY) + 1, 1) FROM DP t", nativeQuery = true)
    Integer findNextPriority();

    @Query("""
    SELECT CASE WHEN COUNT(d) > 0 THEN 1 ELSE 0 END
    FROM Dp d
    JOIN OfferVer ov ON d.offerVerId = ov.id
    JOIN RePricePlan rpp ON ov.id = rpp.id.offerVerId\s
    JOIN RatePlan rp ON rpp.id.offerVerId = rp.offerVerId
    JOIN PriceVer pv ON rp.id = pv.ratePlanId
    WHERE pv.id = :priceVerId
   \s""")
    int existByPriceVerId(@Param("priceVerId") Integer priceVerId);

    @Query(value = """
        SELECT 
            A.DP_ID              AS dpId,
            A.DP_NAME            AS dpName,
            A.PRIORITY           AS priority,
            A.DP_TYPE            AS dpType,
            B.DP_TYPE_NAME       AS dpTypeName,
            A.OFFER_VER_ID       AS pricePlanVerId,
            A.BILLING_PLAN_TYPE  AS billingPlanType,
            A.COMMENTS           AS comments
        FROM DP A
        JOIN DP_TYPE B ON A.DP_TYPE = B.DP_TYPE
        WHERE 1 = 1
          AND (:offerVerId IS NULL OR A.OFFER_VER_ID = :offerVerId)
          AND (:billingPlanType IS NULL OR A.BILLING_PLAN_TYPE = :billingPlanType)
          AND (:billingPlanTypes IS NULL OR A.BILLING_PLAN_TYPE IN (:billingPlanTypes))
          AND (:dpIdSelf IS NULL OR A.DP_ID <> :dpIdSelf)
          AND (:spId IS NULL OR NVL(A.SP_ID,0) = :spId)
        ORDER BY A.PRIORITY
        """, nativeQuery = true)
    List<DpProjection> findDpList(
            @Param("offerVerId") Integer offerVerId,
            @Param("billingPlanType") String billingPlanType,
            @Param("billingPlanTypes") Character billingPlanTypes,
            @Param("dpIdSelf") Integer dpIdSelf,
            @Param("spId") Integer spId
    );

    @Modifying
    @Transactional
    @Query(value = "delete from DP where DP_ID = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);

    @Query(value = """
    SELECT A.DP_ID,
           A.OFFER_VER_ID AS pricePlanVerId,
           A.DP_TYPE,
           A.DP_NAME,
           A.COMMENTS,
           A.PRIORITY,
           B.DP_TYPE_NAME,
           C.ACCT_ITEM_TYPE_ID AS dpRuleAcctItemTypeId,
           G.ACCT_ITEM_TYPE_NAME AS dpRuleAcctItemTypeName,
           C.RULE_SCRIPT,
           C.SCRIPT_PAGE,
           C.RULE_COMMENTS,
           C.SCRIPT_TEMPLET_ID,
           D.CALC_TYPE,
           D.VALUE,
           D.RESULT_ACCT_ITEM_TYPE_ID,
           E.CALC_TYPE_NAME,
           F.ACCT_ITEM_TYPE_NAME AS resultAcctItemTypeName,
           H.IS_CURRENCY,
           A.BILLING_PLAN_TYPE
      FROM DP A,
           DP_TYPE B,
           DP_RULE C,
           REF_DP D,
           CALC_TYPE E,
           ACCT_ITEM_TYPE F,
           ACCT_ITEM_TYPE G,
           ACCT_RES H
     WHERE A.DP_TYPE = B.DP_TYPE
       AND A.DP_ID = C.DP_ID(+)
       AND A.DP_ID = D.DP_ID(+)
       AND D.CALC_TYPE = E.CALC_TYPE(+)
       AND D.RESULT_ACCT_ITEM_TYPE_ID = F.ACCT_ITEM_TYPE_ID(+)
       AND F.ACCT_RES_ID = H.ACCT_RES_ID(+)
       AND C.ACCT_ITEM_TYPE_ID = G.ACCT_ITEM_TYPE_ID(+)
       AND A.DP_ID = :dpId
       AND NVL(A.SP_ID,0) = :spId
    """, nativeQuery = true)
    List<QryDpByPkProjection> QryDpByPk(@Param("dpId") Integer dpId, @Param("spId") Integer spId);

    @Query(value = """
           SELECT *
           FROM DP
           WHERE DP_ID = :dpId
           """, nativeQuery = true)
    Optional<Dp> selectDp(@Param("dpId") Integer dpId);
}
