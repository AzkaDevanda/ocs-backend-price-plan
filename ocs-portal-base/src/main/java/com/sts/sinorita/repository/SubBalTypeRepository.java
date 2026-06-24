package com.sts.sinorita.repository;

import com.sts.sinorita.dto.response.trigger.GetSubBalTypeIdPeriodIdProjection;
import com.sts.sinorita.entity.SubBalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubBalTypeRepository extends JpaRepository<SubBalType, Integer> {

    @Query(value = "SELECT sbt.SUB_BAL_TYPE_ID as subBalTypeId, p.PERIOD_ID as periodId FROM ACM_BENEFIT ab \n" +
            "RIGHT JOIN SUB_BAL_TYPE sbt  ON ab.SUB_BAL_TYPE_ID = sbt.SUB_BAL_TYPE_ID \n" +
            "JOIN PERIOD p ON sbt.PERIOD_ID = p.PERIOD_ID WHERE sbt.SUB_BAL_TYPE_ID = :subBalTypeId", nativeQuery = true)
    Optional<GetSubBalTypeIdPeriodIdProjection> findSubBalTypeIdAndPeriodId(@Param("subBalTypeId") Integer subBalTypeId);

    @Modifying
    @Query(value = "DELETE FROM SubBalType sbt WHERE sbt.id = :subBalTypeId")
    void deleteBySubBalType(@Param("subBalTypeId") Integer subBalTypeId);

    @Query(value = "SELECT 1 FROM ACM_BENEFIT WHERE SUB_BAL_TYPE_ID = :id FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<Integer> isReferencedInAcmBenefit(@Param("id") Integer id);

    @Query(value = "SELECT 1 FROM BAL_BENEFIT WHERE SUB_BAL_TYPE_ID = :id FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<Integer> isReferencedInBalBenefit(@Param("id") Integer id);

    @Query(value = "SELECT 1 FROM EVENT_BENEFIT WHERE SUB_BAL_TYPE_ID = :id FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<Integer> isReferencedInEventBenefit(@Param("id") Integer id);

    @Query(value = "SELECT 1 FROM SUB_BAL_TYPE_LIMIT WHERE SUB_BAL_TYPE_ID = :id FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<Integer> isReferencedInSubBalTypeLimit(@Param("id") Integer id);

    @Query(value = "SELECT 1 FROM SUB_BAL_TYPE s WHERE s.ACCT_RES_ID = :acctResId FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<Integer> isReferencedInAcctRes(@Param("acctResId") Long acctResId);

}
