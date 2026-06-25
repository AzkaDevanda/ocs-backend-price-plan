package com.ocs.portal.repository;

import com.ocs.portal.entity.GlAcctItemType;
import com.ocs.portal.entity.GlAcctItemTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlAcctItemTypeRepository extends JpaRepository<GlAcctItemType, GlAcctItemTypeId> {
    boolean existsByIdAcctItemTypeId(Integer acctItemTypeId);
}
