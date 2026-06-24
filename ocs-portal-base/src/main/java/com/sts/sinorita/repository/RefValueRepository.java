package com.sts.sinorita.repository;

import com.sts.sinorita.dto.request.priceplan.RefValueDto;
import com.sts.sinorita.entity.RefValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefValueRepository extends JpaRepository<RefValue, Integer> {

    @Query(value = "select rf from RefValue rf where rf.priceId=:priceId")
    List<RefValue> findByPriceId(@Param("priceId")Long priceId);

    @Query(value = "SELECT rf FROM RefValue rf where rf.valueString = :valueString")
    Optional<RefValue> selectRefValueByValueString(@Param("valueString") Integer valueString);

    @Query(value = "SELECT rf FROM RefValue rf where rf.valueString = :valueString")
    Optional<RefValue> selectRefValueByValueString222(@Param("valueString") String valueString);

    @Modifying
    @Query("UPDATE RefValue r SET r.priceId = :priceId where r.id = :id")
    Void insertPriceId(@Param("priceId") Long priceId, @Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("update RefValue set priceId = :id where id = :refValueId")
    void insertValue(@Param("id") Integer id, @Param("refValueId") Integer refValueId);

    @Query("SELECT rf from RefValue rf where rf.offerVerId = :offerVerId and rf.valueString =:valueString and rf.state ='A' " )
    public List<RefValue> findByOfferIdAndValueString(@Param("offerVerId") Integer offerVerId, @Param("valueString")String valueString);

    @Query(value = """
        SELECT
            A.REF_VALUE_ID                     AS refValueId,
            A.REF_VALUE_TYPE                   AS refValueType,
            A.VALUE_STRING                     AS valueString,
            A.SIMPLE_PARAM_ID                  AS simpleParamId,
            A.TABLE_PARAM_ID                   AS tableParamId,
            A.COL_ID                           AS colId,
            A.RE_ATTR                          AS reAttr,
            A.PARAM_VALUE_TYPE                 AS paramValueType,
            A.COMMENTS                         AS comments,
            A.STAFF_ID                         AS staffId,
            A.CREATED_DATE                     AS createdDate,
            A.STATE                            AS state,
            A.STATE_DATE                       AS stateDate,
            A.SP_ID                            AS spId,
            A.COST_VALUE_TYPE                  AS costValueType,
            A.RATE_PRECISION                   AS ratePrecision,
            A.PRICE_ID                         AS priceId,
            A.RATE_PLAN_ID                     AS ratePlanId,
            B.OFFER_VER_ID                     AS offerVerId,
            A.PROMO_DISC_ACCT_ITEM_TYPE_ID     AS promoDiscAcctItemTypeId
        FROM REF_VALUE A
        JOIN REF_VALUE_TO_OFFER_VER B
            ON A.REF_VALUE_ID = B.REF_VALUE_ID
        WHERE A.REF_VALUE_ID = :refValueId
        """, nativeQuery = true)
    RefValueDto qryRefValue(
            @Param("refValueId") Long refValueId
    );

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE REF_VALUE
        SET STATE = :state,
            STATE_DATE = SYSDATE
        WHERE REF_VALUE_ID = :refValueId
        """, nativeQuery = true)
    int updateState(
            @Param("refValueId") Long refValueId,
            @Param("state") String state);


    @Query(value = """
        SELECT
            A.REF_VALUE_ID                 AS refValueId,
            A.REF_VALUE_TYPE               AS refValueType,
            A.VALUE_STRING                 AS valueString,
            A.SIMPLE_PARAM_ID              AS simpleParamId,
            A.TABLE_PARAM_ID               AS tableParamId,
            A.COL_ID                       AS colId,
            A.RE_ATTR                      AS reAttr,
            A.PARAM_VALUE_TYPE             AS paramValueType,
            A.COMMENTS                     AS comments,
            A.STAFF_ID                     AS staffId,
            A.CREATED_DATE                 AS createdDate,
            A.STATE                        AS state,
            A.STATE_DATE                   AS stateDate,
            A.SP_ID                        AS spId,
            A.COST_VALUE_TYPE              AS costValueType,
            A.RATE_PRECISION               AS ratePrecision,
            A.PRICE_ID                     AS priceId,
            A.RATE_PLAN_ID                 AS ratePlanId,
            B.OFFER_VER_ID                 AS offerVerId,
            A.PROMO_DISC_ACCT_ITEM_TYPE_ID AS promoDiscAcctItemTypeId
        FROM REF_VALUE A
        JOIN REF_VALUE_TO_OFFER_VER B
          ON A.REF_VALUE_ID = B.REF_VALUE_ID
        WHERE (A.SIMPLE_PARAM_ID = :paramId
            OR A.TABLE_PARAM_ID  = :paramId)
          AND A.PRICE_ID = :priceId
        """, nativeQuery = true)
    RefValueDto[] qryRefValueByParamIdAndPriceId(
            @Param("paramId") Long paramId,
            @Param("priceId") Long priceId);

    @Query(value = """
        SELECT
            A.REF_VALUE_ID                 AS refValueId,
            A.REF_VALUE_TYPE               AS refValueType,
            A.VALUE_STRING                 AS valueString,
            A.SIMPLE_PARAM_ID              AS simpleParamId,
            A.TABLE_PARAM_ID               AS tableParamId,
            A.COL_ID                       AS colId,
            A.RE_ATTR                      AS reAttr,
            A.PARAM_VALUE_TYPE             AS paramValueType,
            A.COMMENTS                     AS comments,
            A.STAFF_ID                     AS staffId,
            A.CREATED_DATE                 AS createdDate,
            A.STATE                        AS state,
            A.STATE_DATE                   AS stateDate,
            A.SP_ID                        AS spId,
            A.COST_VALUE_TYPE              AS costValueType,
            A.RATE_PRECISION               AS ratePrecision,
            A.PRICE_ID                     AS priceId,
            A.RATE_PLAN_ID                 AS ratePlanId,
            B.OFFER_VER_ID                 AS offerVerId,
            A.PROMO_DISC_ACCT_ITEM_TYPE_ID AS promoDiscAcctItemTypeId
        FROM REF_VALUE A
        JOIN REF_VALUE_TO_OFFER_VER B
          ON A.REF_VALUE_ID = B.REF_VALUE_ID
        WHERE (A.SIMPLE_PARAM_ID = :paramId
            OR A.TABLE_PARAM_ID  = :paramId)
          AND A.RATE_PLAN_ID = :ratePlanId
        """, nativeQuery = true)
    RefValueDto[] qryRefValueByParamIdAndRatePlanId(
            @Param("paramId") Long paramId,
            @Param("ratePlanId") Long ratePlanId);

    @Query(value = """
        SELECT
            A.REF_VALUE_ID              AS refValueId,
            A.REF_VALUE_TYPE            AS refValueType,
            A.VALUE_STRING              AS valueString,
            A.SIMPLE_PARAM_ID           AS simpleParamId,
            A.TABLE_PARAM_ID            AS tableParamId,
            A.COL_ID                    AS colId,
            A.RE_ATTR                   AS reAttr,
            A.PARAM_VALUE_TYPE          AS paramValueType,
            A.COMMENTS                  AS comments,
            A.STAFF_ID                  AS staffId,
            A.CREATED_DATE              AS createdDate,
            A.STATE                     AS state,
            A.STATE_DATE                AS stateDate,
            A.SP_ID                     AS spId,
            A.COST_VALUE_TYPE           AS costValueType,
            A.RATE_PRECISION            AS ratePrecision,
            A.PRICE_ID                  AS priceId,
            A.RATE_PLAN_ID              AS ratePlanId,
            B.OFFER_VER_ID              AS offerVerId,
            A.PROMO_DISC_ACCT_ITEM_TYPE_ID AS promoDiscAcctItemTypeId
        FROM REF_VALUE A
        JOIN REF_VALUE_TO_OFFER_VER B
          ON A.REF_VALUE_ID = B.REF_VALUE_ID
        WHERE (A.SIMPLE_PARAM_ID = :paramId
               OR A.TABLE_PARAM_ID = :paramId)
          AND B.OFFER_VER_ID = :offerVerId
        """, nativeQuery = true)
    RefValueDto[] qryRefValueByParamIdAndOfferVerId(
            @Param("paramId") Long paramId,
            @Param("offerVerId") Long offerVerId);


    @Query(value = """
        SELECT COUNT(DISTINCT B.OFFER_VER_ID)
        FROM REF_VALUE A
        JOIN REF_VALUE_TO_OFFER_VER B
          ON A.REF_VALUE_ID = B.REF_VALUE_ID
        WHERE A.SIMPLE_PARAM_ID = :paramId
           OR A.TABLE_PARAM_ID  = :paramId
        """, nativeQuery = true)
    Long qryRefCntByMultipleOfferVer(@Param("paramId") Long paramId);
}
