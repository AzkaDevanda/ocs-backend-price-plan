package com.ocs.portal.repository;

import com.ocs.portal.entity.ReUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReUsageRepository extends JpaRepository<ReUsage, Integer> {
}
