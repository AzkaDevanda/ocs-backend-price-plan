package com.sts.sinorita.repository;

import com.sts.sinorita.entity.OfferCatgApplyChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OfferCatgApplyChannelRepository extends JpaRepository<OfferCatgApplyChannel, Long> {
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM OFFER_CATG_APPLY_CHANNEL
        WHERE OFFER_CATG_ID = :offerCatgId
        """, nativeQuery = true)
    void deleteOfferCatgApplyChannel(@Param("offerCatgId") Integer offerCatgId);

}
