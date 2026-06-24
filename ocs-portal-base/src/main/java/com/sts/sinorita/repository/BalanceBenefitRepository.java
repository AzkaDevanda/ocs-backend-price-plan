package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BalBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceBenefitRepository extends JpaRepository<BalBenefit,Integer> {

    @Query(value = "Select balBen from BalBenefit balBen where balBen.id.balThresholdId =:balThresholdId " +
            " AND balBen.id.subBalTypeId =:subBalTypeId")
    public Optional<BalBenefit> findBySubTresholdAndSubBalType(@Param("balThresholdId")Integer balThresholdId,
                                                               @Param("subBalTypeId")Integer subBalTypeId);
}
