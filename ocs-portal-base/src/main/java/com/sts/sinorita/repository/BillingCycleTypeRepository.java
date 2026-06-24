package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BillingCycleType;
import com.sts.sinorita.projection.acct.BillingCycleTypeListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingCycleTypeRepository extends JpaRepository<BillingCycleType, Integer> {

  boolean existsByBillingCycleTypeNameAndSpId (String name, Integer spId);

  boolean existsByBillingCycleTypeCodeAndSpId (String code, Integer spId);

  @Query(value = """
          SELECT CAST(COALESCE(MAX(B.BILLING_CYCLE_TYPE_ID) + 1, 1) AS INTEGER) FROM BILLING_CYCLE_TYPE B
          """, nativeQuery = true)
  Integer getNextBillingCycleTypeId ();

  @Query(value = """
          SELECT A.BILLING_CYCLE_TYPE_ID AS billingCycleTypeId, A.TIME_UNIT AS timeUnit, A.BILLING_CYCLE_TYPE_NAME AS billingCycleTypeName, A.COMMENTS AS comments, A.QUANTITY AS quantity, A.BEGIN_DATE AS beginDate, A.DEBT_DATE AS debtDate, B.TIME_UNIT_NAME AS timeUnitName, A.OPERATOR AS operator, A.BILLING_CYCLE_TYPE_CODE AS billingCycleTypeCode, A.RUN_DATE AS runDate, A.PROD_TYPE AS prodType, NVL(A.POSTPAID, 'Y') AS postpaid, A.CUST_TYPE AS custType, C.CUST_TYPE_NAME AS custTypeName FROM BILLING_CYCLE_TYPE A JOIN TIME_UNIT B ON A.TIME_UNIT = B.TIME_UNIT LEFT JOIN CUST_TYPE C ON A.CUST_TYPE = C.CUST_TYPE WHERE (:billingCycleTypeId IS NULL OR A.BILLING_CYCLE_TYPE_ID = :billingCycleTypeId) AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId) AND (A.OPERATOR = '1' OR ('1' = '1' AND A.OPERATOR IS NULL)) AND (A.OPERATOR = '1' OR ('1' = '1' AND A.OPERATOR IS NULL)) ORDER BY A.BILLING_CYCLE_TYPE_NAME ASC
          """, nativeQuery = true)
  Page<BillingCycleTypeListProjection> findBillingCycleType (@Param("billingCycleTypeId") Integer billingCycleTypeId, @Param("spId") Integer spId, Pageable pageable);

  @Query("""
          SELECT COUNT(b) > 0 FROM BillingCycleType b WHERE b.billingCycleTypeName = :name AND b.spId = :spId AND b.billingCycleTypeId <> :id
          """)
  boolean existsByNameAndSpIdAndNotId (@Param("name") String name, @Param("spId") Integer spId, @Param("id") Integer id);

  @Query("""
          SELECT COUNT(b) > 0 FROM BillingCycleType b WHERE b.billingCycleTypeCode = :code AND b.spId = :spId AND b.billingCycleTypeId <> :id
          """)
  boolean existsByCodeAndSpIdAndNotId (@Param("code") String code, @Param("spId") Integer spId, @Param("id") Integer id);
}
