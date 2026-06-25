package com.ocs.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.EventTable;

@Repository
public interface EventTableRepository extends JpaRepository<EventTable, Integer> {

  @Query(value = "SELECT A.TABLE_NAME FROM EVENT_TABLE A JOIN TABLE_LIST_CYCLE B ON A.EVENT_TABLE_ID = B.EVENT_TABLE_ID WHERE A.BASE_TABLE_NAME = :baseTableName AND B.BILLING_CYCLE_ID = :billingCycleId", nativeQuery = true)
  List<String> getSubTableName(@Param("billingCycleId") Integer billingCycleId,
      @Param("baseTableName") String baseTableName);

}
