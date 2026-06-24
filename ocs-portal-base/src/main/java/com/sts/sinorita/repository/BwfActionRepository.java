package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BwfAction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BwfActionRepository extends JpaRepository<BwfAction,Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM BwfAction a WHERE a.id.stepId = :stepId")
    void deleteByStepId(@Param("stepId") Integer stepId);


    @Query(value = "select a from BwfAction a where a.id.stepId =:stepId")
    List<BwfAction> getListBwfAction(@Param("stepId")Long stepId);

}
