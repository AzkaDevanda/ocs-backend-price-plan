package com.ocs.portal.repository;

import com.ocs.portal.entity.AdjustPrepareItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjustPrepareItemRepository extends JpaRepository<AdjustPrepareItem, Long> {
    boolean existsByAcctItemTypeId(Integer acctItemTypeId);
}
