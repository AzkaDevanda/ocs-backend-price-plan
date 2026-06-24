package com.sts.sinorita.repository;

import com.sts.sinorita.entity.OverduePlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverduePlanItemRepository extends JpaRepository<OverduePlanItem, Integer> {
    boolean existsByAcctItemTypeId(Integer id);
}
