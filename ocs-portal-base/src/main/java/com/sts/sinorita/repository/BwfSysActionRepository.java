package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BwfSysAction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BwfSysActionRepository extends JpaRepository<BwfSysAction,Integer> {

    @Query(value = "select max(SYS_ACTION_ID) from BWF_SYS_ACTION", nativeQuery = true)
    Integer findMaxActionId();

    @Modifying
    @Transactional
    @Query("DELETE FROM BwfSysAction s WHERE s.stepId = :stepId")
    void deleteByStepId(@Param("stepId") Integer stepId);

    @Query(value = "SELECT NVL(MAX(SYS_ACTION_ID) + 1, 1) FROM BWF_SYS_ACTION", nativeQuery = true)
    Integer getNextSysActionId();

    @Query(value = "select s from BwfSysAction s where s.stepId = :stepId")
    Optional<BwfSysAction> findByStepId(@Param("stepId") Integer stepId);


}
