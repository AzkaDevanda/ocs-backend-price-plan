package com.sts.sinorita.repository;

import com.sts.sinorita.entity.GlAcctItemType;
import com.sts.sinorita.entity.GlAcctItemTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlAcctItemTypeRepository extends JpaRepository<GlAcctItemType, GlAcctItemTypeId> {
    boolean existsByIdAcctItemTypeId(Integer acctItemTypeId);
}
