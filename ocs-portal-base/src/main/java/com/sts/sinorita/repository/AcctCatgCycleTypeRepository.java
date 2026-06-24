package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcctCatgCycleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcctCatgCycleTypeRepository extends JpaRepository<AcctCatgCycleType, Integer> {
    boolean existsByBillingCycleTypeId(Integer billingCycleTypeId);
}
