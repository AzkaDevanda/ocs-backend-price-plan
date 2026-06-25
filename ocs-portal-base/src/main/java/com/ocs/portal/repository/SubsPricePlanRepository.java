package com.ocs.portal.repository;

import com.ocs.portal.entity.SubsPricePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsPricePlanRepository extends JpaRepository<SubsPricePlan, Integer> {
}
