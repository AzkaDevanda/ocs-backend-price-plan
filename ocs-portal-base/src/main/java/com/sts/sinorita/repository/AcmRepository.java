package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Acm;
import com.sts.sinorita.projection.pricePlan.price.AcmListProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AcmRepository extends JpaRepository<Acm, Integer> {
    @Modifying
    @Transactional
    @Query("update Acm set value = :refValueId where id = :id")
    void insertValue(@Param("refValueId") Integer refValueId, @Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("update Acm set refValueId = :refValueId where id = :id")
    void insertRefValueId(@Param("refValueId") Integer refValueId, @Param("id") Integer id);

    @Query(value = """
              SELECT
              	A.id AS priceVerId,
              	H.valueString AS accumulation,
              	A.rum AS rum,
              	B.id AS resourceId,
              	B.resourceName AS resourceName,
              	C.id AS reAttr,
              	C.reAttrName AS reAttrName,
              	D.effDate AS effDate,
              	D.expDate AS expDate,
              	A.acmName AS acmName,
              	A.priceId AS priceId,
              	A.srcPriceId AS srcPriceId,
              	A.comments AS comments,
              	A.refValueId AS refValueId,
              	A.shareFlag AS shareFlag,
              	D.ratePlanId AS ratePlanId,
              	G.ratePlanType AS ratePlanType,
              	H.offerVerId AS offerVerId,
              	D.mappingId AS mappingId
              FROM
              	Acm A
              JOIN RefValue E ON
              	A.refValueId = E.id
              LEFT JOIN RatableResource B ON
              	TO_NUMBER(E.valueString) = B.id
              LEFT JOIN TableParamDefine F ON
              	E.tableParamId = F.id
              LEFT JOIN ReAttr C ON
              	B.reAttr = C.id
              LEFT JOIN PriceVer D ON
              	A.id = D.id
              LEFT JOIN RatePlan G ON
              	D.ratePlanId = G.id
              LEFT JOIN RefValue H ON
              	A.value = H.id
              WHERE
              	(:RATE_PLAN_ID IS NULL
              		OR D.ratePlanId = :RATE_PLAN_ID)
              	AND (:PRICE_ID IS NULL
              		OR A.priceId = :PRICE_ID)
              	AND (:MAPPING_ID IS NULL
              		OR D.mappingId = :MAPPING_ID)
            """)
    public List<AcmListProjection> getAcmList(@Param("RATE_PLAN_ID") Integer ratePlanId, @Param("PRICE_ID") Long priceId, @Param("MAPPING_ID") Integer mappingId);

    @Transactional
    @Modifying
    @Query("DELETE FROM AcmRef r WHERE r.priceVerId = :priceVerId")
    Integer deleteAcmRefByPriceVerId(@Param("priceVerId") Integer priceVerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM AcmRule r WHERE r.priceVerId = :priceVerId")
    Integer deleteAcmRuleByPriceVerId(@Param("priceVerId") Integer priceVerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM AcmTimeSpan r WHERE r.priceVerId = :priceVerId")
    Integer deleteAcmTimeSpanByPriceVerId(@Param("priceVerId") Integer priceVerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Acm r WHERE r.id = :priceVerId")
    Integer deleteAcmByPriceVerId(@Param("priceVerId") Integer priceVerId);

}
