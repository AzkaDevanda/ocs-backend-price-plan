package com.sts.sinorita.repository.depend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.DependProdPackage;
import com.sts.sinorita.projection.depend.dependprodpackage.DependProdJoinPackageProjection;

@Repository
public interface DependProdPackageRepository extends JpaRepository<DependProdPackage, Integer> {
  @Query(value = """
      SELECT
        B.DEPEND_PROD_SPEC_ID as dependProdSpecId,
        C.OFFER_ID as offerId,
        C.OFFER_NAME as offerName,
        E.NETWORK_TYPE as networkType,
        E.NETWORK_TYPE_NAME as networkTypeName,
        NVL(A.DEFAULT_FLAG,'N') as defaultFlag
      FROM DEPEND_PROD_PACKAGE A, DEPEND_PROD_SPEC B, OFFER C ,ALL_SERV_TYPE D,NETWORK_TYPE E
      WHERE A.DEPEND_PROD_PACKAGE_ID = B.DEPEND_PROD_SPEC_ID
        AND B.DEPEND_PROD_SPEC_ID = C.OFFER_ID
        AND B.IS_PACKAGE = 'Y'
        AND C.STATE = 'A'
        AND B.SERV_TYPE=D.SERV_TYPE
        AND D.NETWORK_TYPE=E.NETWORK_TYPE
        AND A.MEM_DEPEND_PROD_SPEC_ID = :DEPEND_PROD_SPEC_ID
        AND NVL(A.SP_ID,0)= :SP_ID
      """, nativeQuery = true)
  List<DependProdJoinPackageProjection> findDependProdJoinPackage(@Param("DEPEND_PROD_SPEC_ID") Integer dependProdSpecId, @Param("SP_ID") Integer spId);

  @Query(value = """
      SELECT COUNT(A.MEM_DEPEND_PROD_SPEC_ID) CNT FROM DEPEND_PROD_PACKAGE A WHERE NVL(A.DEFAULT_FLAG,'Y') = 'Y' AND A.DEPEND_PROD_PACKAGE_ID = :DEPEND_PROD_PACKAGE_ID
      """, nativeQuery = true)
      Long getDefaultOfferMemCnt(@Param("DEPEND_PROD_PACKAGE_ID") Integer dependProdPackageId);
}
