package com.ocs.portal.repository;

import com.ocs.portal.entity.Up;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UpRepository extends JpaRepository<Up, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM UpRule r WHERE r.upId = :upId")
    void deleteUpRuleByUpId(@Param("upId") Integer upId);

    @Transactional
    @Modifying
    @Query("DELETE FROM RankUp r WHERE r.upId = :upId")
    void deleteRankUpByUpId(Integer upId);

    @Transactional
    @Modifying
    @Query("DELETE FROM AcmUp r WHERE r.upId = :upId")
    void deleteAcmUpByUpId(Integer upId);

    @Transactional
    @Modifying
    @Query("DELETE FROM AcmCalc r WHERE r.upId = :upId")
    void deleteAcmCalcByUpId(Integer upId);

    @Transactional
    @Modifying
    @Query("DELETE FROM TimeSpanUp r WHERE r.upId = :upId")
    void deleteTimeSpanUpByUpId(Integer upId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Up r WHERE r.id = :upId")
    void deleteUpByUpId(Integer upId);

}
