package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmRole;
import com.ocs.portal.svc.role.projection.RoleProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<BfmRole, Integer> {

    @Query(value = "select b from BfmRole b order by b.roleId ")
    public List<BfmRole> selectAll();

    @Query(value = """
              SELECT
               ROLE_ID       AS roleId,
               ROLE_NAME     AS roleName,
               COMMENTS      AS comments,
               ROLE_CODE     AS roleCode,
               IS_LOCKED     AS isLocked
               FROM BFM_ROLE WHERE
            ROLE_ID = :roleId
            """, nativeQuery = true)
    public RoleProjection selectByRoleId(@Param("roleId") Integer roleId);


    @Query(value = """ 
            SELECT COUNT(1) COUNT
                    FROM BFM_ROLE A
                    WHERE UPPER(A.ROLE_CODE) = UPPER(:roleCode)
                    AND (:roleId IS NULL OR A.ROLE_ID = :roleId)
            """, nativeQuery = true)
    public Integer isSameCode(@Param("roleCode") String roleCode, @Param("roleId") Integer roleId);

    @Query(value = """ 
            SELECT COUNT(1) COUNT
                    FROM BFM_ROLE A
                    WHERE UPPER(A.ROLE_NAME) = UPPER(:roleName)
                    AND (:roleId IS NULL OR A.ROLE_ID = :roleId)
            """, nativeQuery = true)
    public Integer isSameName(@Param("roleName") String roleName, @Param("roleId") Integer roleId);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE POT.BFM_ROLE SET ROLE_NAME = :roleName, COMMENTS
            		= :comments, APP_ID = :appId,
            		ROLE_CODE = :roleCode,UPDATE_DATE=:updateDate 
                        		WHERE ROLE_ID = :roleId
            """, nativeQuery = true)
    void updateRole(@Param("roleName") String roleName, @Param("comments") String comments,
                    @Param("appId") Long appId,@Param("roleCode")String roleCode, @Param("roleId") Integer roleId,
                    @Param("updateDate") LocalDateTime updateDate);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM BFM_ROLE WHERE ROLE_ID = :roleId", nativeQuery = true)
    void deleteRole(@Param("roleId") Long roleId);

}
