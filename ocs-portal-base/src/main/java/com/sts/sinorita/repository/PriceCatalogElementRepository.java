package com.sts.sinorita.repository;

import com.sts.sinorita.entity.PriceCatalogElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceCatalogElementRepository extends JpaRepository<PriceCatalogElement, Integer> {

    @Modifying
    @Query("DELETE FROM PriceCatalogElement pc WHERE pc.priceId = :priceId")
    void deletePriceCatalogElementByPriceId(@Param("priceId") Integer priceId);
}
