package com.sts.sinorita.repository;

import com.sts.sinorita.dto.response.priceVer.PriceProjection;
import com.sts.sinorita.projection.pricePlan.price.GetRecurringPriceDetailProjection;
import com.sts.sinorita.projection.pricePlan.price.PriceInfoProjection;
import com.sts.sinorita.projection.pricePlan.price.PriceRatingProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sts.sinorita.entity.Price;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT COUNT(p) FROM Price p WHERE p.priceVerId = :priceVerId")
    int countPriceByPriceVerId(@Param("priceVerId") Integer priceVerId);

    @Query(value = "SELECT r.RE_TYPE AS reType, pv.PRICE_VER_ID as priceVerID, pv.EFF_DATE as effDate,pv.EXP_DATE as expDate, p.PRICE_ID as priceId, p.PRICE_NAME as priceName, rv.VALUE_STRING as valueString," +
            " p.RUM as rum, ai.ACCT_ITEM_TYPE_NAME as acctItemTypeName, ra.RE_ATTR_NAME as reAttrName FROM PRICE P\n" +
            "INNER JOIN PRICE_VER pv ON p.PRICE_VER_ID = pv.PRICE_VER_ID\n" +
            "INNER JOIN RATE_PLAN rp ON pv.RATE_PLAN_ID = rp.RATE_PLAN_ID\n" +
            "LEFT JOIN RE_ATTR ra ON p.RE_ATTR = ra.RE_ATTR \n" +
            "JOIN RE r ON rp.RE_ID  = r.RE_ID \n" +
            "LEFT JOIN ACCT_ITEM_TYPE ai ON p.ACCT_ITEM_TYPE_ID = ai.ACCT_ITEM_TYPE_ID\n" +
            "INNER JOIN REF_VALUE rv ON p.VALUE = rv.REF_VALUE_ID \n" +
            "WHERE rp.RATE_PLAN_ID = :ratePlanId ORDER BY pv.EFF_DATE DESC", nativeQuery = true)
    List<PriceProjection> findPriceVerByRatePlanId(@Param("ratePlanId") Integer ratePlanId);

    @Query("SELECT p FROM Price p WHERE p.priceVerId = :priceVerId")
    List<Price> findByPriceVerId(@Param("priceVerId") Integer priceVerId);

    @Query(value = "SELECT p.* FROM price p " +
            "JOIN price_ver pv ON p.price_ver_id = pv.price_ver_id " +
            "JOIN rate_plan rp ON pv.rate_plan_id = rp.rate_plan_id " +
            "JOIN re_price_plan rpp ON rp.offer_ver_id = rpp.offer_ver_id " +
            "JOIN offer_ver ov ON rpp.offer_ver_id = ov.offer_ver_id " +
            "WHERE ov.offer_ver_id = :offerVerId",
            nativeQuery = true)
    Optional<Price> findPricesByOfferVerId(@Param("offerVerId") Integer offerVerId);

    @Transactional
    @Modifying
    @Query("UPDATE Price p SET p.priority = p.priority + :addNum " +
            "WHERE p.priceVerId = :priceVerId " +
            "AND p.priority BETWEEN :beginPriority AND :endPriority")
    int updatePricePriorityByArr(@Param("addNum") Integer addNum,
                                 @Param("beginPriority") Integer beginPriority,
                                 @Param("endPriority") Integer endPriority,
                                 @Param("priceVerId") Integer priceVerId);

    @Transactional
    @Modifying
    @Query("UPDATE Price p SET p.priority = :newPriority WHERE p.id = :priceId")
    int updatePricePriority(@Param("priceId") Long priceId,
                            @Param("newPriority") Integer newPriority);

    int countByPriceVerId(Integer priceVerId);

    @Query("SELECT COALESCE(MAX(p.priority), 0) + 1 FROM Price p WHERE p.priceVerId = :priceVerId")
    int getNextPriority(@Param("priceVerId") Integer priceVerId);


//    @Modifying
//    @Query("DELETE FROM Price p WHERE p.priceVerId = :priceVerId")
//    void deletePriceByPriceVerId(@Param("priceVerId") Long priceVerId);

    @Modifying
    @Query("DELETE FROM Price p WHERE p.id = :priceId")
    void deleteByPriceId(@Param("priceId") Long priceId);

    @Query(value = """
            SELECT \s
                    p.id AS priceId,
                    p.priceName AS priceName,
                    rv.valueString AS value,
                    p.payIndicator AS payIndicator,
                    p.rum AS rum,
                    p.acctItemTypeId AS acctItemTypeId,
                    p.comments AS remarks,
                    p.reAttr AS reAttr,
                    v.effDate AS effDate,
                    v.expDate AS expDate
                FROM Price p
                JOIN PriceVer v ON p.priceVerId = v.id
                LEFT JOIN RefValue rv ON p.value = rv.id
                WHERE p.id = :id
            """)
    Optional<PriceRatingProjection> getPriceDetailById(@Param("id") Long id);

    @Query(value = """
            SELECT
                price.id AS priceId,
                price.priceName AS priceName,
                priceVer.effDate AS effDate,
                priceVer.expDate AS expDate,
                refValue.valueString AS valueString,
                acctItemType.id AS acctItemTypeId,
                acctItemType.acctItemTypeName AS acctItemTypeName,
                price.rum AS calculateUnit,
                price.comments AS comments,
                price.ratePrecision AS ratePrecision,
                price.calcPrecision AS calcPrecision,
                price.creditLimit AS creditLimit,
                price.priority AS priority,
                price.payIndicator AS payIndicator,
                rp.scriptTempletId AS scriptTempletId,
                rp.ruleScript AS ruleScript,
                rp.ruleComments AS ruleComments,
                rp.param AS param,
                rp.scriptPage AS scriptPage,
                rp.configType AS configType
            FROM
                Price price
            JOIN RefValue refValue ON
                price.value = refValue.id
            LEFT JOIN ReAttr reAttr ON
                price.reAttr = reAttr.id
            JOIN PriceVer priceVer ON
                price.priceVerId = priceVer.id
            JOIN AcctItemType acctItemType ON
                price.acctItemTypeId = acctItemType.id
            LEFT JOIN Rp rp ON
                price.id = rp.id
            WHERE
                price.id = :id
            """)
    Optional<GetRecurringPriceDetailProjection> getRecurringPriceDetail(@Param("id") Long priceId);


    @Modifying
    @Transactional
    @Query("UPDATE Price set acctItemTypeIdParam = :acctItemTypeIdParam where id = :id")
    void insertAcctItemTypeIdParam(@Param("acctItemTypeIdParam") Integer acctItemTypeIdParam, @Param("id") int id);

    @Query(value = "SELECT COUNT(A.PRICE_ID) FROM PRICE A WHERE A.PRICE_VER_ID = :priceVerId", nativeQuery = true)
    int selectPriceCountByPriceVer(@Param("priceVerId") Integer priceVerId);

    @Query(value = "SELECT PRICE_ID_SEQ.NEXTVAL FROM dual", nativeQuery = true)
    public Integer selectMaxSeq();

    @Query(value = """
    SELECT A.PRICE_ID AS priceId,
           A.PRICE_NAME AS priceName,
           A.PAY_INDICATOR AS payIndicator,
           A.ACCT_ITEM_TYPE_ID AS priceAcctItemTypeId,
           Q.VALUE_STRING AS value,
           A.CALC_PRECISION AS calcPrecision,
           A.RUM AS rum,
           A.RE_ATTR AS reAttr,
           E.RE_ATTR_NAME AS reAttrName,
           A.COMMENTS AS comments,
           A.PRICE_VER_ID AS priceVerId,
           A.CALC_DIS_AIT_ID AS calcDisAitId,
           A.PARENT_PRICE_ID AS parentPriceId,
           D.MAPPING_ID AS mappingId,
           D.EFF_DATE AS effDate,
           D.EXP_DATE AS expDate,
           NVL(S.ACCT_ITEM_TYPE_NAME,B.ACCT_ITEM_TYPE_NAME) AS acctItemTypeName,
           NVL(R.VALUE_STRING,A.ACCT_ITEM_TYPE_ID) AS acctItemTypeId,
           NVL(T.ACCT_RES_NAME,C.ACCT_RES_NAME) AS acctResName,
           NVL(T.IS_CURRENCY,C.IS_CURRENCY) AS isCurrency,
           A.CREDIT_LIMIT AS creditLimit,
           A.PRIORITY AS priority,
           A.RATE_PRECISION AS ratePrecision,
           F.DEPOSIT_TYPE_ID AS depositTypeId,
           G.NAME AS depositTypeName,
           O.PARAM AS param,
           A.SHARE_FLAG AS shareFlag,
           D.RATE_PLAN_ID AS ratePlanId,
           P.RATE_PLAN_TYPE AS ratePlanType,
           Q.OFFER_VER_ID AS offerVerId,
           A.ACCT_ITEM_TYPE_ID_PARAM AS acctItemTypeIdParam,
           R.VALUE_STRING AS valueString
    FROM PRICE A
    LEFT JOIN ACCT_ITEM_TYPE B ON A.ACCT_ITEM_TYPE_ID = B.ACCT_ITEM_TYPE_ID
    LEFT JOIN ACCT_RES C ON B.ACCT_RES_ID = C.ACCT_RES_ID
    LEFT JOIN PRICE_VER D ON A.PRICE_VER_ID = D.PRICE_VER_ID
    LEFT JOIN REF_VALUE R ON A.ACCT_ITEM_TYPE_ID_PARAM = R.REF_VALUE_ID
    LEFT JOIN ACCT_ITEM_TYPE S ON R.VALUE_STRING = TO_CHAR(S.ACCT_ITEM_TYPE_ID)
    LEFT JOIN ACCT_RES T ON S.ACCT_RES_ID = T.ACCT_RES_ID
    LEFT JOIN RATE_PLAN P ON D.RATE_PLAN_ID = P.RATE_PLAN_ID
    LEFT JOIN RE_ATTR E ON A.RE_ATTR = E.RE_ATTR
    LEFT JOIN DEPOSIT_PRICE F ON A.PRICE_ID = F.PRICE_ID
    LEFT JOIN DEPOSIT_TYPE G ON F.DEPOSIT_TYPE_ID = G.DEPOSIT_TYPE_ID
    LEFT JOIN OP O ON A.PRICE_ID = O.OP_ID
    LEFT JOIN REF_VALUE Q ON A.VALUE = Q.REF_VALUE_ID
    WHERE D.RATE_PLAN_ID = :ratePlanId
      AND (:mappingId IS NULL OR D.MAPPING_ID = :mappingId)
      AND (:priceVerId IS NULL OR A.PRICE_VER_ID = :priceVerId)
      AND (:priceId IS NULL OR A.PRICE_ID = :priceId)
      AND (:priceIdSelf IS NULL OR A.PRICE_ID <> :priceIdSelf)
      AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId)
      AND (:parentPriceId IS NULL OR NVL(A.PARENT_PRICE_ID, 0) = :parentPriceId)
      AND (:srcPriceId IS NULL OR A.SRC_PRICE_ID = :srcPriceId)
      AND (:shareFlag IS NULL OR A.SHARE_FLAG = :shareFlag)
    """, nativeQuery = true)
    List<PriceInfoProjection> findPricesByRatePlanId(
            @Param("ratePlanId") Integer ratePlanId,
            @Param("mappingId") Integer mappingId,
            @Param("priceVerId") Integer priceVerId,
            @Param("priceId") Long priceId,
            @Param("priceIdSelf") Long priceIdSelf,
            @Param("spId") Integer spId,
            @Param("parentPriceId") Integer parentPriceId,
            @Param("srcPriceId") Integer srcPriceId,
            @Param("shareFlag") Character shareFlag
    );

    boolean existsByAcctItemTypeId(Integer acctItemTypeId);
}
