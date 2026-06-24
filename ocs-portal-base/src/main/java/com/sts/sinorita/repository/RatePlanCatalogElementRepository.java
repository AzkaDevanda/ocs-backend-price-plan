package com.sts.sinorita.repository;

import com.sts.sinorita.entity.RatePlanCatalogElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatePlanCatalogElementRepository extends JpaRepository<RatePlanCatalogElement, Integer> {
}
