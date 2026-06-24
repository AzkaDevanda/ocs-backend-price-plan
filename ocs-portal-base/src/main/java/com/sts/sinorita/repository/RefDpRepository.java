package com.sts.sinorita.repository;

import com.sts.sinorita.entity.RefDp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RefDpRepository extends JpaRepository<RefDp, Integer> {
    boolean existsByResultAcctItemTypeId(Integer id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM REF_DP WHERE DP_ID = :id",nativeQuery = true)
    void deleteByDpId(@Param("id") Integer id);
}
