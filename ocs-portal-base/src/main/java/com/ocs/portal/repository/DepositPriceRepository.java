package com.ocs.portal.repository;

import com.ocs.portal.entity.DepositPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositPriceRepository extends JpaRepository<DepositPrice, Long> {
}
