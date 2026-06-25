package com.ocs.portal.repository.offer;

import java.util.List;

import com.ocs.portal.projection.offer.offerrela.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.OfferRela;

@Repository
public interface OfferRelaRepository extends JpaRepository<OfferRela, Integer> {
  @Query(value = """
      SELECT
        T.OFFER_RELA_ID               AS offerRelaId,
        T.RELA_TYPE                   AS relaType,
        T.DEST_OFFER_ID               AS destOfferId,
        T.ORI_LOWER_LIMIT             AS oriLowerLimit,
        T.ORI_UPPER_LIMIT             AS oriUpperLimit,
        T.DEST_OFFER_TYPE             AS destOfferType,
        T.DEST_OFFER_GROUP_OFFER_TYPE AS destOfferGroupOfferType,
        T.ORI_OFFER_NAME              AS oriOfferName,
        T.DEST_OFFER_NAME             AS destOfferName,
        T.DEST_OFFER_CODE             AS destOfferCode,
        T.DEST_OFFER_GROUP_NAME       AS destOfferGroupName,
        T.DEST_IND_OFFER_NAME         AS destIndOfferName,
        T.DEST_SUBS_PLAN_NAME         AS destSubsPlanName,
        T.DEST_EFF_DATE               AS destEffDate,
        T.DEST_EXP_DATE               AS destExpDate
      FROM
        (
        SELECT
          R.OFFER_RELA_ID,
          R.RELA_TYPE,
          R.DEST_OFFER_ID,
          R.ORI_LOWER_LIMIT,
          R.ORI_UPPER_LIMIT,
          '' DEST_OFFER_TYPE,
          G.OFFER_GROUP_TYPE DEST_OFFER_GROUP_OFFER_TYPE,
          O.OFFER_NAME ORI_OFFER_NAME,
          '' DEST_OFFER_NAME,
          '' DEST_OFFER_CODE,
          G.OFFER_GROUP_NAME DEST_OFFER_GROUP_NAME,
          G.INDEP_PROD_NAME DEST_IND_OFFER_NAME,
          G.SUBS_PLAN_NAME DEST_SUBS_PLAN_NAME,
          TO_CHAR(G.EFF_DATE, 'yyyy-MM-dd') DEST_EFF_DATE,
          TO_CHAR(G.EXP_DATE, 'yyyy-MM-dd') DEST_EXP_DATE
        FROM
          OFFER_RELA R,
          OFFER O,
          (
          SELECT
            A.OFFER_GROUP_ID,
            A.OFFER_GROUP_TYPE,
            A.OFFER_GROUP_NAME,
            B.OFFER_NAME INDEP_PROD_NAME,
            C.EFF_DATE,
            C.EXP_DATE,
            DO.OFFER_NAME SUBS_PLAN_NAME
          FROM
            OFFER_GROUP A,
            OFFER B,
            OFFER_VER C,
            SUBS_PLAN D ,
            OFFER DO
          WHERE
            A.INDEP_PROD_SPEC_ID = B.OFFER_ID
            AND A.OFFER_VER_ID = C.OFFER_VER_ID
            AND D.SUBS_PLAN_ID = DO.OFFER_ID
            AND C.OFFER_ID = D.SUBS_PLAN_ID) G
        WHERE
          R.ORI_OFFER_ID = O.OFFER_ID
          AND R.DEST_OFFER_GROUP_ID = G.OFFER_GROUP_ID
      UNION ALL
        SELECT
          R.OFFER_RELA_ID,
          R.RELA_TYPE,
          R.DEST_OFFER_ID,
          R.ORI_LOWER_LIMIT,
          R.ORI_UPPER_LIMIT,
          DO.OFFER_TYPE DEST_OFFER_TYPE,
          '' DEST_OFFER_GROUP_OFFER_TYPE,
          OO.OFFER_NAME ORI_OFFER_NAME,
          DO.OFFER_NAME DEST_OFFER_NAME,
          DO.OFFER_CODE DEST_OFFER_CODE,
          '' DEST_OFFER_GROUP_NAME,
          '' DEST_IND_OFFER_NAME,
          '' DEST_SUBS_PLAN_NAME,
          '' DEST_EFF_DATE,
          '' DEST_EXP_DATE
        FROM
          OFFER_RELA R,
          OFFER OO,
          OFFER DO
        WHERE
          R.ORI_OFFER_ID = OO.OFFER_ID
          AND R.DEST_OFFER_ID = DO.OFFER_ID
          AND R.ORI_OFFER_ID = :ORI_OFFER_ID) T
      """, nativeQuery = true)
  List<OfferRelaAsOriProjection> findOfferRelaAsOri(@Param("ORI_OFFER_ID") Integer oriOfferId);

  @Query(value = """
      SELECT
          A.OFFER_ID AS offerId,
          A.OFFER_NAME AS offerName,
          A.OFFER_CODE AS offerCode,
          C.OFFER_CATG_ID AS offerCatgId
          FROM OFFER A, DEPEND_PROD_SPEC B, OFFER_CATG_MEM C
          WHERE A.OFFER_ID = B.DEPEND_PROD_SPEC_ID
          AND A.OFFER_ID = C.OFFER_ID
          AND A.OFFER_TYPE = '3'
          AND A.STATE = 'A'
          AND (UPPER(A.OFFER_NAME) LIKE '%'||UPPER(:OFFER_NAME)||'%')
          AND (:SERV_TYPE IS NULL OR B.SERV_TYPE = :SERV_TYPE)
          AND (:IS_PACKAGE IS NULL OR B.IS_PACKAGE = :IS_PACKAGE)
          AND (UPPER(A.OFFER_CODE) LIKE '%'||UPPER(:OFFER_CODE)||'%')
          AND (:SP_ID IS NULL OR NVL(A.SP_ID, 0) = :SP_ID)
      """, nativeQuery = true)
  List<OfferDependProdSpecProjection> findProdSpecByName(
      @Param("OFFER_NAME") String offerName,
      @Param("OFFER_CODE") String offerCode,
      @Param("SERV_TYPE") Integer servType,
      @Param("IS_PACKAGE") String isPackage,
      @Param("SP_ID") Integer spId);

  @Query(value = """
          SELECT
              A.SEQ AS seq,
              A.OFFER_CATG_MEM_ID AS offerCatgMemId,
              B.OFFER_ID AS offerId,
              B.OFFER_TYPE AS offerType,
              B.OFFER_NAME AS offerName,
              B.COMMENTS AS comments,
              B.OFFER_CODE AS offerCode,
              B.SALE_LIST_PRICE AS saleListPrice,
              B.RENT_LIST_PRICE AS rentListPrice,
              B.EFF_DATE AS effDate,
              B.EXP_DATE AS expDate,
              B.CREATED_DATE AS createdDate,
              B.STATE AS state,
              B.STATE_DATE AS stateDate,
              B.EFF_TYPE AS effType,
              B.EXP_OFF AS expOff,
              B.TIME_UNIT AS timeUnit,
              B.AUTO_CONTINUE_FLAG AS autoContinueFlag,
              B.CYCLE_QUANTITY AS cycleQuantity,
              B.DUPLICATE_FLAG AS duplicateFlag,
              B.SP_ID AS spId,
              B.EXP_TIME_UNIT AS expTimeUnit,
              B.AGREEMENT_EFF_TYPE AS agreementEffType,
              B.SALES_RULE_SCRIPT AS salesRuleScript,
              B.SALE_PRICE_GST_TYPE AS salePriceGstType,
              B.RENT_PRICE_GST_TYPE AS rentPriceGstType,
              B.PROD_TYPE AS prodType,
              B.PUBLISH_STATE AS publishState,
              B.PUBLISH_STATE_DATE AS publishStateDate,
              C.IS_PACKAGE AS isPackage,
              C.SERV_TYPE AS servType,
              D.SERV_TYPE_NAME AS servTypeName
          FROM
              OFFER_CATG_MEM A,
              OFFER B,
              DEPEND_PROD_SPEC C,
              ALL_SERV_TYPE D
          WHERE
              A.OFFER_ID = B.OFFER_ID
              AND B.OFFER_ID = C.DEPEND_PROD_SPEC_ID
              AND C.SERV_TYPE = D.SERV_TYPE(+)
              AND A.OFFER_CATG_ID = :offerCatgId
              AND (:servType IS NULL OR C.SERV_TYPE = :servType)
              AND (:isPackage IS NULL OR C.IS_PACKAGE = :isPackage)
              AND (:spId IS NULL OR NVL(B.SP_ID, 0) = :spId)
      """, nativeQuery = true)
  List<DependOfferListByCatgProjection> dependOfferListByCatgId(
      @Param("offerCatgId") Integer offerCatgId,
      @Param("servType") Integer servType,
      @Param("isPackage") String isPackage,
      @Param("spId") Integer spId);

  @Query(value = """
      SELECT
          A.OFFER_ID AS offerId,
          A.OFFER_TYPE AS offerType,
          A.OFFER_NAME AS offerName,
          A.COMMENTS AS comments,
          A.OFFER_CODE AS offerCode,
          A.SALE_LIST_PRICE AS saleListPrice,
          A.RENT_LIST_PRICE AS rentListPrice,
          A.EFF_DATE AS effDate,
          A.EXP_DATE AS expDate,
          A.CREATED_DATE AS createdDate,
          A.STATE AS state,
          A.STATE_DATE AS stateDate,
          A.EFF_TYPE AS effType,
          A.AUTO_CONTINUE_FLAG AS autoContinueFlag,
          A.CYCLE_QUANTITY AS cycleQuantity,
          A.TIME_UNIT AS timeUnit,
          A.DUPLICATE_FLAG AS duplicateFlag,
          A.EXP_OFF AS expOff,
          A.EXP_TIME_UNIT AS expTimeUnit,
          A.AGREEMENT_EFF_TYPE AS agreementEffType,
          A.SALE_PRICE_GST_TYPE AS salePriceGstType,
          A.RENT_PRICE_GST_TYPE AS rentPriceGstType,
          B.SERV_TYPE AS servType,
          B.IS_PACKAGE AS isPackage,
          D.LIFECYCLE_TYPE AS lifecycleType,
          B.GROUP_TYPE AS groupType,
          B.UPPER_LIMIT AS upperLimit,
          B.LOWER_LIMIT AS lowerLimit,
          B.NETWORK_TYPE AS networkType
      FROM
          OFFER A,
          DEPEND_PROD_SPEC B,
          LIFECYCLE_APPLY D
      WHERE
          A.OFFER_ID = B.DEPEND_PROD_SPEC_ID
          AND B.DEPEND_PROD_SPEC_ID = D.OFFER_ID(+)
          AND A.OFFER_TYPE = '3'
          AND A.STATE = 'A'
          AND B.DEPEND_PROD_SPEC_ID = :dependProdSpecId
          AND NVL(B.SP_ID, 0) = :spId
      """, nativeQuery = true)
  List<DependProdDetailByOfferIdProjection> getDependProdDetailByOfferId(
      @Param("dependProdSpecId") Integer dependProdSpecId,
      @Param("spId") Integer spId);

  @Query(value = """
      SELECT
          O.OFFER_ID AS offerId,
          O.OFFER_NAME AS offerName,
          B.NETWORK_TYPE_NAME AS networkTypeName
      FROM
          INDEP_PROD_SPEC IP, OFFER O, ALL_SERV_TYPE A, NETWORK_TYPE B
      WHERE
          IP.INDEP_PROD_SPEC_ID = O.OFFER_ID
          AND NOT EXISTS (
              SELECT BU.OFFER_ID
              FROM OFFER BU
              WHERE BU.OFFER_TYPE = '1'
                AND BU.OFFER_ID = IP.INDEP_PROD_SPEC_ID
          )
          AND O.STATE = 'A'
          AND A.SERV_TYPE = IP.SERV_TYPE
          AND A.NETWORK_TYPE = B.NETWORK_TYPE
          AND IP.SERV_TYPE = :servType
          AND (:offerName IS NULL OR UPPER(O.OFFER_NAME) LIKE '%' || UPPER(:offerName) || '%')
          AND NVL(IP.SP_ID, 0) = :spId
      """, nativeQuery = true)
  List<IndepOfferForRelaProjection> findIndepOfferForRela(
      @Param("servType") Integer servType,
      @Param("offerName") String offerName,
      @Param("spId") Integer spId);

  @Query(value = """
      SELECT
          O.OFFER_ID AS offerId,
          O.OFFER_NAME AS offerName,
          D.NETWORK_TYPE AS networkType,
          D.NETWORK_TYPE_NAME AS networkTypeName
      FROM
          PRICE_PLAN PP,
          OFFER O,
          (SELECT A.SERV_TYPE, B.NETWORK_TYPE, B.NETWORK_TYPE_NAME
           FROM ALL_SERV_TYPE A, NETWORK_TYPE B
           WHERE A.NETWORK_TYPE = B.NETWORK_TYPE) D,
          SUBS_PRICE_PLAN C
      WHERE
          PP.PRICE_PLAN_ID = O.OFFER_ID
          AND D.SERV_TYPE(+) = PP.SERV_TYPE
          AND C.PRICE_PLAN_ID = PP.PRICE_PLAN_ID
          AND O.STATE = 'A'
          AND PP.IS_PACKAGE = :isPackage
          AND C.PRICE_PLAN_TYPE = :pricePlanType
          AND D.NETWORK_TYPE = :networkType
          AND PP.SERV_TYPE = :servType
          AND NVL(PP.SP_ID, 0) = :spId
          AND (:offerName IS NULL OR UPPER(O.OFFER_NAME) LIKE '%' || UPPER(:offerName) || '%')
      """, nativeQuery = true)
  List<PricePlanForRelaProjection> findPricePlanForRela(
      @Param("isPackage") String isPackage,
      @Param("pricePlanType") String pricePlanType,
      @Param("networkType") Character networkType,
      @Param("servType") Integer servType,
      @Param("spId") Integer spId,
      @Param("offerName") String offerName);

  @Query(value = """
      SELECT
          O.OFFER_ID AS offerId,
          O.OFFER_NAME AS offerName,
          B.NETWORK_TYPE_NAME AS networkTypeName
      FROM
          INDEP_PROD_SPEC IP,
          SUBS_PLAN S,
          OFFER O,
          ALL_SERV_TYPE A,
          NETWORK_TYPE B
      WHERE
          IP.INDEP_PROD_SPEC_ID = S.INDEP_PROD_SPEC_ID
          AND S.SUBS_PLAN_ID = O.OFFER_ID
          AND NOT EXISTS (
              SELECT 1
              FROM OFFER BU
              WHERE BU.OFFER_TYPE = '1'
                AND BU.OFFER_ID = IP.INDEP_PROD_SPEC_ID
          )
          AND O.STATE = 'A'
          AND A.SERV_TYPE = IP.SERV_TYPE
          AND A.NETWORK_TYPE = B.NETWORK_TYPE
          AND IP.SERV_TYPE = :servType
          AND NVL(IP.SP_ID, 0) = :spId
          AND (:offerName IS NULL OR UPPER(O.OFFER_NAME) LIKE CONCAT('%', UPPER(:offerName), '%'))
      """, nativeQuery = true)
  Page<SubsPlanOfferForRelaProjection> findSubsPlanOfferForRela(
      @Param("servType") Integer servType,
      @Param("offerName") String offerName,
      @Param("spId") Integer spId,
      Pageable pageable);

  @Query(value = """
      SELECT
          O.OFFER_ID AS offerId,
          O.OFFER_NAME AS offerName
      FROM
          INDEP_PROD_SPEC IP,
          SUBS_PLAN S,
          OFFER O
      WHERE
          IP.INDEP_PROD_SPEC_ID = S.INDEP_PROD_SPEC_ID
          AND S.SUBS_PLAN_ID = O.OFFER_ID
          AND EXISTS (
              SELECT 1
              FROM OFFER BU
              WHERE BU.OFFER_TYPE = '1'
                AND BU.OFFER_ID = IP.INDEP_PROD_SPEC_ID
          )
          AND O.STATE = 'A'
          AND IP.SERV_TYPE = :servType
          AND NVL(IP.SP_ID, 0) = :spId
          AND (:offerName IS NULL OR UPPER(O.OFFER_NAME) LIKE CONCAT('%', UPPER(:offerName), '%'))
      """, nativeQuery = true)
  List<BundleOfferForRelaProjection> findBundleOfferForRela(
      @Param("servType") Integer servType,
      @Param("offerName") String offerName,
      @Param("spId") Integer spId);

  @Query(value = """
      SELECT
          OG.OFFER_GROUP_ID AS offerId,
          OG.OFFER_GROUP_NAME AS offerName,
          C.OFFER_NAME AS indOfferName,
          DO.OFFER_NAME AS subsPlanName,
          E.EFF_DATE AS effDate,
          E.EXP_DATE AS expDate,
          A.NETWORK_TYPE_NAME AS networkTypeName
      FROM
          OFFER_GROUP OG,
          OFFER C,
          SUBS_PLAN D,
          OFFER_VER E,
          NETWORK_TYPE A,
          OFFER DO
      WHERE
          OG.OFFER_GROUP_TYPE = :offerType
          AND OG.STATE = 'A'
          AND DO.OFFER_ID = D.SUBS_PLAN_ID
          AND A.NETWORK_TYPE(+) = OG.NETWORK_TYPE
          AND OG.INDEP_PROD_SPEC_ID = C.OFFER_ID(+)
          AND E.OFFER_ID = D.SUBS_PLAN_ID(+)
          AND OG.OFFER_VER_ID = E.OFFER_VER_ID(+)
          AND (E.EXP_DATE > SYSDATE OR E.EXP_DATE IS NULL)
          AND NOT EXISTS (
              SELECT OGM.CHILD_OFFER_GROUP_ID
              FROM OFFER_GROUP_MEM OGM
              WHERE OGM.CHILD_OFFER_GROUP_ID = OG.OFFER_GROUP_ID
          )
          AND UPPER(OG.OFFER_GROUP_NAME) LIKE '%' || UPPER(:offerName) || '%'
          AND NVL(OG.SP_ID, 0) = :spId
      """, nativeQuery = true)
  List<OfferGroupForRelaProjection> findOfferGroupForRela(
      @Param("offerType") Integer offerType,
      @Param("offerName") String offerName,
      @Param("spId") Integer spId);

  @Query(value = """
      SELECT O.OFFER_ID AS offerId,
             O.OFFER_NAME AS offerName
      FROM OFFER O
      WHERE O.STATE = 'A'
        AND O.OFFER_TYPE = '2'
        AND UPPER(O.OFFER_NAME) LIKE '%' || UPPER(:offerName) || '%'
        AND NVL(O.SP_ID, 0) = :spId
      """, nativeQuery = true)
  List<GoodsOfferForRelaProjection> findGoodsOfferForRela(
      @Param("offerName") String offerName,
      @Param("spId") Integer spId);

  @Query(value = """
    SELECT 
        O.OFFER_ID AS offerId,
        O.OFFER_NAME AS offerName,
        DP.SERV_TYPE AS servType,
        D.NETWORK_TYPE AS networkType,
        D.NETWORK_TYPE_NAME AS networkTypeName
    FROM DEPEND_PROD_SPEC DP
    JOIN OFFER O
      ON DP.DEPEND_PROD_SPEC_ID = O.OFFER_ID
    LEFT JOIN (
        SELECT A.SERV_TYPE,
               B.NETWORK_TYPE,
               B.NETWORK_TYPE_NAME
        FROM ALL_SERV_TYPE A
        JOIN NETWORK_TYPE B
          ON A.NETWORK_TYPE = B.NETWORK_TYPE
    ) D
      ON D.SERV_TYPE = DP.SERV_TYPE
    WHERE O.STATE = 'A'
      AND DP.IS_PACKAGE = :isPackage
      AND DP.SERV_TYPE = :servType
      AND D.NETWORK_TYPE = :networkType
      AND (:offerName IS NULL OR UPPER(O.OFFER_NAME) LIKE '%' || UPPER(:offerName) || '%')
      AND NVL(O.SP_ID,0) = :spId
    """,
          nativeQuery = true)
  List<DependOfferForRelaProjection> findDependOfferForRela(
          @Param("isPackage") String isPackage,
          @Param("servType") Integer servType,
          @Param("networkType") Character networkType,
          @Param("offerName") String offerName,
          @Param("spId") Integer spId);


  @Query(value = """
      SELECT A.OFFER_RELA_ID,
       A.RELA_TYPE          AS relaType,
        A.ORI_LOWER_LIMIT   AS oriLowerLimit,
        A.ORI_UPPER_LIMIT   AS oriUpperLimit,
        B.OFFER_TYPE        AS oriOfferType,
        C.OFFER_GROUP_TYPE  AS oriOfferGroupOfferType,
        D.OFFER_TYPE        AS destOfferType,
        E.OFFER_GROUP_TYPE  AS destOfferGroupOfferType,
        B.OFFER_NAME        AS oriOfferName,
        C.OFFER_GROUP_NAME  AS oriOfferGroupName,
        D.OFFER_NAME        AS destOfferName,
        E.OFFER_GROUP_NAME  AS destOfferGroupName,
        CC.OFFER_NAME       AS oriIndOfferName,
        CDO.OFFER_NAME      AS oriSubsPlanName,
        CE.EFF_DATE         AS oriEffDate,
        CE.EXP_DATE         AS oriExpDate,
        EC.OFFER_NAME       AS destIndOfferName,
        EDO.OFFER_NAME      AS destSubsPlanName,
        EE.EFF_DATE         AS destEffDate,
        EE.EXP_DATE         AS destExpDate
      FROM OFFER_RELA A, OFFER B, OFFER_GROUP C, OFFER CC,
        SUBS_PLAN  CD, OFFER CDO ,OFFER_VER CE, OFFER D, OFFER_GROUP E,  OFFER  EC,
        SUBS_PLAN  ED, OFFER EDO ,OFFER_VER EE 
      WHERE A.ORI_OFFER_ID = B.OFFER_ID
        AND A.ORI_OFFER_GROUP_ID = C.OFFER_GROUP_ID
        AND A.DEST_OFFER_ID = D.OFFER_ID
        AND A.DEST_OFFER_GROUP_ID = E.OFFER_GROUP_ID
        AND C.INDEP_PROD_SPEC_ID = CC.OFFER_ID
        AND CD.SUBS_PLAN_ID = CDO.OFFER_ID
        AND CE.OFFER_ID = CD.SUBS_PLAN_ID
        AND C.OFFER_VER_ID = CE.OFFER_VER_ID
        AND E.INDEP_PROD_SPEC_ID = EC.OFFER_ID
        AND ED.SUBS_PLAN_ID = EDO.OFFER_ID
        AND EE.OFFER_ID = ED.SUBS_PLAN_ID
        AND E.OFFER_VER_ID = EE.OFFER_VER_ID
    """,
    countQuery = """
      SELECT count(*)
      FROM OFFER_RELA A, OFFER B, OFFER_GROUP C, OFFER CC,
        SUBS_PLAN  CD, OFFER CDO ,OFFER_VER CE, OFFER D, OFFER_GROUP E,  OFFER  EC,
        SUBS_PLAN  ED, OFFER EDO ,OFFER_VER EE 
      WHERE A.ORI_OFFER_ID = B.OFFER_ID
        AND A.ORI_OFFER_GROUP_ID = C.OFFER_GROUP_ID
        AND A.DEST_OFFER_ID = D.OFFER_ID
        AND A.DEST_OFFER_GROUP_ID = E.OFFER_GROUP_ID
        AND C.INDEP_PROD_SPEC_ID = CC.OFFER_ID
        AND CD.SUBS_PLAN_ID = CDO.OFFER_ID
        AND CE.OFFER_ID = CD.SUBS_PLAN_ID
        AND C.OFFER_VER_ID = CE.OFFER_VER_ID
        AND E.INDEP_PROD_SPEC_ID = EC.OFFER_ID
        AND ED.SUBS_PLAN_ID = EDO.OFFER_ID
        AND EE.OFFER_ID = ED.SUBS_PLAN_ID
        AND E.OFFER_VER_ID = EE.OFFER_VER_ID
    """,nativeQuery = true)
    Page<OfferRelaProjection> findOfferRela(Pageable pageable);
}
