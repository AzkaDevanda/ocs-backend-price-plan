package com.ocs.portal.repository;

import com.ocs.portal.entity.DistributeMethod;
import com.ocs.portal.projection.pricePlan.discount.DistributeMethodProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DistributeMethodRepository extends JpaRepository<DistributeMethod,Character> {
    @Query(value = """
    SELECT 
        A.DISTRIBUTE_METHOD AS distributeMethod,
        A.DISTRIBUTE_METHOD_NAME AS distributeMethodName
    FROM DISTRIBUTE_METHOD A
   """, nativeQuery = true)
    List<DistributeMethodProjection> getDistributeMethod();
}
