package com.ocs.portal.repository;

import com.ocs.portal.entity.AcmCalc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculateRepository extends JpaRepository<AcmCalc, Integer> {
}
