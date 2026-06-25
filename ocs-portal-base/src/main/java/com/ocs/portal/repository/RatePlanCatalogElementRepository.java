package com.ocs.portal.repository;

import com.ocs.portal.entity.RatePlanCatalogElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatePlanCatalogElementRepository extends JpaRepository<RatePlanCatalogElement, Integer> {
}
