package com.sts.sinorita.repository;

import com.sts.sinorita.entity.ReUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReUsageRepository extends JpaRepository<ReUsage, Integer> {
}
