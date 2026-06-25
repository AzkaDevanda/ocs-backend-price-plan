package com.ocs.portal.repository;

import com.ocs.portal.entity.ReEbMappingPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReEbMappingPriceRepository extends JpaRepository<ReEbMappingPrice, Integer> {

    @Modifying
    @Query(value = """
            DELETE RpEbMappingPrice T WHERE T.id = :priceId
            """)
    void deleteReEbMappingBranchByPriceId(@Param("priceId") Integer priceId);

}
