package com.ocs.portal.repository;

import com.ocs.portal.dto.request.BalTypeNameDto;
import com.ocs.portal.entity.BalType;
import com.ocs.portal.projection.accountConfig.QryBalTypeProjection;
import com.ocs.portal.projection.acct.BalTypeAcctResProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalTypeRepository extends JpaRepository<BalType, Integer> {

    @Query(value = """
            SELECT
            	A.BAL_TYPE AS balType,
            	A.BAL_TYPE_NAME AS balTypeName,
            	A.COMMENTS AS comments
            FROM
            	BAL_TYPE A
            WHERE
            	1 = 1
            	AND NOT EXISTS (
            	SELECT
            		1
            	FROM
            		ACCT_RES B
            	WHERE
            		A.BAL_TYPE = B.BAL_TYPE
            		AND (:BAL_TYPE_FLAG IS NULL
            			OR B.BAL_TYPE =:BAL_TYPE_FLAG)
            		AND (TO_CHAR(B.ACCT_RES_ID) IN (
            		SELECT
            			NVL(PARAM_VALUE, DEFAULT_VALUE)
            		FROM
            			CONFIG_ITEM_PARAM
            		WHERE
            			PARAM_CODE = 'DEFAULT_ACCT_RES_ID')
            		OR NVL(B.SP_ID, 0) = :SP_ID))
            	AND (:BAL_TYPE IS NULL
            		OR A.BAL_TYPE = :BAL_TYPE)
            	ORDER BY A.BAL_TYPE_NAME
            """,nativeQuery = true)
    List<QryBalTypeProjection> qryBalType(@Param("BAL_TYPE") Integer balType,
                                          @Param("BAL_TYPE_FLAG") Integer balTypeFlag,
                                          @Param("SP_ID") Integer spId);


    @Query(value = """
            SELECT
            	A.BAL_TYPE AS balType,
            	A.BAL_TYPE_NAME AS balTypeName,
            	A.COMMENTS AS comments
            FROM
            	BAL_TYPE A
            """,nativeQuery = true)
    List<QryBalTypeProjection> findBalType();

    @Query(value = """
    SELECT 
        A.BAL_TYPE AS balType,
        A.BAL_TYPE_NAME AS balTypeName,
        B.ACCT_RES_ID AS acctResId,
        B.ACCT_RES_NAME AS acctResName,
        B.IS_CURRENCY AS isCurrency,
        B.COMMENTS AS comments,
        B.CREDIT_LIMIT AS creditLimit,
        B.MAX_VALUE AS maxValue,
        B.REFILLABLE AS refillable,
        B.STD_CODE AS stdCode,
        B.PAYMENT_FORCE AS paymentForce,
        B.PARENT_ACCT_RES_ID AS parentAcctResId,
        B.IS_FREE_UNIT AS isFreeUnit,
        B.DEFAULT_ACCT_ITEM_TYPE_ID AS defaultAcctItemTypeId,
        C.ACCT_RES_NAME AS parentAcctResName,
        D.ACCT_ITEM_TYPE_NAME AS acctItemTypeName,
        B.UNIT_TYPE_ID AS unitTypeId,
        B.UNIT_PRECISION AS unitPrecision,
        -- E.UNIT_TYPE_NAME AS unitTypeName,
        B.RATIO_MONEY AS ratioMoney,
        B.RATIO_PRECISION AS ratioPrecision,
        B.PRIORITY AS priority,
        DECODE(B.EXTEND_RULE, '000000','', B.EXTEND_RULE) AS extendRule,
        DECODE(B.EXTEND_RULE, 
               '210000', 'Best Rule', 
               '100000', 'Accumulation Rule', 
               '') AS extendRuleName,
        B.MAX_ADJUST_VALUE AS maxAdjustValue,
        B.MAX_CHG_VALUE AS maxChgValue,
        B.MAX_EXP_DATE AS maxExpDate,
        B.RESET_ZERO AS resetZero,
        B.GRACE_PERIOD AS gracePeriod,
        B.MAX_ROLLOVER AS maxRollover,
        DECODE(B.RESET_ZERO, 
               'Y', 'Clear bal after expired', 
               'N', 'Can not clear bal after expired') AS resetZeroName
    FROM BAL_TYPE A
    JOIN ACCT_RES B ON A.BAL_TYPE = B.BAL_TYPE
    LEFT JOIN ACCT_RES C ON B.PARENT_ACCT_RES_ID = C.ACCT_RES_ID
    LEFT JOIN ACCT_ITEM_TYPE D ON B.DEFAULT_ACCT_ITEM_TYPE_ID = D.ACCT_ITEM_TYPE_ID
    LEFT JOIN UNIT_TYPE E ON B.UNIT_TYPE_ID = E.UNIT_TYPE_ID
    WHERE (
        TO_CHAR(B.ACCT_RES_ID) IN (
            SELECT NVL(PARAM_VALUE, DEFAULT_VALUE)
            FROM CONFIG_ITEM_PARAM
            WHERE PARAM_CODE = 'DEFAULT_ACCT_RES_ID'
        )
        OR NVL(B.SP_ID, 0) = :spId
    )
    ORDER BY A.BAL_TYPE, B.ACCT_RES_ID
""", nativeQuery = true)
    Page<BalTypeAcctResProjection> findBalTypeChild(@Param("spId") Integer spId,
                                                    Pageable pageable);

}
