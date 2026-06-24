package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcctItemTaxApply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcctItemTaxApplyRepository extends JpaRepository<AcctItemTaxApply, Integer> {
    void deleteByAcctItemTypeId(Integer acctItemTypeId);
}
