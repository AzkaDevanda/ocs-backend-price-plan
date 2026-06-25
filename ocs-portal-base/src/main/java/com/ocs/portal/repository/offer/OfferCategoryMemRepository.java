package com.ocs.portal.repository.offer;

import com.ocs.portal.entity.OfferCatGMem;
import com.ocs.portal.projection.offer.offercatgmem.IndepOfferListByCatgIdProjection;
import com.ocs.portal.projection.offer.offercatgmem.PriceOfferListByCatgIdProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OfferCategoryMemRepository extends JpaRepository<OfferCatGMem, Long> {

	@Query(value = """
			SELECT
				ocm.OFFER_ID,
				o.OFFER_NAME,
				o.OFFER_CODE,
				COALESCE(spp_type.PRICE_PLAN_TYPE_NAME, app_type.PRICE_PLAN_TYPE_NAME) AS PRICE_PLAN_TYPE,
				ast.SERV_TYPE_NAME,
				nt.NETWORK_TYPE_NAME,
				o.EFF_TYPE,
				o.EFF_DATE,
				o.EXP_DATE,
				ocm.CHILD_OFFER_CATG_ID,
				oc.OFFER_CATG_NAME
			FROM OFFER_CATG_MEM ocm
			LEFT JOIN OFFER o ON ocm.OFFER_ID = o.OFFER_ID
			LEFT JOIN OFFER_CATG oc ON ocm.CHILD_OFFER_CATG_ID = oc.OFFER_CATG_ID
			LEFT JOIN PRICE_PLAN pp ON o.OFFER_ID = pp.PRICE_PLAN_ID
			LEFT JOIN SUBS_PRICE_PLAN spp ON pp.APPLY_LEVEL = 'S' AND spp.PRICE_PLAN_ID = pp.PRICE_PLAN_ID
			LEFT JOIN ACCT_PRICE_PLAN app ON pp.APPLY_LEVEL = 'A' AND app.PRICE_PLAN_ID = pp.PRICE_PLAN_ID
			LEFT JOIN PRICE_PLAN_TYPE spp_type ON spp.PRICE_PLAN_TYPE = spp_type.PRICE_PLAN_TYPE
			LEFT JOIN PRICE_PLAN_TYPE app_type ON app.PRICE_PLAN_TYPE = app_type.PRICE_PLAN_TYPE
			LEFT JOIN ALL_SERV_TYPE ast ON pp.SERV_TYPE = ast.SERV_TYPE
			LEFT JOIN NETWORK_TYPE nt ON ast.NETWORK_TYPE = nt.NETWORK_TYPE
			WHERE ocm.OFFER_CATG_ID = :offerCatgId AND oc.STATE = 'A'""",
			countQuery = "SELECT COUNT(*) FROM OFFER_CATG_MEM ocm WHERE ocm.OFFER_CATG_ID = :offerCatgId", 
			nativeQuery = true)
	Page<Object[]> listOfferFindByOfferCatgId(@Param("offerCatgId") Integer offerCatgId, Pageable pageable);

	@Query(value = """
			SELECT 
				A.OFFER_CATG_MEM_ID   AS offerCatgMemId,
				A.CHILD_OFFER_CATG_ID AS childOfferCatgId,
				A.SEQ                 AS seq,
				B.OFFER_ID            AS offerId,
				B.OFFER_TYPE          AS offerType,
				B.OFFER_NAME          AS offerName,
				B.OFFER_CODE          AS offerCode,
				B.EFF_DATE            AS effDate,
				B.EXP_DATE            AS expDate,
				B.EFF_TYPE            AS effType,
				B.EXP_OFF             AS expOff,
				B.TIME_UNIT           AS timeUnit,
				B.COMMENTS            AS comments,
				B.PROD_TYPE           AS prodType,
				C.INDEP_PROD_SPEC_ID  AS indepProdSpecId,
				C.SERV_TYPE           AS servType,
				C.PAID_FLAG           AS paidFlag,
				C.BRAND_PRICE_PLAN_ID AS brandPricePlanId,
				D.SERV_TYPE_NAME      AS servTypeName,
				D.PAID_FLAG           AS servTypePaidFlag,
				D.NETWORK_TYPE        AS networkType
			FROM OFFER_CATG_MEM A, OFFER B, INDEP_PROD_SPEC C, ALL_SERV_TYPE D
			WHERE A.OFFER_ID = B.OFFER_ID
			  AND B.OFFER_ID = C.INDEP_PROD_SPEC_ID
			  AND C.SERV_TYPE = D.SERV_TYPE
			  AND A.OFFER_CATG_ID = :OFFER_CATG_ID
			  AND NVL(A.SP_ID,0)= :SP_ID""", 
		countQuery = """
			SELECT COUNT(*)
			FROM OFFER_CATG_MEM A, OFFER B, INDEP_PROD_SPEC C, ALL_SERV_TYPE D
			WHERE A.OFFER_ID = B.OFFER_ID
			  AND B.OFFER_ID = C.INDEP_PROD_SPEC_ID
			  AND C.SERV_TYPE = D.SERV_TYPE
			  AND A.OFFER_CATG_ID = :OFFER_CATG_ID
			  AND NVL(A.SP_ID,0)= :SP_ID""",
		nativeQuery = true)
	Page<IndepOfferListByCatgIdProjection> findIndepOfferListByCatgId(@Param("OFFER_CATG_ID") Integer offerCatgId, @Param("SP_ID") Integer spId, Pageable pageable);

	@Query(value = """
      SELECT
      	A.SEQ AS seq,
				A.OFFER_CATG_MEM_ID AS offerCatgMemId,
				B.OFFER_ID AS offerId,
				B.OFFER_TYPE AS offerType,
				B.OFFER_NAME AS offerName,
				B.OFFER_CODE AS offerCode,
				B.EFF_DATE AS effDate,
				B.EXP_DATE AS expDate,
				B.DUPLICATE_FLAG AS duplicateFlag,
				B.EXP_OFF AS expOff,
				B.EXP_TIME_UNIT AS expTimeUnit,
				C.IS_PACKAGE AS isPackage,
				C.APPLY_LEVEL AS applyLevel,
				C.POLICY_FLAG AS policyFlag,
				C.WARN_LEVEL AS warnLevel,
				CASE
					WHEN C.APPLY_LEVEL = 'S' THEN (
						SELECT SPP.PRICE_PLAN_TYPE
						FROM SUBS_PRICE_PLAN SPP
						WHERE SPP.PRICE_PLAN_ID = C.PRICE_PLAN_ID
					)
					WHEN C.APPLY_LEVEL = 'A' THEN (
						SELECT APP.PRICE_PLAN_TYPE
						FROM ACCT_PRICE_PLAN APP
						WHERE APP.PRICE_PLAN_ID = C.PRICE_PLAN_ID
					)
				END AS pricePlanType
      FROM OFFER_CATG_MEM A, OFFER B, PRICE_PLAN C
      WHERE
      	A.OFFER_ID = B.OFFER_ID
      	AND B.OFFER_ID = C.PRICE_PLAN_ID
      	AND B.STATE = 'A'
      	AND ((A.OFFER_CATG_ID = :OFFER_CATG_ID) OR (-1 = :OFFER_CATG_ID AND A.OFFER_CATG_ID IS NULL))
      	AND NVL(B.SP_ID, 0)= :SP_ID""",
		countQuery = """
			SELECT COUNT(*)
      FROM OFFER_CATG_MEM A, OFFER B, PRICE_PLAN C
      WHERE
      	A.OFFER_ID = B.OFFER_ID
      	AND B.OFFER_ID = C.PRICE_PLAN_ID
      	AND B.STATE = 'A'
      	AND ((A.OFFER_CATG_ID = :OFFER_CATG_ID) OR (-1 = :OFFER_CATG_ID AND A.OFFER_CATG_ID IS NULL))
      	AND NVL(B.SP_ID, 0)= :SP_ID
		""", nativeQuery = true)
  Page<PriceOfferListByCatgIdProjection> qryPriceOfferListByCatgId(@Param("OFFER_CATG_ID") Integer offerCatgId, @Param("SP_ID") Integer spId,Pageable pageable);

	@Query(value = """
        SELECT COUNT(1)
        FROM OFFER_CATG_MEM A
        LEFT JOIN OFFER B ON A.OFFER_ID = B.OFFER_ID
        LEFT JOIN OFFER_CATG C ON A.CHILD_OFFER_CATG_ID = C.OFFER_CATG_ID
        WHERE A.OFFER_CATG_ID = :offerCatgId
          AND (C.STATE = 'A' OR C.STATE IS NULL)
          AND (B.STATE = 'A' OR B.STATE IS NULL)
        """, nativeQuery = true)
	int countOfferCatgMembers(@Param("offerCatgId") Integer offerCatgId);

	@Modifying
	@Transactional
	@Query(value = """
        DELETE FROM OFFER_CATG_MEM
        WHERE OFFER_ID = :offerId
        """, nativeQuery = true)
	void deleteOfferCatgMemByOfferId(@Param("offerId") Integer offerId);

	@Modifying
	@Transactional
	@Query(value = """
        DELETE FROM OFFER_CATG_MEM
        WHERE CHILD_OFFER_CATG_ID = :childOfferCatgId
        """, nativeQuery = true)
	void deleteChildOfferCatgMem(@Param("childOfferCatgId") Integer childOfferCatgId);

	@Query(value = """
        SELECT COUNT(1)
        FROM OFFER_CATG_MEM A
        JOIN OFFER B ON A.OFFER_ID = B.OFFER_ID
        JOIN DEPEND_PROD_SPEC C ON B.OFFER_ID = C.DEPEND_PROD_SPEC_ID
        LEFT JOIN ALL_SERV_TYPE D ON C.SERV_TYPE = D.SERV_TYPE
        WHERE A.OFFER_CATG_ID = :offerCatgId
          AND NVL(B.SP_ID, 0) = '0'
        """, nativeQuery = true)
	int countActiveMembersInCatg(@Param("offerCatgId") Integer offerCatgId);
}
