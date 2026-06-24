
package com.sts.sinorita.repository;

import com.sts.sinorita.entity.OfferVer;
import com.sts.sinorita.dto.response.offerver.OfferVerListResponse;
import com.sts.sinorita.projection.pricePlan.OfferVerProjection;
import com.sts.sinorita.projection.pricePlan.PricePlanVerByPricePlanIdProjection;
import com.sts.sinorita.repository.customRepository.impl.OfferVerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sts.sinorita.projection.offer.OfferVerByOfferIdProjection;
import com.sts.sinorita.projection.offer.offerver.OfferEffectiveVerByOfferIdProjection;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferVerRepository extends JpaRepository<OfferVer, Integer>, OfferVerRepositoryCustom {
	boolean existsByOfferId(Integer offerId);

	@Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM offer_ver WHERE ref_offer_ver_id = :pricePlanVerId", nativeQuery = true)
	boolean existsByRefOfferVerId(@Param("pricePlanVerId") Integer pricePlanVerId);

	@Query(value = "SELECT o.OFFER_NAME  FROM OFFER o \n" +
			"JOIN OFFER_VER ov ON o.OFFER_ID = ov.OFFER_ID \n" +
			"WHERE ov.OFFER_VER_ID = :offerVerId", nativeQuery = true)
	String findOfferNameByOfferVerId(@Param("offerVerId") Integer offerVerId);

	@Modifying
	@Query(value = """
				 DELETE FROM offer_ver
				 WHERE offer_ver_id IN (
					SELECT rpp.offer_ver_id
					FROM re_price_plan rpp
					JOIN rate_plan rp ON rpp.offer_ver_id = rp.offer_ver_id
					JOIN price_ver pv ON rp.rate_plan_id = pv.rate_plan_id
					WHERE pv.price_ver_id = :priceVerId
				 )
			""", nativeQuery = true)
	void deleteOfferVerByPriceVerId(@Param("priceVerId") Integer priceVerId);

	@Modifying
	@Transactional
	void deleteById(Integer priceVerId);

	@Query(value = """
			SELECT ov
			FROM OfferVer ov
			WHERE ov.offerId = :offerId
			ORDER BY ov.effDate DESC
			""")
	List<OfferVer> getVersionByOfferId(@Param("offerId") Integer offerId);

	@Query(value = "SELECT NVL(MAX(offer_ver_id), 0) + 1 FROM offer_ver WHERE offer_id = :offerId", nativeQuery = true)
	Integer getNextSeq(@Param("offerId") Integer offerId);

	Optional<OfferVer> findById(Integer id);

	@Query(value = "SELECT OFFER_VER_ID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
	Integer getNextOfferVerId();

	@Query("SELECT new com.sts.sinorita.dto.response.offerver.OfferVerListResponse(o.id , ov.id , o.offerName, o.offerCode, null, null) FROM Offer o INNER JOIN OfferVer ov ON o.id  = ov.offerId")
	public List<OfferVerListResponse> findOfferVerListAndOffer();

	// ======> Dev By Ramdhan <======
	@Query(value = """
				SELECT T.OFFER_VER_ID as offerVerId, T.OFFER_ID as offerId, T.EXP_DATE as expDate, T.EFF_DATE as effDate, T.EFF_DATE|| ' -- '||T.EXP_DATE AS name,'Version' as type
				FROM OFFER_VER T
				WHERE T.OFFER_ID=:OFFER_ID
					AND 1=:ONLY_VALID  AND ( EXP_DATE>=TRUNC(SYSDATE) OR  EXP_DATE IS NULL )
					AND T.SEQ = :SEQ
					AND NVL(T.SP_ID,0)= :SP_ID
			""", nativeQuery = true)
	Optional<OfferVerByOfferIdProjection> findOfferVerByOfferId(@Param("OFFER_ID") Integer offerId,
			@Param("ONLY_VALID") Integer onlyValid, @Param("SEQ") Integer seq, @Param("SP_ID") Integer spId);

	@Query(value = """
			SELECT A.OFFER_VER_ID offerVerId,
			  A.OFFER_ID offerId,
			  A.EFF_DATE effDate,
			  A.EXP_DATE expDate,
			  A.SEQ seq,
			  A.STATE state,
			  A.REF_OFFER_VER_ID refOfferVerId
			 FROM OFFER_VER A
			 WHERE A.OFFER_ID =:PRICE_PLAN_ID
				AND NVL(A.SP_ID,0)= :SP_ID
			 ORDER BY A.SEQ DESC
			""", nativeQuery = true)
	List<PricePlanVerByPricePlanIdProjection> qryPricePlanVerListByPricePlanId(@Param("PRICE_PLAN_ID") Integer offerId,
			@Param("SP_ID") Integer spId);

	@Query(value = """
				SELECT OFFER_VER_ID as offerVerId, OFFER_ID as offerId, EFF_DATE as effDate, EXP_DATE as expDate, SEQ as seq, SP_ID as spId
				FROM OFFER_VER
				WHERE OFFER_ID = :OFFER_ID
					AND EFF_DATE <= SYSDATE
					AND (EXP_DATE IS NULL OR EXP_DATE > SYSDATE)
				ORDER BY SEQ
			""", nativeQuery = true)
	Optional<OfferEffectiveVerByOfferIdProjection> findOfferEffectiveVerByOfferId(@Param("OFFER_ID") Integer offerId);

	@Query(value = """
			SELECT A.OFFER_VER_ID as offerVerId, A.OFFER_ID as offerId, A.EFF_DATE as effDate, A.EXP_DATE as expDate, A.SEQ as seq, A.SP_ID as spId
			FROM OFFER_VER A
			WHERE A.OFFER_ID = :OFFER_ID
				AND 1 = :ONLY_VALID
				AND (A.EXP_DATE > SYSDATE OR A.EXP_DATE IS NULL)
				AND NVL(A.SP_ID,0)= :SP_ID
			""", nativeQuery = true)
	Optional<OfferEffectiveVerByOfferIdProjection> findSubsPlanVer(@Param("OFFER_ID") Integer offerId, @Param("ONLY_VALID") Integer onlyValid, @Param("SP_ID") Integer spId);

	@Query(value = "select o from OfferVer o where o.id = :id")
	Optional<OfferVer> findOfferVerById(@Param("id") Integer id);

	@Query(value = "SELECT MAX(SEQ) FROM OFFER_VER WHERE OFFER_ID = :offerId", nativeQuery = true)
	Integer findMaxSeqByOfferId(@Param("offerId") Integer offerId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE OFFER_VER SET  EXP_DATE=:expDate WHERE OFFER_VER_ID=:offerVerId",nativeQuery = true)
	void updateOfferVer(@Param("expDate")LocalDate expDate, @Param("offerVerId") Integer offerVerId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE OFFER_VER SET  STATE=:state WHERE OFFER_VER_ID=:offerVerID",nativeQuery = true)
	void updateState(@Param("offerVerID") Integer offerVerID, @Param("state") Character state);

	@Query(value = """
					SELECT
			A.DEPEND_PROD_SPEC_ID        AS dependProdSpecId,
			B.OFFER_ID                   AS offerId,
			B.OFFER_NAME                 AS offerName,
			D.NETWORK_TYPE               AS networkType,
			D.NETWORK_TYPE_NAME          AS networkTypeName
		FROM
			DEPENDENCY_TABLE A
		JOIN
			OFFER B ON A.OFFER_ID = B.OFFER_ID
		JOIN
			NETWORK_TYPE D ON A.NETWORK_TYPE_ID = D.NETWORK_TYPE
		WHERE
			B.OFFER_ID = :offerId
			AND SYSDATE >= A.EFF_DATE
			AND SYSDATE < A.EXP_DATE
			ORDER B
		""", nativeQuery = true)
	Optional<OfferVerProjection> getOfferVerByOfferId(@Param("offerId") Integer offerId);

	@Query(value = "select o from OfferVer o where o.offerId = :offerId and o.id = :offerVerId")
	Optional<OfferVer>findByOfferId(@Param("offerId") Integer offerId,@Param("offerVerId")Integer offerVerId);

//	@Query(value = "select o from OfferVer o where o.offerId = :offerId and o.id = :offerVerId")
//	List<OfferVer>findListByOfferId(@Param("offerId") Integer offerId,@Param("offerVerId")Integer offerVerId);

	@Query(value = "select * from offer_ver o where o.OFFER_ID = :offerId and o.offer_ver_id = :offerVerId FETCH FIRST 1 ROWS ONLY ", nativeQuery = true)
	Optional<OfferVerProjection> findOneByOfferId(@Param("offerId") Integer offerId,@Param("offerVerId")Integer offerVerId);

	@Query(value = "select o from OfferVer o where o.offerId = :offerId ")
	List<OfferVer>findByOffer(@Param("offerId") Integer offerId);

	@Query(value = "select 1 from RE_PRICE_PLAN where OFFER_VER_ID=:offerVerId FETCH FIRST 1 ROWS ONLY",nativeQuery = true)
	public Optional<Integer> isReferenceRePricePlan(@Param("offerVerId")Integer offerVerId);

	@Query(value = "select 1 from DP where OFFER_VER_ID=:offerVerId FETCH FIRST 1 ROWS ONLY",nativeQuery = true)
	public Optional<Integer> isReferenceDP(@Param("offerVerId")Integer offerVerId);

	@Query(value = "select 1 from ACM_TRIGGER where OFFER_VER_ID=:offerVerId FETCH FIRST 1 ROWS ONLY",nativeQuery = true)
	public Optional<Integer> isReferenceTriggerAcm(@Param("offerVerId")Integer offerVerId);

	@Query(value = "select 1 from BAL_TRIGGER where OFFER_VER_ID=:offerVerId FETCH FIRST 1 ROWS ONLY",nativeQuery = true)
	public Optional<Integer> isReferenceBalTrigger(@Param("offerVerId")Integer offerVerId);

	@Query(value = "select 1 from OFFER_VER where REF_OFFER_VER_ID=:offerVerId FETCH FIRST 1 ROWS ONLY",nativeQuery = true)
	public Optional<Integer> isReferenceROfferVer(@Param("offerVerId")Integer offerVerId);

	@Modifying
	@Transactional
	@Query(value = "DELETE OFFER_VER WHERE OFFER_VER_ID=:offerVerId", nativeQuery = true)
	public void deleteOfferVer(@Param("offerVerId")Integer offerVerId);

}