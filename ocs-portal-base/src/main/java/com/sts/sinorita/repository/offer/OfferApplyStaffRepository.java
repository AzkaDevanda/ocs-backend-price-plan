package com.sts.sinorita.repository.offer;

import com.sts.sinorita.entity.OfferApplyStaff;
import com.sts.sinorita.entity.embeddable.OfferApplyStaffId;
import com.sts.sinorita.projection.offer.OfferApplyStaffProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferApplyStaffRepository extends JpaRepository<OfferApplyStaff, OfferApplyStaffId> {

    @Query(value = """
            SELECT O.OFFER_ID as offerId,
                   O.STAFF_ID as staffId,
                   O.SP_ID as spId,
                   O.EXCLUDE_FLAG as excludeFlag,
                   A.USER_CODE as userCode,
                   R.STAFF_NAME as staffName,
                   R.STAFF_NAME as conditionName,
                   R.STATE as state,
                   R.STATE_DATE as stateDate,
                   R.USER_ID as userId
             FROM OFFER_APPLY_STAFF O, BFM_STAFF R, BFM_USER A
             WHERE O.STAFF_ID = R.STAFF_ID
               AND O.OFFER_ID = :OFFER_ID
               AND R.USER_ID=A.USER_ID
               AND R.STATE = 'A'
             AND NVL(O.SP_ID,0)= :SP_ID
            """, nativeQuery = true)
    List<OfferApplyStaffProjection> qryOfferApplyStaff(@Param("OFFER_ID") Integer offerId, @Param("SP_ID") Integer spId);

}
