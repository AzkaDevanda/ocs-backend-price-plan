
package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcctPricePlan;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface AcctPricePlanRepository extends JpaRepository<AcctPricePlan, Integer> {

}
