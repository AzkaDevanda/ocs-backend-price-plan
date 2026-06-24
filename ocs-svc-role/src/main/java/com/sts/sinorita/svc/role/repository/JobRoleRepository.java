package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.JobRole;
import com.sts.sinorita.svc.role.projection.JobRoleProjection;
import com.sts.sinorita.svc.role.projection.StaffJobProjection;
import com.sts.sinorita.svc.role.projection.UserRoleProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRoleRepository extends JpaRepository<JobRole, Long> {

    @Query(value = """
    SELECT DISTINCT
        A.STAFF_JOB_ID     AS staffJobId,
        B.STAFF_ORG_ID     AS staffOrgId,
        C.STAFF_ID         AS staffId,
        C.STAFF_NAME       AS staffName,
        D.ORG_NAME         AS orgName,
        D.ORG_ID           AS orgId,
        E.AREA_NAME        AS areaName,
        E.AREA_CODE        AS areaCode,
        E.AREA_ID          AS areaId,
        F.USER_NAME        AS userName,
        F.USER_CODE        AS userCode,
        F.USER_ID          AS userId,
        C.STAFF_CODE       AS staffCode
    FROM 
        BFM_STAFF_JOB A
    JOIN 
        BFM_STAFF_ORG B ON A.STAFF_ORG_ID = B.STAFF_ORG_ID
    JOIN 
        BFM_STAFF C ON B.STAFF_ID = C.STAFF_ID
    JOIN 
        BFM_ORG D ON B.ORG_ID = D.ORG_ID
    JOIN 
        BFM_AREA E ON D.AREA_ID = E.AREA_ID
    JOIN 
        POT.BFM_USER F ON C.USER_ID = F.USER_ID
    WHERE 
        A.JOB_ID = :jobId
        AND (:spId IS NULL 
            OR (COALESCE(A.SP_ID, 0) = :spId 
             AND COALESCE(B.SP_ID, 0) = :spId 
             AND COALESCE(D.SP_ID, 0) = :spId 
             AND COALESCE(E.SP_ID, 0) = :spId))
        AND A.STATE = 'A'
        AND B.STATE = 'A'
        AND F.STATE = 'A'
    ORDER BY 
        D.ORG_ID, E.AREA_ID, F.USER_ID
""", nativeQuery = true)
    List<StaffJobProjection> queryOrgAreaStaffUserInfoByJobId(@Param("jobId") Long jobId, @Param("spId") Long spId);


    @Query(value = """
        SELECT 
            USER_ID       AS userId,
            ROLE_ID       AS roleId,
            SP_ID         AS spId,
            ID            AS id,
            USER_ROLE_TIMES AS userRoleTimes,
            STAFF_ROLE_TIMES AS staffRoleTimes,
            CREATED_DATE  AS createdDate,
            UPDATE_DATE   AS updateDate
        FROM POT.BFM_USER_ROLE
        WHERE ROLE_ID = :roleId
        AND USER_ID IN (:userIds)
        AND (:spId IS NULL  OR (COALESCE(SP_ID, 0) = :spId ))
        """, nativeQuery = true)
    List<UserRoleProjection> findByRoleIdAndUserIds(
            @Param("roleId") Long roleId,
            @Param("userIds") List<Long> userIds,
            @Param("spId")Long spId);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM BFM_JOB_ROLE
        WHERE ROLE_ID = :roleId
        AND JOB_ID IN (:jobIds)
        """, nativeQuery = true)
    void deleteByRoleIdAndJobIds(
            @Param("roleId") Long roleId,
            @Param("jobIds") List<Long> jobIds
    );

    @Query(value = """
        SELECT 
            JOB_ROLE_ID    AS jobRoleId,
            JOB_ID         AS jobId,
            ROLE_ID        AS roleId,
            STATE          AS state,
            STATE_DATE     AS stateDate,
            SP_ID          AS spId,
            CREATED_DATE   AS createdDate,
            UPDATE_DATE    AS updateDate
        FROM BFM_JOB_ROLE
        WHERE JOB_ID IN (:jobList)
        AND ROLE_ID IN (:roleList)
        """, nativeQuery = true)
    List<JobRoleProjection> selectJobRolesByJobIdAndRoleList(
            @Param("jobList") List<Long> jobList,
            @Param("roleList") List<Long> roleList
    );



    @Query(value = """
    SELECT DISTINCT
        A.STAFF_JOB_ID     AS staffJobId,
        B.STAFF_ORG_ID     AS staffOrgId,
        C.STAFF_ID         AS staffId,
        C.STAFF_NAME       AS staffName,
        D.ORG_NAME         AS orgName,
        D.ORG_ID           AS orgId,
        E.AREA_NAME        AS areaName,
        E.AREA_CODE        AS areaCode,
        E.AREA_ID          AS areaId,
        F.USER_NAME        AS userName,
        F.USER_CODE        AS userCode,
        F.USER_ID          AS userId,
        C.STAFF_CODE       AS staffCode
    FROM 
        BFM_STAFF_JOB A
    JOIN 
        BFM_STAFF_ORG B ON A.STAFF_ORG_ID = B.STAFF_ORG_ID
    JOIN 
        BFM_STAFF C ON B.STAFF_ID = C.STAFF_ID
    JOIN 
        BFM_ORG D ON B.ORG_ID = D.ORG_ID
    JOIN 
        BFM_AREA E ON D.AREA_ID = E.AREA_ID
    JOIN 
        POT.BFM_USER F ON C.USER_ID = F.USER_ID
    WHERE 
        F.USER_NAME = :userName
        AND (:spId IS NULL 
            OR (COALESCE(A.SP_ID, 0) = :spId 
             AND COALESCE(B.SP_ID, 0) = :spId 
             AND COALESCE(D.SP_ID, 0) = :spId 
             AND COALESCE(E.SP_ID, 0) = :spId))
        AND A.STATE = 'A'
        AND B.STATE = 'A'
        AND F.STATE = 'A'
    ORDER BY 
        D.ORG_ID, E.AREA_ID, F.USER_ID
""", nativeQuery = true)
    List<StaffJobProjection> getJobsByUser(@Param("userName") String userName, @Param("spId") Long spId);


}
