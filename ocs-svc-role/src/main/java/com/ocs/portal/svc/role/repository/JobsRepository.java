package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.Job;
import com.ocs.portal.svc.role.projection.JobProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends JpaRepository<Job, Long> {

    @Query(value = """
            SELECT 
                A.JOB_ID AS jobId,
                A.JOB_NAME AS jobName,
                A.STATE AS state,
                A.STATE_DATE AS stateDate,
                A.SP_ID AS spId,
                A.JOB_CODE AS jobCode,
                A.PAR_JOB_ID AS parJobId
            FROM BFM_JOB A
            WHERE NOT EXISTS (
                SELECT 1 
                FROM BFM_JOB_ROLE B 
                WHERE A.JOB_ID = B.JOB_ID 
                  AND B.ROLE_ID = :roleId 
                  AND B.STATE = 'A'
            )
            AND A.STATE <> 'D'
            AND (:spId IS NULL OR COALESCE(A.SP_ID, 0) = :spId)
            AND (:jobName IS NULL OR UPPER(A.JOB_NAME) LIKE CONCAT('%', UPPER(TRIM(:jobName)), '%'))
            AND (:jobCode IS NULL OR UPPER(A.JOB_CODE) LIKE CONCAT('%', UPPER(TRIM(:jobCode)), '%'))
            """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM BFM_JOB A
                    WHERE NOT EXISTS (
                        SELECT 1 
                        FROM BFM_JOB_ROLE B 
                        WHERE A.JOB_ID = B.JOB_ID 
                          AND B.ROLE_ID = :roleId 
                          AND B.STATE = 'A'
                    )
                    AND A.STATE <> 'D'
                    AND (:spId IS NULL OR COALESCE(A.SP_ID, 0) = :spId)
                    AND (:jobName IS NULL OR UPPER(A.JOB_NAME) LIKE CONCAT('%', UPPER(TRIM(:jobName)), '%'))
                    AND (:jobCode IS NULL OR UPPER(A.JOB_CODE) LIKE CONCAT('%', UPPER(TRIM(:jobCode)), '%'))
                    """,
            nativeQuery = true)
    Page<JobProjection> queryUnGrantJobByRoleId(
            @Param("roleId") Long roleId,
            @Param("spId") Long spId,
            @Param("jobName") String jobName,
            @Param("jobCode") String jobCode,
            Pageable pageable
    );

    @Query(value = """
            SELECT 
                A.JOB_ID AS jobId,
                A.JOB_NAME AS jobName,
                A.STATE AS state,
                A.STATE_DATE AS stateDate,
                A.SP_ID AS spId,
                A.JOB_CODE AS jobCode,
                A.PAR_JOB_ID AS parJobId
            FROM BFM_JOB A
            LEFT JOIN BFM_JOB_ROLE B ON A.JOB_ID = B.JOB_ID
            WHERE B.ROLE_ID = :roleId
              AND B.STATE = 'A'
              AND A.STATE <> 'D'
              AND (:spId IS NULL OR COALESCE(A.SP_ID, 0) = :spId)
              AND (:jobName IS NULL OR UPPER(TRIM(A.JOB_NAME)) LIKE CONCAT('%', UPPER(:jobName), '%'))
              AND (:jobCode IS NULL OR UPPER(TRIM(A.JOB_CODE)) LIKE CONCAT('%', UPPER(:jobCode), '%'))
            """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM BFM_JOB A
                    LEFT JOIN BFM_JOB_ROLE B ON A.JOB_ID = B.JOB_ID
                    WHERE B.ROLE_ID = :roleId
                      AND B.STATE = 'A'
                      AND A.STATE <> 'D'
                      AND (:spId IS NULL OR COALESCE(A.SP_ID, 0) = :spId)
                      AND (:jobName IS NULL OR UPPER(TRIM(A.JOB_NAME)) LIKE CONCAT('%', UPPER(:jobName), '%'))
                      AND (:jobCode IS NULL OR UPPER(TRIM(A.JOB_CODE)) LIKE CONCAT('%', UPPER(:jobCode), '%'))
                    """,
            nativeQuery = true)
    Page<JobProjection> queryGrantJobByRoleId(
            @Param("roleId") Long roleId,
            @Param("spId") Long spId,
            @Param("jobName") String jobName,
            @Param("jobCode") String jobCode,
            Pageable pageable
    );


    @Query(value = """
        SELECT JOB_ID
        FROM BFM_JOB_ROLE
        WHERE ROLE_ID = :roleId
          AND STATE = 'A'
          AND (:spId IS NULL OR COALESCE(SP_ID, 0) = :spId)
        """, nativeQuery = true)
    List<Long> queryJobIdsByRoleId(@Param("roleId") Long roleId, @Param("spId") Long spId);

}
