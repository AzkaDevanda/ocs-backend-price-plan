package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AttrApplyChannel;
import com.sts.sinorita.entity.AttrApplyChannelId;
import com.sts.sinorita.projection.offer.commonoffer.AttrApplyChannelProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AttrApplyChannelRepository extends JpaRepository<AttrApplyChannel, AttrApplyChannelId> {
    @Query(value = """
        SELECT 
            ATTR_ID AS attrId,
            CONTACT_CHANNEL_ID AS contactChannelId,
            SP_ID AS spId
        FROM ATTR_APPLY_CHANNEL
        WHERE ATTR_ID = :attrId
          AND NVL(SP_ID, 0) = 0
        """, nativeQuery = true)
    List<AttrApplyChannelProjection> findByAttrApplyChannel(@Param("attrId") Integer attrId);
}
