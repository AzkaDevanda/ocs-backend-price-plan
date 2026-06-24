package com.sts.sinorita.repository;

import com.sts.sinorita.entity.InstalmentType;
import com.sts.sinorita.projection.accountConfig.QryInstalmentType1Projection;
import com.sts.sinorita.projection.accountConfig.QryInstalmentTypeDetailProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface InstalmentTypeRepository extends JpaRepository<InstalmentType, Long> {

    @Query(value = "SELECT NVL(MAX(INSTALMENT_TYPE_ID), 0) + 1 FROM INSTALMENT_TYPE", nativeQuery = true)
    Long getNextId();

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO INSTALMENT_TYPE
                (INSTALMENT_TYPE_ID, INSTALMENT_TYPE_NAME, FIRST_PAY, STATE, STATE_DATE, COMMENTS, SP_ID, FEE_PERCENT)
            VALUES
                (INSTALMENT_TYPE_SEQ.NEXTVAL, :name, :firstPay, 'A', SYSDATE, :comments, 0, :feePercent)
            """, nativeQuery = true)
    void insertInstalmentType(
            @Param("name") String name,
            @Param("firstPay") Double firstPay,
            @Param("comments") String comments,
            @Param("feePercent") Double feePercent);

    @Query(value = """
            SELECT\s
                A.INSTALMENT_TYPE_ID as instalmentTypeId,\s
                A.INSTALMENT_TYPE_NAME as instalmentTypeName,\s
                A.FIRST_PAY as firstPay,\s
                NVL(SUM(B.REPEAT_TIME), 0) AS repeatTimes,\s
                A.COMMENTS as comments,\s
                A.FEE_PERCENT as feePercent\s
            FROM\s
                INSTALMENT_TYPE A\s
                LEFT JOIN INSTALMENT_ITEM B\s
                    ON A.INSTALMENT_TYPE_ID = B.INSTALMENT_TYPE_ID
            WHERE\s
                (:STATE IS NULL OR A.STATE = :STATE)
                AND (:SP_ID IS NULL OR NVL(A.SP_ID, 0) = :SP_ID)
                AND (
                    :IS_FIXED_FIRST_PAY IS NULL\s
                    OR (:IS_FIXED_FIRST_PAY = 'Y' AND (A.FIRST_PAY = 0 OR A.FIRST_PAY IS NULL))
                )
            GROUP BY\s
                A.INSTALMENT_TYPE_ID,\s
                A.INSTALMENT_TYPE_NAME,\s
                A.FIRST_PAY,
                A.COMMENTS,
                A.FEE_PERCENT
            ORDER BY\s
                repeatTimes ASC
            """, nativeQuery = true)
    Page<QryInstalmentType1Projection> QryInstalmentType1(@Param("STATE") Character state,
            @Param("SP_ID") Integer spId,
            @Param("IS_FIXED_FIRST_PAY") String isFixedFirstPay,
            Pageable pageable);

    @Query(value = "SELECT i.INSTALMENT_TYPE_ID AS id, i.INSTALMENT_TYPE_NAME AS instalmentTypeName, i.FIRST_PAY AS firstPay, i.STATE AS state, i.STATE_DATE AS stateDate, i.COMMENTS AS comments, i.SP_ID AS spId, i.FEE_PERCENT AS feePercent FROM INSTALMENT_TYPE i WHERE i.INSTALMENT_TYPE_ID = :instalmentTypeId", nativeQuery = true)
    Optional<QryInstalmentTypeDetailProjection> findByInstalmentTypeId(Long instalmentTypeId);

    @Query(value = "SELECT 1 FROM RE_CC_INST WHERE INSTALMENT_TYPE_ID = :instalmentTypeId AND ROWNUM = 1", nativeQuery = true)
    Optional<Integer> isReferencedInReCcInst(@Param("instalmentTypeId") Long instalmentTypeId);

    @Query(value = "SELECT 1 FROM INSTALMENT_ITEM WHERE INSTALMENT_TYPE_ID = :instalmentTypeId AND ROWNUM = 1", nativeQuery = true)
    Optional<Integer> isReferencedInInstalmentItem(@Param("instalmentTypeId") Long instalmentTypeId);

    @Query(value = "SELECT 1 FROM INSTALMENT_TYPE_ITEM WHERE INSTALMENT_TYPE_ID = :instalmentTypeId AND ROWNUM = 1", nativeQuery = true)
    Optional<Integer> isReferencedInInstalmentTypeItem(@Param("instalmentTypeId") Long instalmentTypeId);

    @Query(value = "SELECT 1 FROM OVERDUE_PLAN WHERE INSTALMENT_TYPE_ID = :instalmentTypeId AND ROWNUM = 1", nativeQuery = true)
    Optional<Integer> isReferencedInOverduePlan(@Param("instalmentTypeId") Long instalmentTypeId);

    @Query(value = "SELECT 1 FROM SHOPPING_CART_OFFER WHERE INSTALMENT_TYPE_ID = :instalmentTypeId AND ROWNUM = 1", nativeQuery = true)
    Optional<Integer> isReferencedInShoppingCartOffer(@Param("instalmentTypeId") Long instalmentTypeId);

    @Query(value = "SELECT 1 FROM ORDER_ITEM WHERE INSTALMENT_TYPE_ID = :instalmentTypeId AND ROWNUM = 1", nativeQuery = true)
    Optional<Integer> isReferencedInOrderItem(@Param("instalmentTypeId") Long instalmentTypeId);

    @Query(value = "SELECT 1 FROM INSTALLMENT WHERE INSTALLMENT_TYPE_ID = :instalmentTypeId AND ROWNUM = 1", nativeQuery = true)
    Optional<Integer> isInstalmentTypeReferencedByInstallment(@Param("instalmentTypeId") Long instalmentTypeId);

}
