package com.sts.sinorita.repository;

import com.sts.sinorita.entity.SubsPricePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsPricePlanRepository extends JpaRepository<SubsPricePlan, Integer> {
}
