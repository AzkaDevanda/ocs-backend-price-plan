package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcctIdentify;
import com.sts.sinorita.projection.acct.AcctIdentifyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcctIdentifyRepository extends JpaRepository<AcctIdentify, Long> {
    @Query(value = "SELECT A.ACCT_IDENTIFY_ID AS acctIdentifyId, A.IDENTIFY_TYPE AS identifyType, A.IDENTIFY_VALUE AS identifyValue, A.ACCT_ID AS acctId, A.CREATED_DATE AS createdDate, A.UPDATE_DATE AS updateDate, A.STATE AS state, A.SP_ID AS spId, A.ROUTING_ID AS routingId FROM ACCT_IDENTIFY A WHERE(:acctId IS NULL OR A.ACCT_ID = :acctId) AND (:identifyType IS NULL OR A.IDENTIFY_TYPE = :identifyType) AND (:identifyValue IS NULL OR A.IDENTIFY_VALUE = :identifyValue) AND (:state IS NULL OR A.STATE = :state) AND (:routingId IS NULL OR A.ROUTING_ID = :routingId) AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId)", nativeQuery = true)
    List<AcctIdentifyProjection> qryAcctIdentifyList(@Param("acctId") Long acctId, @Param("identifyType") String identifyType, @Param("identifyValue") String identifyValue, @Param("state") String state, @Param("routingId") Long routingId, @Param("spId") Long spId);
}
