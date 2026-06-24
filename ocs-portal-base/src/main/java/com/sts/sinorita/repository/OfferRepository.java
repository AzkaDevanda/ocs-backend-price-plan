package com.sts.sinorita.repository;

import com.sts.sinorita.dto.request.OfferDetailProjection;
import com.sts.sinorita.dto.response.offer.GetRelaTypeProjection;
import com.sts.sinorita.dto.response.offer.GetSalesRuleScript;
import com.sts.sinorita.entity.Offer;
import com.sts.sinorita.projection.acct.QryAcctPricePlanProjection;
import com.sts.sinorita.projection.offer.IndepProdSpecByNameProjection;
import com.sts.sinorita.projection.offer.OfferByNameProjection;
import com.sts.sinorita.projection.offer.SelectProdSpecByProdIdProjection;
import com.sts.sinorita.projection.offer.commonoffer.PricePlanByIdProjection;
import com.sts.sinorita.projection.pricePlan.SubsPricePlanProjection;
import com.sts.sinorita.projection.pricePlan.discount.QryMemberAliasProjection;
import com.sts.sinorita.repository.customRepository.OfferCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer>, OfferCustomRepository {
  Offer findByOfferCode (String offerCode);

  @Modifying
  @Query(value = "UPDATE OFFER SET STATE = :state WHERE OFFER_ID = :id", nativeQuery = true)
  void updateOfferState (@Param("state") Character state, @Param("id") Long id);

  @Query(value = """
    SELECT offer.id,offer.offerName, offer.offerCode, ppt.pricePlanTypeName,offer.effDate, ov.id AS offerVerId
          FROM Offer offer\s
          INNER JOIN OfferVer ov ON ov.offerId = offer.id
          INNER JOIN PricePlan pp ON pp.id = offer.id
          INNER JOIN SubsPricePlan spp ON spp.id = pp.id
          INNER JOIN PricePlanType ppt ON ppt.id = spp.pricePlanType
          where offer.state = 'A' AND offer.offerType = '4'
    """, countQuery = """
            SELECT COUNT(offer.id)\s
                   FROM Offer offer\s
                   INNER JOIN OfferVer ov ON ov.offerId = offer.id
                   INNER JOIN PricePlan pp ON pp.id = offer.id
                   INNER JOIN SubsPricePlan spp ON spp.id = pp.id
                   INNER JOIN PricePlanType ppt ON ppt.id = spp.pricePlanType
                   where offer.state = 'A' AND offer.offerType = '4'
    """)
    // select all subs price plan
  Page<Object[]> selectAllPricePlan (Pageable pageable);

  @Query(value = """
    SELECT offer.id,offer.offerName, offer.offerCode, ppt.pricePlanTypeName,offer.effDate, offer.expDate,ov.id AS offerVerId
          FROM Offer offer\s
          INNER JOIN OfferVer ov ON ov.offerId = offer.id
          INNER JOIN PricePlan pp ON pp.id = offer.id
          INNER JOIN AcctPricePlan spp ON spp.id = pp.id
          INNER JOIN PricePlanType ppt ON ppt.id = spp.pricePlanType
          where offer.state = 'A' AND offer.offerType = '4'
    """, countQuery = """
            SELECT COUNT(offer.id)\s
                   FROM Offer offer\s
                   INNER JOIN OfferVer ov ON ov.offerId = offer.id
                   INNER JOIN PricePlan pp ON pp.id = offer.id
                   INNER JOIN AcctPricePlan spp ON spp.id = pp.id
                   INNER JOIN PricePlanType ppt ON ppt.id = spp.pricePlanType
                   where offer.state = 'A' AND offer.offerType = '4'
    """)
    // select all acct price plan
  Page<Object[]> selectAllAcctPricePlan (Pageable pageable);

  @Query(value = """
    SELECT\s
       offer.OFFER_ID ,
       offer.offer_name,
       offer.offer_code,
       ppt.price_plan_type_name,
       offer.eff_date,
       offer.exp_date,
       ov.OFFER_VER_ID  AS offer_ver_id
               FROM\s
       OFFER offer
       INNER JOIN OFFER_VER ov ON ov.offer_id = offer.OFFER_ID
       INNER JOIN PRICE_PLAN pp ON pp.PRICE_PLAN_ID = offer.OFFER_ID
       INNER JOIN SUBS_PRICE_PLAN spp ON spp.PRICE_PLAN_ID = pp.PRICE_PLAN_ID\s
       INNER JOIN PRICE_PLAN_TYPE ppt ON ppt.PRICE_PLAN_TYPE  = spp.PRICE_PLAN_TYPE
               WHERE\s
       (:type IS NULL OR ppt.PRICE_PLAN_TYPE = :type)
       AND pp.apply_level = 'S'
       AND offer.state = 'A'
       AND offer.offer_type = '4'
       AND (:KEY IS NULL AND :value IS NULL OR  LOWER(:KEY) LIKE :value)
       CASE WHEN :sort IS NULL THEN NULL ELSE :sort END :sotyBy;
    """, nativeQuery = true)
    // select subs price plan by price plan type
  Page<Object[]> selectAllPricePlanByPricePlanTypes (@Param("type") Character type, @Param("KEY") String key,
                                                     @Param("value") String value, @Param("sort") String sort, @Param("sotyBy") String sotyBy,
                                                     Pageable pageable);

  @Query(value = """
     SELECT offer.id,offer.offerName, offer.offerCode, ppt.pricePlanTypeName, offer.effDate, offer.expDate, ov.id as offerVerId
     FROM Offer offer
     INNER JOIN OfferVer ov ON ov.offerId = offer.id
     INNER JOIN PricePlan pp ON pp.id = offer.id
     INNER join AcctPricePlan app on app.id = pp.id
     INNER join PricePlanType ppt on ppt.id = app.pricePlanType\s
     WHERE (:type IS NULL OR ppt.id = :type) and pp.applyLevel = 'A' and offer.state = 'A' AND offer.offerType = '4'
    \s""", countQuery = """
    SELECT COUNT(offer.id)
    FROM Offer offer
    INNER JOIN OfferVer ov ON ov.offerId = offer.id
    INNER JOIN PricePlan pp ON pp.id = offer.id
    INNER join AcctPricePlan app on app.id = pp.id
    INNER join PricePlanType ppt on ppt.id = app.pricePlanType\s
    WHERE (:type IS NULL OR ppt.id = :type) and pp.applyLevel = 'A' and offer.state = 'A' AND offer.offerType = '4'
    """)
    // select acct price plan by price plan type
  Page<Object[]> selectAcctPricePLan (@Param("type") Character type, Pageable pageable);

  @Query(value = "SELECT A.OFFER_CATG_ID ,A.OFFER_CATG_NAME,A.SEQ, A.OFFER_CATG_CODE,A.EFF_DATE,A.EXP_DATE,A.COMMENTS,"
    +
    "A.OFFER_CATG_TYPE,A.OFFER_CATG_CLASS,A.POLICY_FLAG FROM OFFER_CATG A WHERE A.STATE ='A' " +
    "AND A.OFFER_CATG_TYPE=:OFFER_CATG_TYPE " +
    "AND A.OFFER_CATG_CLASS=:OFFER_CATG_CLASS " +
    "AND A.OFFER_CATG_ID !=:OFFER_CATG_ID  " +
    "AND UPPER(A.OFFER_CATG_NAME) LIKE '%'||UPPER(:OFFER_CATG_NAME)||'%' " +
    "AND NVL(A.SP_ID,0) =:SP_ID " +
    "AND NVL(A.POLICY_FLAG,'N') =:POLICY_FLAG  " +
    "AND NOT EXISTS (SELECT 1 FROM OFFER_CATG_MEM B WHERE A.OFFER_CATG_ID=B.CHILD_OFFER_CATG_ID) " +
    "ORDER BY SEQ, OFFER_CATG_NAME ", nativeQuery = true)
  List<Object[]> selectPricePlanDefaultCatg (@Param("OFFER_CATG_NAME") String OFFER_CATG_NAME,
                                             @Param("OFFER_CATG_TYPE") Character OFFER_CATG_TYPE,
                                             @Param("OFFER_CATG_CLASS") Character OFFER_CATG_CLASS,
                                             @Param("OFFER_CATG_ID") Integer OFFER_CATG_ID,
                                             @Param("SP_ID") Integer SP_ID,
                                             @Param("POLICY_FLAG") Character POLICY_FLAG);

  @Query("select o from Offer o where o.offerCode = :offerCode ")
  Optional<Offer> findOfferByCode (@Param("offerCode") String offerCode);

  @Query("select o from Offer o where o.offerName =:offerName and o.offerType =:offerType and o.state = :state")
  Optional<Offer> findOfferByNameOfferTypeState (@Param("offerName") String offerName,
                                                 @Param("offerType") Character offerType,
                                                 @Param("state") Character state);

  @Modifying
  @Query(value = """
        DELETE FROM Offer
        WHERE id IN (
            SELECT offer_id FROM (
                SELECT pp.id AS offer_id
                FROM PricePlan pp
                JOIN SubsPricePlan spp ON spp.id = pp.id
                JOIN PricePlanType ppt ON ppt.id = spp.pricePlanType
                WHERE pp.id = :pricePlanId
            ) temp
        )
    """, nativeQuery = true)
  void deletePricePlan (@Param("pricePlanId") Long pricePlanId);

  @Query(value = """
        SELECT o.OFFER_ID as offerId, ov.OFFER_VER_ID as offerVerId,
         o.OFFER_NAME as offerName,o.OFFER_CODE as offerCode,ppt.PRICE_PLAN_TYPE_NAME as pricePlanTypeName,o.EFF_DATE as effDate,pp.PRICE_PLAN_ID as pricePlanId,
         pp.APPLY_LEVEL as applyLevel
        FROM offer o
        JOIN offer_ver ov ON ov.OFFER_ID = o.OFFER_ID
        JOIN price_plan pp ON pp.PRICE_PLAN_ID = o.OFFER_ID
        JOIN subs_price_plan spp ON spp.PRICE_PLAN_ID = pp.PRICE_PLAN_ID
        JOIN price_plan_type ppt ON ppt.PRICE_PLAN_TYPE = spp.PRICE_PLAN_TYPE
        WHERE o.OFFER_ID = :pricePlanId AND o.STATE = 'A'
    """, nativeQuery = true)
  Optional<OfferDetailProjection> findOfferDetailByPricePlanId (@Param("pricePlanId") Integer pricePlanId);

  @Query(value = """
        SELECT A.OFFER_ID, A.OFFER_NAME, A.OFFER_CODE, B.APPLY_LEVEL, C.OFFER_CATG_ID
        FROM OFFER A
        JOIN PRICE_PLAN B ON A.OFFER_ID = B.PRICE_PLAN_ID
        LEFT JOIN OFFER_CATG_MEM C ON A.OFFER_ID = C.OFFER_ID
        WHERE A.OFFER_TYPE = '4'
          AND A.STATE = 'A'
          AND (:offerName IS NULL OR UPPER(A.OFFER_NAME) LIKE CONCAT('%', UPPER(:offerName), '%'))
          AND (:offerCode IS NULL OR UPPER(A.OFFER_CODE) LIKE CONCAT('%', UPPER(:offerCode), '%'))
          AND (:policyFlagY IS NULL OR B.POLICY_FLAG = :policyFlagY)
          AND (:policyFlagN IS NULL OR (B.POLICY_FLAG = :policyFlagN OR B.POLICY_FLAG IS NULL))
          AND (:servType IS NULL OR B.SERV_TYPE = :servType)
          AND (:isPackage IS NULL OR NVL(B.IS_PACKAGE, 'N') = :isPackage)
          AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId)
          AND (
            :pricePlanTypeEmpty = true OR A.OFFER_ID IN (
                SELECT S.PRICE_PLAN_ID FROM SUBS_PRICE_PLAN S WHERE S.PRICE_PLAN_TYPE IN (:pricePlanType)
                UNION ALL
                SELECT T.PRICE_PLAN_ID FROM ACCT_PRICE_PLAN T WHERE T.PRICE_PLAN_TYPE IN (:pricePlanType)
            )
          )
        ORDER BY A.OFFER_NAME ASC
    """, nativeQuery = true)
  List<Object[]> findOfferFiltered (
    @Param("offerName") String offerName,
    @Param("offerCode") String offerCode,
    @Param("policyFlagY") String policyFlagY,
    @Param("policyFlagN") String policyFlagN,
    @Param("servType") String servType,
    @Param("isPackage") String isPackage,
    @Param("spId") Long spId,
    @Param("pricePlanType") List<String> pricePlanType,
    @Param("pricePlanTypeEmpty") boolean pricePlanTypeEmpty);

  @Query(value = """
        SELECT
            A.OFFER_ID, A.OFFER_TYPE, A.OFFER_NAME, A.OFFER_CODE, A.COMMENTS,
            A.SALE_LIST_PRICE , A.RENT_LIST_PRICE, A.EFF_DATE, A.EXP_DATE, A.AUTO_CONTINUE_FLAG,
            A.CYCLE_QUANTITY, A.TIME_UNIT, A.EXP_OFF, A.EXP_TIME_UNIT, A.DUPLICATE_FLAG, A.EFF_TYPE,
            B.PRICE_PLAN_ID, B.APPLY_LEVEL, B.SERV_TYPE, B.PRIORITY, B.IS_PACKAGE,
            COALESCE(C.PRICE_PLAN_TYPE, D.PRICE_PLAN_TYPE) AS PRICE_PLAN_TYPE,
            A.CREATED_DATE, A.AGREEMENT_EFF_TYPE, A.SALE_PRICE_GST_TYPE, A.RENT_PRICE_GST_TYPE,
            B.GROUP_TYPE, B.UPPER_LIMIT, B.LOWER_LIMIT
        FROM
            OFFER A
        JOIN
            PRICE_PLAN B ON A.OFFER_ID = B.PRICE_PLAN_ID
        LEFT JOIN
            SUBS_PRICE_PLAN C ON B.PRICE_PLAN_ID = C.PRICE_PLAN_ID
        LEFT JOIN
            ACCT_PRICE_PLAN D ON B.PRICE_PLAN_ID = D.PRICE_PLAN_ID
        WHERE
            A.STATE = 'A'
            AND B.PRICE_PLAN_ID = :pricePlanId
            AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId)
    """, nativeQuery = true)
  List<Object[]> findOfferDetail (@Param("pricePlanId") Integer pricePlanId, @Param("spId") Integer spId);

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
        NVL(SPP.PRICE_PLAN_TYPE, APP.PRICE_PLAN_TYPE) AS pricePlanType
    FROM OFFER_CATG_MEM A
    RIGHT JOIN OFFER B ON A.OFFER_ID = B.OFFER_ID
    JOIN PRICE_PLAN C ON B.OFFER_ID = C.PRICE_PLAN_ID
    LEFT JOIN SUBS_PRICE_PLAN SPP ON C.APPLY_LEVEL = 'S' AND SPP.PRICE_PLAN_ID = C.PRICE_PLAN_ID
    LEFT JOIN ACCT_PRICE_PLAN APP ON C.APPLY_LEVEL = 'A' AND APP.PRICE_PLAN_ID = C.PRICE_PLAN_ID
    WHERE B.STATE = 'A'
      AND (:offerCatgId = -1 OR A.OFFER_CATG_ID = :offerCatgId)
      AND (:servType IS NULL OR C.SERV_TYPE = :servType)
      AND (:isPackage IS NULL OR C.IS_PACKAGE = :isPackage)
      AND (:spId IS NULL OR NVL(B.SP_ID, 0) = :spId)
      AND (:policyFlag IS NULL OR NVL(C.POLICY_FLAG, 'N') = :policyFlag)
    """, countQuery = """
    SELECT COUNT(*)
    FROM OFFER_CATG_MEM A
    RIGHT JOIN OFFER B ON A.OFFER_ID = B.OFFER_ID
    JOIN PRICE_PLAN C ON B.OFFER_ID = C.PRICE_PLAN_ID
    LEFT JOIN SUBS_PRICE_PLAN SPP ON C.APPLY_LEVEL = 'S' AND SPP.PRICE_PLAN_ID = C.PRICE_PLAN_ID
    LEFT JOIN ACCT_PRICE_PLAN APP ON C.APPLY_LEVEL = 'A' AND APP.PRICE_PLAN_ID = C.PRICE_PLAN_ID
    WHERE B.STATE = 'A'
      AND (:offerCatgId = -1 OR A.OFFER_CATG_ID = :offerCatgId)
      AND (:servType IS NULL OR C.SERV_TYPE = :servType)
      AND (:isPackage IS NULL OR C.IS_PACKAGE = :isPackage)
      AND (:spId IS NULL OR NVL(B.SP_ID, 0) = :spId)
      AND (:policyFlag IS NULL OR NVL(C.POLICY_FLAG, 'N') = :policyFlag)
    """, nativeQuery = true)
  Page<Object[]> findOfferByCatg (
    @Param("offerCatgId") Integer offerCatgId,
    @Param("servType") Integer servType,
    @Param("isPackage") Character isPackage,
    @Param("spId") Integer spId,
    @Param("policyFlag") Character policyFlag,
    Pageable pageable);

  @Query(value = "SELECT T.id as offerId, T.salesRuleScript as salesRuleScript" +
    " FROM Offer T" +
    " WHERE 1=1" +
    " AND T.id=:OFFER_ID")
  Optional<GetSalesRuleScript> qrySalesRuleScriptByOfferId (@Param("OFFER_ID") Integer offerId);

  @Query(value = "SELECT RELA_TYPE as relaType,RELA_TYPE_NAME as relaTypeName\n" +
    "FROM RELA_TYPE\n" +
    "ORDER BY RELA_TYPE_NAME", nativeQuery = true)
  List<GetRelaTypeProjection> qryRelaType ();

  @Query(value = """
    SELECT A.OFFER_ID as offerId, A.OFFER_NAME as offerName, C.OFFER_CATG_ID as offerCatgId
     FROM OFFER A, INDEP_PROD_SPEC B, OFFER_CATG_MEM C
     WHERE A.OFFER_ID = B.INDEP_PROD_SPEC_ID
       AND A.OFFER_ID = C.OFFER_ID
       AND A.OFFER_TYPE = '2'
       AND A.STATE = 'A'
       AND UPPER(A.OFFER_NAME) LIKE '%'||UPPER(:OFFER_NAME)||'%'
    --  AND B.SERV_TYPE = :SERV_TYPE
       AND NVL(A.SP_ID,0)= :SP_ID\s
    """, nativeQuery = true)
  Page<IndepProdSpecByNameProjection> qryIndepProdSpecByName (
    @Param("OFFER_NAME") String offerName,
    @Param("SP_ID") Integer spId,
    Pageable pageable);

  @Query(value = """
    SELECT A.id AS pricePlanId, A.applyLevel AS applyLevel, A.priority AS priority, A.servType AS servType, A.isPackage AS isPackage, A.warnLevel AS warnLevel, B.pricePlanType AS pricePlanType, C.pricePlanTypeName AS pricePlanTypeName, D.offerName AS pricePlanName, D.offerCode AS pricePlanCode, D.saleListPrice AS saleListPrice, D.rentListPrice AS rentListPrice, D.effType AS effType, D.autoContinueFlag AS autoContinueFlag, D.cycleQuantity AS cycleQuantity, D.timeUnit AS timeUnit, D.duplicateFlag AS duplicateFlag, D.effDate AS effDate, D.expDate AS expDate, D.comments AS comments, D.state AS state, D.stateDate AS stateDate, D.createdDate AS createdDate FROM Offer D JOIN PricePlan A ON D.id = A.id JOIN SubsPricePlan B ON A.id = B.id JOIN PricePlanType C ON B.pricePlanType = C.id WHERE D.offerType = '4' AND D.state = 'A' AND A.applyLevel = 'S' AND (:pricePlanName IS NULL OR UPPER(D.offerName) LIKE CONCAT('%', UPPER(:pricePlanName), '%')) AND (:pricePlanCode IS NULL OR UPPER(D.offerCode) LIKE CONCAT('%', UPPER(:pricePlanCode), '%')) AND (:pricePlanType IS NULL OR B.pricePlanType = :pricePlanType) AND (:pricePlanIdSelf IS NULL OR A.id <> :pricePlanIdSelf) AND (:spId IS NULL OR COALESCE(A.spId, 0) = :spId)
    """)
  Page<SubsPricePlanProjection> findSubsPricePlan (
    @Param("pricePlanName") String pricePlanName,
    @Param("pricePlanCode") String pricePlanCode,
    @Param("pricePlanType") Character pricePlanType,
    @Param("pricePlanIdSelf") Integer pricePlanId,
    @Param("spId") Integer spId, Pageable pageable);

  @Query(value = """
    SELECT
                 A.id AS pricePlanId,
                 A.applyLevel AS applyLevel,
                 A.priority AS priority,
                 A.servType AS servType,
                 A.isPackage AS isPackage,
                 A.warnLevel AS warnLevel,
                 B.pricePlanType AS pricePlanType,
                 C.pricePlanTypeName AS pricePlanTypeName,
                 D.offerName AS pricePlanName,
                 D.offerCode AS pricePlanCode,
                 D.saleListPrice AS saleListPrice,
                 D.rentListPrice AS rentListPrice,
                 D.effType AS effType,
                 D.autoContinueFlag AS autoContinueFlag,
                 D.cycleQuantity AS cycleQuantity,
                 D.timeUnit AS timeUnit,
                 D.duplicateFlag AS duplicateFlag,
                 D.effDate AS effDate,
                 D.expDate AS expDate,
                 D.comments AS comments,
                 D.state AS state,
                 D.stateDate AS stateDate,
                 D.createdDate AS createdDate
             FROM
                 PricePlan A
             JOIN AcctPricePlan B ON A.id = B.id
             JOIN PricePlanType C ON B.pricePlanType = C.id
             JOIN Offer D ON A.id = D.id
             WHERE
                 D.offerType = '4'
                 AND D.state = 'A'
                 AND A.applyLevel = 'A'
                 AND (:pricePlanName IS NULL OR UPPER(D.offerName) LIKE CONCAT('%', UPPER(:pricePlanName), '%'))
                AND (:pricePlanCode IS NULL OR UPPER(D.offerCode) LIKE CONCAT('%', UPPER(:pricePlanCode), '%'))
                 AND (:pricePlanType IS NULL OR B.pricePlanType = :pricePlanType)
                 AND (:spId IS NULL OR COALESCE(A.spId, 0) = :spId)
             ORDER BY
                 A.priority
    """)
  Page<QryAcctPricePlanProjection> findAcctPricePlan (@Param("pricePlanName") String pricePlanName,
                                                      @Param("pricePlanType") Character pricePlanType,
                                                      @Param("pricePlanCode") String pricePlanCode,
                                                      @Param("spId") Integer spId, Pageable pageable);

  @Query("SELECT COUNT(o) > 0 FROM Offer o WHERE o.offerName = :offerName")
  boolean existsByOfferName (@Param("offerName") String offerName);

  @Query("SELECT COUNT(o) > 0 FROM Offer o WHERE o.offerName = :name AND o.offerType = :type AND o.spId = :spId AND o.state = 'A'")
  boolean existsByNameTypeAndState (@Param("name") String name, @Param("type") Character type,
                                    @Param("spId") Integer spId);

  @Query("SELECT COUNT(o) > 0 FROM Offer o WHERE o.offerName = :code AND o.offerType = :type AND o.spId = :spId AND o.state = 'A'")
  boolean existsByCodeTypeAndState (@Param("code") String code, @Param("type") Character type,
                                    @Param("spId") Integer spId);

  @Modifying
  @Transactional
  @Query("UPDATE Offer o SET o.state = :state, o.stateDate = :stateDate WHERE o.id = :offerId")
  void deactivateOfferById (@Param("state") Character state, @Param("stateDate") LocalDate stateDate, @Param("offerId") Integer offerId);

  @Modifying
  @Transactional
  @Query("DELETE FROM OfferCatGMem ocm WHERE ocm.offerId = :offerId")
  void deleteOfferCatgMemByOfferId (@Param("offerId") Integer offerId);

  @Modifying
  @Transactional
  @Query("UPDATE OfferGroup og SET og.state = :state, og.stateDate = :stateDate WHERE og.indepProdSpecId = :indepProdSpecId")
  void deactivateOfferGroupByIndepProdSpecId (@Param("state") Character state, @Param("stateDate") LocalDate stateDate, @Param("indepProdSpecId") Integer indepProdSpecId);

  @Modifying
  @Transactional
  @Query(" UPDATE Offer o SET o.state = :state, o.stateDate = :stateDate WHERE o.id IN (SELECT sp.id FROM SubsPlan sp WHERE sp.indepProdSpecId = :indepProdSpecId)")
  void deactivateOfferBySubsPlanIndepProdSpecId (@Param("state") Character state,
                                                 @Param("stateDate") LocalDate stateDate, @Param("indepProdSpecId") Integer indepProdSpecId);


  @Query(value = """
    SELECT
        A.OFFER_ID as offerId,
        A.OFFER_TYPE as offerType,
        A.OFFER_NAME as offerName,
        A.OFFER_CODE as offerCode,
        A.COMMENTS as comments,
        A.SALE_LIST_PRICE as saleListPrice,
        A.RENT_LIST_PRICE as rentListPrice,
        A.EFF_DATE as effDate,
        A.EXP_DATE as expDate,
        A.AUTO_CONTINUE_FLAG as autoContinueFlag,
        A.CYCLE_QUANTITY as cycleQuantity,
        A.TIME_UNIT as timeUnit,
        A.EXP_OFF as expOff,
        A.EXP_TIME_UNIT as expTimeUnit,
        A.DUPLICATE_FLAG as duplicateFlag,
        A.EFF_TYPE as effType,
        B.PRICE_PLAN_ID as pricePlanId,
        B.APPLY_LEVEL as applyLevel,
        B.SERV_TYPE as servType,
        B.PRIORITY as priority,
        B.IS_PACKAGE as isPackage,
        NVL(C.PRICE_PLAN_TYPE, D.PRICE_PLAN_TYPE) as pricePlanType,
        A.CREATED_DATE as createdDate,
        A.AGREEMENT_EFF_TYPE as agreementEffType,
        A.SALE_PRICE_GST_TYPE as salePriceGstType,
        A.RENT_PRICE_GST_TYPE as rentPriceGstType,
        B.GROUP_TYPE as groupType,
        B.UPPER_LIMIT as upperLimit,
        B.LOWER_LIMIT as lowerLimit
    FROM
        OFFER A
        JOIN PRICE_PLAN B ON A.OFFER_ID = B.PRICE_PLAN_ID
        LEFT JOIN SUBS_PRICE_PLAN C ON B.PRICE_PLAN_ID = C.PRICE_PLAN_ID
        LEFT JOIN ACCT_PRICE_PLAN D ON B.PRICE_PLAN_ID = D.PRICE_PLAN_ID
    WHERE
        A.STATE = 'A'
        AND B.PRICE_PLAN_ID = :pricePlanId
        AND NVL(A.SP_ID, 0) = 0
    """, nativeQuery = true)
  List<PricePlanByIdProjection> findPricePlanById (@Param("pricePlanId") Integer pricePlanId,
                                                   @Param("spId") Integer spId);

  @Query(value = """
    	SELECT DISTINCT A.OFFER_ID as offerId, A.OFFER_NAME as offerName, A.OFFER_ID as indepProdSpecId
    	FROM OFFER A, OFFER_CATG_MEM B
    	WHERE 1=1
    		AND A.OFFER_ID=B.OFFER_ID
    		AND A.STATE='A'
    		AND UPPER(A.OFFER_NAME) LIKE  UPPER('%' || :OFFER_NAME || '%')
    		AND A.OFFER_TYPE=:OFFER_TYPE AND A.OFFER_TYPE = '2'
    		AND NVL(A.SP_ID,0)= :SP_ID
    
    	UNION
    
    	SELECT DISTINCT A.OFFER_ID as offerId, A.OFFER_NAME as offerName, A.OFFER_ID as indepProdSpecId
    	FROM OFFER A, OFFER_CATG_MEM B
    	WHERE 1=1
    		AND A.OFFER_ID=B.OFFER_ID
    		AND A.STATE='A'
    		AND UPPER(A.OFFER_NAME) LIKE  UPPER('%' || :OFFER_NAME || '%')
    		AND A.OFFER_TYPE=:OFFER_TYPE AND A.OFFER_TYPE = '5'
    		AND NVL(A.SP_ID,0)= :SP_ID
    
    	UNION
    
    	SELECT DISTINCT A.OFFER_ID as offerId, A.OFFER_NAME as offerName, B.INDEP_PROD_SPEC_ID as indepProdSpecId
    	FROM OFFER A, SUBS_PLAN B
    	WHERE 1=1
    		AND A.OFFER_ID = B.SUBS_PLAN_ID
    		AND A.STATE = 'A'
    		AND B.IS_BUNDLE_FLAG = :IS_BUNDLE_FLAG
    		AND (B.IS_BUNDLE_FLAG = :IS_BUNDLE_FLAG OR B.IS_BUNDLE_FLAG IS NULL)
    		AND UPPER(A.OFFER_NAME) LIKE UPPER('%' || :OFFER_NAME || '%')
    		AND A.OFFER_TYPE=:OFFER_TYPE AND A.OFFER_TYPE = '7'
    		AND NVL(A.SP_ID,0)= :SP_ID
    """, nativeQuery = true)
  List<OfferByNameProjection> findOfferByName (@Param("OFFER_NAME") String offerName, @Param("OFFER_TYPE") String offerType, @Param("SP_ID") Integer spId, @Param("IS_BUNDLE_FLAG") Character isBundleFlag);

  @Query(value = """
    SELECT\s
        A.OFFER_NAME,
        A.OFFER_ID,
        'From ' || TO_CHAR(C.EFF_DATE, 'YYYY-MM-DD') ||
            CASE\s
                WHEN C.EXP_DATE IS NULL THEN ''
                ELSE ' To ' || TO_CHAR(C.EXP_DATE, 'YYYY-MM-DD')
            END AS OFFER_VER_NAME,
        C.OFFER_VER_ID,
        D.BUNDLE_UNIT_ID,
        D.MEMBER_ALIAS,
        F.INDEP_PROD_SPEC_ID
    FROM\s
        OFFER A
        JOIN OFFER_TYPE B ON A.OFFER_TYPE = B.OFFER_TYPE
        JOIN SUBS_PLAN F ON A.OFFER_ID = F.INDEP_PROD_SPEC_ID
        JOIN OFFER_VER C ON F.SUBS_PLAN_ID = C.OFFER_ID
        JOIN BUNDLE_UNIT D ON C.OFFER_VER_ID = D.OFFER_VER_ID
    WHERE\s
        B.OFFER_TYPE = 1
        AND NVL(A.SP_ID, 0) = '0'
        AND D.MEMBER_ALIAS IS NOT NULL
    ORDER BY\s
        A.OFFER_NAME,\s
        OFFER_VER_NAME,\s
        D.MEMBER_ALIAS
    """, nativeQuery = true)
  List<QryMemberAliasProjection> qryMemberAlias ();

  @Modifying
  @Transactional
  @Query(value = """
    UPDATE OFFER
    SET STATE = 'X', STATE_DATE = SYSDATE
    WHERE OFFER_ID = :offerId
    """, nativeQuery = true)
  void deactivateOffer (@Param("offerId") Integer offerId);

  @Query(value = "select o from Offer o where o.id =:id")
  Optional<Offer> getOfferById (@Param("id") Integer id);

  @Query(value = """
    SELECT
        OFFER_ID      AS offerId,
        OFFER_TYPE    AS offerType,
        OFFER_NAME    AS offerName,
        COMMENTS      AS comments,
        OFFER_CODE    AS offerCode,
        EFF_DATE      AS effDate,
        EXP_DATE      AS expDate,
        STATE         AS state,
        STATE_DATE    AS stateDate,
        SP_ID         AS spId
    FROM OFFER
    WHERE OFFER_ID = :offerId
    """, nativeQuery = true)
  Optional<SelectProdSpecByProdIdProjection> selectProdSpecByProdId(@Param("offerId") Long offerId);
}

