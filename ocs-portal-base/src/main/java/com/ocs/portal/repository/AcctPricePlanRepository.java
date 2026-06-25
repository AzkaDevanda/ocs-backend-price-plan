package com.ocs.portal.repository;


import com.ocs.portal.entity.AcctPricePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcctPricePlanRepository extends JpaRepository<AcctPricePlan, Integer> {

}
