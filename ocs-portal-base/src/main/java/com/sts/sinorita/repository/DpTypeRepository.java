package com.sts.sinorita.repository;

import com.sts.sinorita.entity.DpType;
import com.sts.sinorita.projection.pricePlan.discount.DpTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DpTypeRepository extends JpaRepository<DpType,Character> {
    @Query(value = """
    SELECT 
        A.DP_TYPE      AS dpType,
        A.DP_TYPE_NAME AS dpTypeName,
        A.COMMENTS     AS comments
    FROM DP_TYPE A
    """, nativeQuery = true)
    List<DpTypeProjection> findDpTypeNames();
}
