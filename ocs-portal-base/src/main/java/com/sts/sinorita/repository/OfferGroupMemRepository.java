package com.sts.sinorita.repository;

import com.sts.sinorita.entity.OfferGroupMem;
import com.sts.sinorita.projection.offer.offergroupmem.OfferGroupMemByOfferIdProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferGroupMemRepository extends JpaRepository<OfferGroupMem, Integer> {

    @Query(value = """
            SELECT A.OFFER_GROUP_MEM_ID offerGroupMemId,
                   A.OFFER_GROUP_ID offerGroupId,
                   A.OFFER_ID offerId ,
                   A.AGREEMENT_PERIOD agreementPeriod,
                   A.TIME_UNIT timeUnit,
                   A.DEFAULT_FLAG defaultFlag,
                   B.OFFER_GROUP_NAME offerGroupName,
                   B.GROUP_TYPE groupType,
                   B.SHARE_FLAG shareFlag,
                   A.UPPER_LIMIT upperLimit,
                   A.LOWER_LIMIT lowerLimit,
                   A.DEFAULT_NUM defaultNum
             FROM OFFER_GROUP_MEM A, OFFER_GROUP B, OFFER C
             WHERE A.OFFER_GROUP_ID = B.OFFER_GROUP_ID
               AND A.OFFER_ID = C.OFFER_ID
               AND B.STATE = 'A'
               AND C.STATE = 'A'
               AND A.OFFER_ID = :OFFER_ID
               AND NVL(A.SP_ID,0)= :SP_ID
            """, nativeQuery = true)
    Page<OfferGroupMemByOfferIdProjection> qryOfferGroupMemByOfferId(@Param("OFFER_ID") Integer offerId, @Param("SP_ID") Integer spId, Pageable pageable);


    @Query(value = """
        SELECT COUNT(*)
        FROM OFFER_GROUP_MEM T1
        JOIN OFFER_GROUP T2 ON T1.OFFER_GROUP_ID = T2.OFFER_GROUP_ID
        WHERE T2.STATE = 'A'
          AND T1.OFFER_ID = :offerId
        """, nativeQuery = true)
    int countOfferGroupMembership(@Param("offerId") Integer offerId);
}
