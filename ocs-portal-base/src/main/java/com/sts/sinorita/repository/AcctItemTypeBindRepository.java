package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcctItemTypeBind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AcctItemTypeBindRepository extends JpaRepository<AcctItemTypeBind, Integer> {
    @Modifying
    @Query("DELETE FROM AcctItemTypeBind b WHERE b.acctItemTypeId = :acctItemTypeId")
    void deleteByAcctItemTypeId(@Param("acctItemTypeId") Integer acctItemTypeId);

    boolean existsByBindAcctItemTypeId(Integer bindAcctItemTypeId);
}
