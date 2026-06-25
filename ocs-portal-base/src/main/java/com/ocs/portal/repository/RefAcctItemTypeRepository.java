package com.ocs.portal.repository;

import com.ocs.portal.entity.RefAcctItemType;
import com.ocs.portal.entity.RefAcctItemTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RefAcctItemTypeRepository extends JpaRepository<RefAcctItemType, RefAcctItemTypeId> {
    boolean existsByIdAcctItemTypeId(Integer acctItemTypeId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM REF_ACCT_ITEM_TYPE WHERE DP_ID = :id",nativeQuery = true)
    void deleteByDpId(@Param("id") Integer id);
}
