package com.ocs.portal.repository;

import com.ocs.portal.entity.TabDpType;
import com.ocs.portal.projection.pricePlan.discount.TabDpTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TabDpTypeRepository extends JpaRepository<TabDpType,Character> {
    @Query(value = """
    SELECT
        A.TAB_DP_TYPE      AS tabDpType,
        A.TAB_DP_TYPE_NAME AS tabDpTypeName
    FROM TAB_DP_TYPE A
    """, nativeQuery = true)
    List<TabDpTypeProjection> findTabDpType();
}
