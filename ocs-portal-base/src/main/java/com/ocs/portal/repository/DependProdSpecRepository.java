package com.ocs.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.DependProdSpec;
import com.ocs.portal.projection.depend.dependprodspec.DependProdWithNetworkTypeProjection;

@Repository
public interface DependProdSpecRepository extends JpaRepository<DependProdSpec, Long> {
  Optional<DependProdSpec> findByDependProdSpecId(Integer dependProdSpecId);

  @Query(value = """
      SELECT
        A.DEPEND_PROD_SPEC_ID        AS dependProdSpecId,
        B.OFFER_NAME                 AS offerName,
        B.DUPLICATE_FLAG             AS duplicateFlag,
        D.NETWORK_TYPE               AS networkType,
        D.NETWORK_TYPE_NAME          AS networkTypeName,
        NVL(A.IS_PACKAGE, 'N')       AS isPackage
      FROM DEPEND_PROD_SPEC A, OFFER B, ALL_SERV_TYPE C, NETWORK_TYPE D
      WHERE
        A.DEPEND_PROD_SPEC_ID = B.OFFER_ID
        AND A.SERV_TYPE = C.SERV_TYPE(+)
        AND C.NETWORK_TYPE = D.NETWORK_TYPE(+)
        AND NVL(A.SP_ID, 0) = :SP_ID
        AND B.STATE = 'A'
      ORDER BY OFFER_NAME
      """, nativeQuery = true)
  List<DependProdWithNetworkTypeProjection> findDependProdWithNetworkType(@Param("SP_ID") Integer spId);

  @Query(value = """
        SELECT COUNT(*)
        FROM DEPEND_PROD_PACKAGE A
        WHERE A.MEM_DEPEND_PROD_SPEC_ID = :specId
          AND A.DEPEND_PROD_PACKAGE_ID = :packageId
        """, nativeQuery = true)
  int countDependProdRelations(@Param("specId") Integer specId, @Param("packageId") Integer packageId);
}
