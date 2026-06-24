package com.sts.sinorita.repository.offer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.OfferCategory;
import com.sts.sinorita.projection.offer.offercatg.RootCatgProjection;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OfferCatgRepository extends JpaRepository<OfferCategory, Integer> {
  @Query(value = """
      SELECT
        A.OFFER_CATG_ID     AS offerCatgId,
        A.OFFER_CATG_NAME   AS offerCatgName,
        A.SEQ               AS seq,
        A.OFFER_CATG_CODE   AS offerCatgCode,
        A.EFF_DATE          AS effDate,
        A.EXP_DATE          AS expDate,
        A.COMMENTS          AS comments,
        A.OFFER_CATG_TYPE   AS offerCatgType,
        A.OFFER_CATG_CLASS  AS offerCatgClass,
        A.POLICY_FLAG       AS policyFlag
      FROM OFFER_CATG A
      WHERE
        A.STATE = 'A'
        AND A.OFFER_CATG_TYPE = :OFFER_CATG_TYPE
        AND A.OFFER_CATG_CLASS = :OFFER_CATG_CLASS
        AND A.OFFER_CATG_ID != :OFFER_CATG_ID
        AND UPPER(A.OFFER_CATG_NAME) LIKE '%' || UPPER(:OFFER_CATG_NAME)|| '%'
        AND NVL(A.SP_ID, 0) = :SP_ID
        AND NVL(A.POLICY_FLAG, 'N') = :POLICY_FLAG
        AND NOT EXISTS (
        	SELECT 1 FROM OFFER_CATG_MEM B
        	WHERE A.OFFER_CATG_ID = B.CHILD_OFFER_CATG_ID)
      ORDER BY SEQ, OFFER_CATG_NAME""", nativeQuery = true)
  List<RootCatgProjection> findRootCatg(@Param("OFFER_CATG_TYPE") Integer offerCatgType,
      @Param("OFFER_CATG_CLASS") Character offerCatgClass, @Param("OFFER_CATG_ID") Integer offerCatgId,
      @Param("OFFER_CATG_NAME") String offerCatgName, @Param("SP_ID") Integer spId,
      @Param("POLICY_FLAG") Character policyFlag);

  @Modifying
  @Transactional
  @Query(value = """
        UPDATE OFFER_CATG
        SET STATE = 'X', STATE_DATE = SYSDATE
        WHERE OFFER_CATG_ID = :offerCatgId
        """, nativeQuery = true)
  void deactivateOfferCatg(@Param("offerCatgId") Integer offerCatgId);

}
