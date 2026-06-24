package com.sts.sinorita.repository.offer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.OfferType;
import com.sts.sinorita.projection.offer.commonoffer.Offer4ReConfProjection;

@Repository
public interface OfferTypeRepository extends JpaRepository<OfferType, Integer> {
  @Query(value = """
      SELECT
        A.OFFER_ID AS prodSpecId, A.OFFER_NAME AS prodSpecName, A.OFFER_CODE AS stdCode,
        B.OFFER_TYPE AS offerType, B.OFFER_TYPE_NAME AS offerTypeName, A.OFFER_ID AS offerId, A.OFFER_NAME AS offerName
      FROM OFFER A, OFFER_TYPE B
      WHERE 1 = 1
        AND A.OFFER_TYPE = B.OFFER_TYPE(+)
        AND A.EFF_DATE <= SYSDATE
        AND (A.EXP_DATE IS NULL OR A.EXP_DATE > SYSDATE)
        AND UPPER(A.OFFER_NAME) LIKE '%' || UPPER(:PROD_SPEC_NAME)|| '%' ESCAPE '/'
        AND UPPER(A.OFFER_CODE) LIKE '%' || UPPER(:STD_CODE)|| '%' ESCAPE '/'
        AND A.OFFER_TYPE = :OFFER_TYPE
        AND A.STATE = :STATE
        AND (NVL(A.SP_ID, 0)= :SP_ID OR -1 = :SP_ID)
      """, countQuery = """
      SELECT COUNT(*)
        FROM OFFER A, OFFER_TYPE B
        WHERE 1 = 1
          AND A.OFFER_TYPE = B.OFFER_TYPE(+)
          AND A.EFF_DATE <= SYSDATE
          AND (A.EXP_DATE IS NULL OR A.EXP_DATE > SYSDATE)
          AND UPPER(A.OFFER_NAME) LIKE '%' || UPPER(:PROD_SPEC_NAME)|| '%' ESCAPE '/'
          AND UPPER(A.OFFER_CODE) LIKE '%' || UPPER(:STD_CODE)|| '%' ESCAPE '/'
          AND A.OFFER_TYPE = :OFFER_TYPE
          AND A.STATE = :STATE
          AND (NVL(A.SP_ID, 0)= :SP_ID OR -1 = :SP_ID)
      """, nativeQuery = true)
  Page<Offer4ReConfProjection> findoffer4ReConf(@Param("PROD_SPEC_NAME") String prodSpecName,
      @Param("STD_CODE") String stdCode,
      @Param("OFFER_TYPE") Character offerType, @Param("STATE") Character state, @Param("SP_ID") Integer spId,
      Pageable pageable);
}