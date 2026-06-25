package com.ocs.portal.repository;

import com.ocs.portal.entity.PriceTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceTaxRepository extends JpaRepository<PriceTax, Integer> {

    @Modifying
    @Query(value = "delete from price_tax where price_id = :priceId", nativeQuery = true)
    void deletePriceTaxByPriceId(@Param("priceId") Integer priceId);

}
