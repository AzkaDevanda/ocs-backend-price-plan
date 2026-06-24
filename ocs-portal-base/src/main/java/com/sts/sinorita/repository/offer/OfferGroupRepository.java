package com.sts.sinorita.repository.offer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.OfferGroup;
import com.sts.sinorita.projection.offer.offergroup.OfferGroupAndMemberProjection;

@Repository
public interface OfferGroupRepository extends JpaRepository<OfferGroup, Integer> {
  @Query(value = """
      SELECT COUNT(1) COUNT
      FROM OFFER_GROUP A
      WHERE A.STATE = 'A'
        AND A.OFFER_GROUP_TYPE =:OFFER_GROUP_TYPE
        AND A.SHARE_FLAG =:SHARE_FLAG
        AND A.INDEP_PROD_SPEC_ID =:INDEP_PROD_SPEC_ID
        AND A.OFFER_VER_ID =:OFFER_VER_ID
        AND A.OFFER_GROUP_ID =:OFFER_GROUP_ID
        AND NVL(A.SP_ID, 0) =:SP_ID
      """, nativeQuery = true)
  Long findOfferGroupCount(@Param("OFFER_GROUP_TYPE") Integer offerGroupType, @Param("SHARE_FLAG") Integer shareFlag,
      @Param("INDEP_PROD_SPEC_ID") Integer indepProdSpecId, @Param("OFFER_VER_ID") Integer offerVerId,
      @Param("OFFER_GROUP_ID") Integer offerGroupId, @Param("SP_ID") Integer spId);

  @Query(value = """
      SELECT
        A.OFFER_GROUP_ID         AS offerGroupId,
        A.OFFER_GROUP_NAME       AS offerGroupName,
        A.OFFER_GROUP_CODE       AS offerGroupCode,
        A.OFFER_GROUP_TYPE       AS offerGroupType,
        A.GROUP_TYPE             AS groupType,
        A.UPPER_LIMIT            AS upperLimit,
        A.LOWER_LIMIT            AS lowerLimit,
        A.EFF_DATE               AS effDate,
        A.EXP_DATE               AS expDate,
        A.CREATED_DATE           AS createdDate,
        A.STATE                  AS state,
        A.STATE_DATE             AS stateDate,
        A.SHARE_FLAG             AS shareFlag,
        A.INDEP_PROD_SPEC_ID     AS indepProdSpecId,
        A.OFFER_VER_ID           AS offerVerId,
        A.COMMENTS               AS comments,
        A.NETWORK_TYPE           AS networkType
      FROM
        OFFER_GROUP A
      WHERE A.OFFER_GROUP_TYPE = :OFFER_GROUP_TYPE
        AND A.SHARE_FLAG = :SHARE_FLAG
        AND A.INDEP_PROD_SPEC_ID = :INDEP_PROD_SPEC_ID
        AND NVL(A.SP_ID, 0)= :SP_ID
      """, countQuery = """
      SELECT COUNT(*)
      FROM
        OFFER_GROUP A
      WHERE A.OFFER_GROUP_TYPE = :OFFER_GROUP_TYPE
        AND A.SHARE_FLAG = :SHARE_FLAG
        AND A.INDEP_PROD_SPEC_ID = :INDEP_PROD_SPEC_ID
        AND NVL(A.SP_ID, 0)= :SP_ID
      """, nativeQuery = true)
  Page<OfferGroupAndMemberProjection> findOfferGroupAndMember(@Param("OFFER_GROUP_TYPE") Integer offerGroupType, @Param("SHARE_FLAG") Integer shareFlag, @Param("INDEP_PROD_SPEC_ID") Integer indepProdSpecId, @Param("SP_ID") Integer spId, Pageable pageable);
}
