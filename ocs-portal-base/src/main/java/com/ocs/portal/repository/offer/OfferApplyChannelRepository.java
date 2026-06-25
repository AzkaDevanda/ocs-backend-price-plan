package com.ocs.portal.repository.offer;

import com.ocs.portal.entity.OfferApplyChannel;
import com.ocs.portal.projection.offer.offerapplychannel.OfferApplyChannelProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferApplyChannelRepository extends JpaRepository<OfferApplyChannel, Integer> {

    @Query(value = """
            
            SELECT
                O.OFFER_ID offerId,
                O.CONTACT_CHANNEL_ID contactChannelId,
                O.SP_ID spId,
                O.EXCLUDE_FLAG excludeFlag,
                C.CHANNEL_TYPE channelType,
                C.CONTACT_CHANNEL_NAME contactChannelName,
                C.CONTACT_CHANNEL_NAME AS conditionName,
                C.COMMENTS comments
            FROM OFFER_APPLY_CHANNEL O , CONTACT_CHANNEL C
            WHERE O.CONTACT_CHANNEL_ID = C.CONTACT_CHANNEL_ID
            AND O.OFFER_ID=:OFFER_ID
            AND NVL(O.SP_ID,0)= :SP_ID
            """, nativeQuery = true)
    List<OfferApplyChannelProjection> qryOfferApplyChannel(@Param("OFFER_ID") Integer offerId, @Param("SP_ID") Integer spId);

}
