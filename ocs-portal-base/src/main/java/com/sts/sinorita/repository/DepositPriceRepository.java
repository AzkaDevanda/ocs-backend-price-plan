package com.sts.sinorita.repository;

import com.sts.sinorita.entity.DepositPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositPriceRepository extends JpaRepository<DepositPrice, Long> {
}
