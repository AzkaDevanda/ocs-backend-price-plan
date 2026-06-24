package com.sts.sinorita.repository;

import com.sts.sinorita.entity.RatableResource;
import com.sts.sinorita.projection.balanceAdjustment.SelectRatableResourceProjection;
import com.sts.sinorita.projection.pricePlan.AccumulationTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatableResourceRepository extends JpaRepository<RatableResource, Integer> {

  @Query(value = "SELECT rr.RESOURCE_ID AS resourceId , rr.RESOURCE_NAME resourceName, ra.RE_ATTR as reAttrId, ra.RE_ATTR_NAME AS reAttrName FROM RATABLE_RESOURCE rr JOIN RE_ATTR ra ON rr.RE_ATTR = ra.RE_ATTR ORDER BY rr.RESOURCE_NAME ASC", nativeQuery = true)
  List<AccumulationTypeProjection> findAllAccumulationType ();

  @Query(value = "SELECT A.RESOURCE_ID AS resourceId, A.ACM_TYPE AS acmType, A.MASK AS mask, A.RESOURCE_NAME AS resourceName, A.COMMENTS AS comments, A.SP_ID AS spId, A.UNIT_TYPE_ID AS unitTypeId, A.UNIT_PRECISION AS unitPrecision, B.UNIT_TYPE_NAME AS unitTypeName, A.PRECISION AS precisionValue, A.ROUND_WAY AS roundWay, A.BILLING_CYCLE_TYPE_ID AS billingCycleTypeId FROM RATABLE_RESOURCE A LEFT JOIN UNIT_TYPE B ON A.UNIT_TYPE_ID = B.UNIT_TYPE_ID WHERE A.RESOURCE_ID = :resourceId", nativeQuery = true)
  Optional<SelectRatableResourceProjection> selectRatableResource (@Param("resourceId") Long resourceId);
}
