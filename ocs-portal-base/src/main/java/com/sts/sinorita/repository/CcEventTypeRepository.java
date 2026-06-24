package com.sts.sinorita.repository;

import com.sts.sinorita.entity.CcEventType;
import com.sts.sinorita.projection.balanceAdjustment.CcEventTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CcEventTypeRepository extends JpaRepository<CcEventType, Integer> {
  @Query(value = "SELECT EVENT_TYPE AS eventType, EVENT_TYPE_NAME AS eventTypeName, COMMENTS AS comments, EVENT_TYPE_CODE AS eventTypeCode FROM CC_EVENT_TYPE WHERE EVENT_TYPE_CODE = :eventTypeCode", nativeQuery = true)
  Optional<CcEventTypeProjection> getCcEventTypeByCode (@Param("eventTypeCode") String eventTypeCode);
}
