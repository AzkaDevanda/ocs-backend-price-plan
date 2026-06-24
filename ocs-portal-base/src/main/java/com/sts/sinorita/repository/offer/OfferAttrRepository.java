package com.sts.sinorita.repository.offer;

import com.sts.sinorita.entity.OfferAttr;
import com.sts.sinorita.projection.balanceAdjustment.SelectOfferAttrByAttrCodeProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectOfferAttrListByAttrCodeProjection;
import com.sts.sinorita.projection.offer.offerattr.OfferAttrProjection;
import com.sts.sinorita.projection.offer.offerattr.QryOfferAttrForDisctObjProjection;
import com.sts.sinorita.projection.offer.offerattr.SelectOfferAttrByOfferIdProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferAttrRepository extends JpaRepository<OfferAttr, Integer> {
  @Query(value = """
          SELECT
            T.ATTR_ID as attrId,
            T.ATTR_NAME as attrName,
            T.ATTR_TYPE as attrType,
            T.ATTR_CODE as attrCode,
            T.OBJ_ATTR_ID as objAttrId,
            T.CSR_VISIBLE as csrVisible,
            T.INSTANTIATABLE as instantiatable,
            T.CONFIG_VISIBLE as configVisible,
            OA.OFFER_ID as offerId,
            OA.DISP_ORDER as dispOrder,
            OA.DEFAULT_VALUE as defaultValue,
            OA.DEFAULT_VALUE as attrValue,
            OA.OPERATION_TYPES as operationTypes
          FROM ATTR T, OFFER_ATTR OA, OFFER C
          WHERE OA.ATTR_ID = T.ATTR_ID
            AND OA.OFFER_ID = C.OFFER_ID
            AND OA.OFFER_ID = :OFFER_ID
            AND NVL(OA.SP_ID,0)= :SP_ID
            -- AND C.OFFER_TYPE IN (&OFFER_TYPE&)
            -- AND T.ATTR_ID IN (&ATTR_ID_LIST&)
            -- AND T.ATTR_CODE = :ATTR_CODE
            -- AND UPPER( T.ATTR_NAME) LIKE '%' || UPPER(:ATTR_NAME) || '%'
      """, countQuery = """
      SELECT COUNT(*)
      FROM ATTR T, OFFER_ATTR OA, OFFER C
      WHERE OA.ATTR_ID = T.ATTR_ID
        AND OA.OFFER_ID = C.OFFER_ID
        AND OA.OFFER_ID = :OFFER_ID
        AND NVL(OA.SP_ID,0)= :SP_ID
        -- AND C.OFFER_TYPE IN (&OFFER_TYPE&)
        -- AND T.ATTR_ID IN (&ATTR_ID_LIST&)
        -- AND T.ATTR_CODE = :ATTR_CODE
        -- AND UPPER( T.ATTR_NAME) LIKE '%' || UPPER(:ATTR_NAME) || '%'
      """, nativeQuery = true)
  Page<OfferAttrProjection> findOfferAttr(@Param("OFFER_ID") Integer offerId, @Param("SP_ID") Integer spId,
      Pageable pageable);

  @Query(value = "select o from OfferAttr o where o.id =:id")
  List<OfferAttr> selectOfferAttrById(@Param("id") Integer id);

  @Query(value = """
        SELECT DISTINCT B.ATTR_ID AS attrId, B.ATTR_NAME AS attrName, B.ATTR_CODE AS attrCode FROM OFFER_ATTR A JOIN ATTR B ON A.ATTR_ID = B.ATTR_ID WHERE (:SP_ID IS NULL OR NVL(A.SP_ID,0) = :SP_ID)
      """, nativeQuery = true)
  List<QryOfferAttrForDisctObjProjection> qryOfferAttrForDisctObj(@Param("SP_ID") Integer spId);

  @Query(value = """
      SELECT A.OFFER_ID AS offerId, A.ATTR_ID AS attrId, A.DEFAULT_VALUE AS defaultValue, A.SP_ID AS spId FROM OFFER_ATTR A JOIN ATTR B ON A.ATTR_ID = B.ATTR_ID WHERE A.OFFER_ID = :offerId AND B.ATTR_CODE = :attrCode
      """, nativeQuery = true)
  Optional<SelectOfferAttrByAttrCodeProjection> selectOfferAttrByAttrCode(@Param("offerId") Long offerId,
      @Param("attrCode") String attrCode);

  @Query(value = """
      SELECT A.OFFER_ID AS offerId, A.ATTR_ID AS attrId, DECODE(C.INPUT_TYPE, '3', PKG_BC_COMMON.F_GET_ATTR_DEFAULT_VALUE(NVL(A.DEFAULT_VALUE, C.DEFAULT_VALUE)), NVL(A.DEFAULT_VALUE, C.DEFAULT_VALUE)) AS defaultValue, B.ATTR_NAME AS attrName, B.ATTR_TYPE AS attrType, B.ATTR_CODE AS attrCode, B.CSR_VISIBLE AS csrVisible, B.INSTANTIATABLE AS instantiatable, B.SP_ID AS spId FROM OFFER_ATTR A JOIN ATTR B ON A.ATTR_ID = B.ATTR_ID JOIN BASE_ATTR C ON A.ATTR_ID = C.BASE_ATTR_ID WHERE A.OFFER_ID = :offerId AND (:attrCodes IS NULL OR B.ATTR_CODE IN (:attrCodes))
      """, nativeQuery = true)
  List<SelectOfferAttrListByAttrCodeProjection> selectOfferAttrListByAttrCode(@Param("offerId") Long offerId,
      @Param("attrCodes") List<String> attrCodes);

  @Query(value = "SELECT A.OFFER_ID AS offerId, A.ATTR_ID AS attrId, B.ATTR_CODE AS attrCode, A.DEFAULT_VALUE AS defaultValue, A.DISP_ORDER AS dispOrder, A.SP_ID AS spId, A.OPERATION_TYPES AS operationTypes FROM OFFER_ATTR A JOIN ATTR B ON A.ATTR_ID = B.ATTR_ID WHERE A.OFFER_ID = :offerId ORDER BY A.DISP_ORDER", nativeQuery = true)
  List<SelectOfferAttrByOfferIdProjection> selectOfferAttrByOfferId(
      @Param("offerId") Long offerId);

}
