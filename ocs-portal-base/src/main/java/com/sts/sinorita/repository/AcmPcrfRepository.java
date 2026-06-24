package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AcmPcrf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AcmPcrfRepository extends JpaRepository<AcmPcrf,Integer> {

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
                        FROM ACM_PCRF
                        WHERE ACM_THRESHOLD_ID = :thresholdId
            """, nativeQuery = true)
    boolean isNotEmpty(@Param("thresholdId") Long acmThresholdId);

    @Modifying
    @Query(value = """
            DELETE FROM ACM_PCRF WHERE ACM_THRESHOLD_ID = :thresholdId
            """, nativeQuery = true)
    void delAcmPcrf(@Param("thresholdId") Long acmThresholdId);
}
