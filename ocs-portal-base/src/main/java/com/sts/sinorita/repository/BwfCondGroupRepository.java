package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BwfCondGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BwfCondGroupRepository extends JpaRepository<BwfCondGroup,Integer> {

    @Query(value = "select max(COND_GROUP_ID) from BWF_COND_GROUP", nativeQuery = true)
    Integer findMaxId();

    @Modifying
    @Transactional
    @Query("DELETE FROM BwfCondGroup g WHERE g.stepId = :stepId")
    void deleteByStepId(@Param("stepId") Integer stepId);


}
