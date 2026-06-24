package com.sts.sinorita.repository;

import com.sts.sinorita.entity.CcEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface CcEventRepository extends JpaRepository<CcEvent, Integer> {
  @Transactional
  @Modifying
  @Query(value = "INSERT INTO CC_EVENT ( EVENT_ID, EVENT_FORMAT_ID, SUBS_ID, PREFIX, ACC_NBR, CREATED_DATE, STATE, STATE_DATE, EVENT_PARAM, COMMENTS, SP_ID, EVENT_PARAM1 ) VALUES ( :eventId, :eventFormatId, :subsId, :prefix, :accNbr, :createdDate, :state, :stateDate, :eventParam, :comments, :spId, :eventParam1 )", nativeQuery = true)
  void insertCcEvent (@Param("eventId") Long eventId, @Param("eventFormatId") Long eventFormatId, @Param("subsId") Long subsId, @Param("prefix") String prefix, @Param("accNbr") String accNbr, @Param("createdDate") LocalDateTime createdDate, @Param("state") String state, @Param("stateDate") LocalDateTime stateDate, @Param("eventParam") String eventParam, @Param("comments") String comments, @Param("spId") Long spId, @Param("eventParam1") String eventParam1);
}
