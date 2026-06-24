package com.sts.sinorita.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.LifeCycleType;
import com.sts.sinorita.projection.offer.commonoffer.LifeCycleTypeProjection;

@Repository
public interface LifeCycleTypeRepository extends JpaRepository<LifeCycleType, Integer> {
  @Query(value = """
            SELECT
              LIFECYCLE_TYPE as lifecycleType,
              LIFECYCLE_TYPE_NAME as lifecycleTypeName,
              COMMENTS as comments,
              SP_ID as spId,
              EXT_ATTR as extAttr
            FROM LIFECYCLE_TYPE
            WHERE 0=0
              AND LIFECYCLE_TYPE = :LIFECYCLE_TYPE
              AND NVL(SP_ID,0)= :SP_ID
      ORDER BY LIFECYCLE_TYPE_NAME
            """, nativeQuery = true)
  Optional<LifeCycleTypeProjection> findLifecycleType(@Param("LIFECYCLE_TYPE") Integer lifecycleType, @Param("SP_ID") Integer spId);
}
