package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AdjustPrepareItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjustPrepareItemRepository extends JpaRepository<AdjustPrepareItem, Long> {
    boolean existsByAcctItemTypeId(Integer acctItemTypeId);
}
