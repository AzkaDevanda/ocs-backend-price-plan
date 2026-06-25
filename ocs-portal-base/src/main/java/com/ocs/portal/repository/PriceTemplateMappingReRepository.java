package com.ocs.portal.repository;

import com.ocs.portal.entity.PriceTemplateMappingRe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceTemplateMappingReRepository extends JpaRepository<PriceTemplateMappingRe, Integer> {

    @Modifying
    @Query("DELETE FROM PriceTemplateMappingRe p WHERE p.priceId = :priceId")
    void deleteByPriceId(@Param("priceId") Integer priceId);

}
