package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcmCalc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculateRepository extends JpaRepository<AcmCalc, Integer> {
}
