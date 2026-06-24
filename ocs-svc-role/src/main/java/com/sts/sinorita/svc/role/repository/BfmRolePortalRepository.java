package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmRolePortal;
import com.sts.sinorita.svc.role.projection.RolePortalProjection;
import com.sts.sinorita.svc.role.repository.custom.RolePortalCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BfmRolePortalRepository extends JpaRepository<BfmRolePortal, Integer>, RolePortalCustomRepository {

    @Query(value = """
            SELECT 
                ROLE_ID    AS roleId, 
                PORTAL_ID  AS portalId, 
                STATE      AS state, 
                STATE_DATE AS stateDate, 
                IS_DEFAULT AS isDefault, 
                SP_ID      AS spId, 
                CREATED_DATE AS createdDate, 
                UPDATE_DATE AS updateDate
            FROM POT.BFM_ROLE_PORTAL
            WHERE ROLE_ID = :roleId
            AND PORTAL_ID IN (:portalIds)
            """, nativeQuery = true)
    List<RolePortalProjection> selectRolePortalsByRoleIdAndPortalList(
            @Param("roleId") Long roleId,
            @Param("portalIds") List<Long> portalIds
    );



}
