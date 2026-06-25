package com.ocs.portal.repository;

import com.ocs.portal.entity.ContactChannel;
import com.ocs.portal.projection.offer.commonoffer.ContactChanelProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactChannelRepository extends JpaRepository<ContactChannel, Integer> {
  @Query(value = """
      SELECT
          CONTACT_CHANNEL_ID AS contactChannelId,
          CONTACT_CHANNEL_NAME AS contactChannelName,
          COMMENTS as comments
      FROM CONTACT_CHANNEL
      WHERE (:channelType IS NULL OR CHANNEL_TYPE = :channelType)
        AND (:spId IS NULL OR SYSTEM_RESERVE = 'Y' OR NVL(SP_ID, 0) = :spId)
      ORDER BY CONTACT_CHANNEL_ID
      """, nativeQuery = true)
  List<ContactChanelProjection> findContactChannels(@Param("channelType") String channelType, @Param("spId") Integer spId);

  @Query(value = """
      SELECT 
        A.CONTACT_CHANNEL_ID as contactChannelId,
        A.CONTACT_CHANNEL_NAME as contactChannelName,
        A.SP_ID as spId,
        A.COMMENTS as comments,
        A.CHANNEL_TYPE as channelType,
        C.CHANNEL_TYPE_NAME as channelTypeName
      FROM CONTACT_CHANNEL A, CHANNEL_TYPE C
      WHERE 1 = 1
        AND A.CHANNEL_TYPE = C.CHANNEL_TYPE
        AND A.CONTACT_CHANNEL_ID = :CONTACT_CHANNEL_ID
        AND (A.SYSTEM_RESERVE='Y' OR NVL(A.SYSTEM_RESERVE,'N') !='Y' AND NVL(A.SP_ID, 0) = :SP_ID)
      ORDER BY A.CONTACT_CHANNEL_NAME
    """, nativeQuery = true)
  List<ContactChanelProjection> findContactChannelList(@Param("CONTACT_CHANNEL_ID") Integer contactChannelId, @Param("SP_ID") Integer spId);
}
