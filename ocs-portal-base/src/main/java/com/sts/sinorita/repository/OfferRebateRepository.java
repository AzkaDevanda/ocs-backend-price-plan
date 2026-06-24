package com.sts.sinorita.repository;

import com.sts.sinorita.entity.OfferRebate;
import com.sts.sinorita.projection.offer.offerrebate.OfferRebateProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRebateRepository extends JpaRepository<OfferRebate, Integer> {

  List<OfferRebate> findByOfferId(Integer offerId);

  List<OfferRebate> findByOfferIdAndOfferVerId(Integer offerId, Integer offerVerId);

  @Query(value = """
       SELECT A.OFFER_REBATE_ID offerRebateId,
             A.OFFER_ID offerId,
             A.OFFER_VER_ID offerVerId,
             A.REBATE_TYPE rebateType,
             A.REBATE_COUNT rebateCount,
             A.REBATE_SEQ rebateSeq,
             A.VALUE value,
             A.COMMENTS comments,
             A.SP_ID spId,
             B.EFF_DATE|| ' -- '||B.EXP_DATE AS NAME
        FROM OFFER_REBATE A, OFFER_VER B
       WHERE 1 = 1
         AND A.OFFER_VER_ID = B.OFFER_VER_ID(+)
         AND A.OFFER_ID = :OFFER_ID
         AND A.OFFER_VER_ID = :OFFER_VER_ID\s
         AND A.SP_ID = :SP_ID\s
        ORDER BY A.REBATE_SEQ
      """, nativeQuery = true)
  List<OfferRebateProjection> qryOfferRebate(@Param("OFFER_ID") Integer offerId,
      @Param("OFFER_VER_ID") Integer offerVerId, @Param("SP_ID") Integer spId);

}
