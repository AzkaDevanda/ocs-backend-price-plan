package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Rp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RpRepository extends JpaRepository<Rp, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM RpMappingBranch r WHERE r.id = :rpId")
    void deleteRpMappingBranchByRpId(Integer rpId);

}
