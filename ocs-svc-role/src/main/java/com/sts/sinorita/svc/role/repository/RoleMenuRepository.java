package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmPortalMenu;
import com.sts.sinorita.svc.role.projection.PortalMenuDirProjection;
import com.sts.sinorita.svc.role.projection.PortalPrivProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleMenuRepository extends JpaRepository<BfmPortalMenu, Integer> {

    @Query(value = """
            SELECT 
                A.PORTAL_ID AS portalId,
                A.SEQ AS seq,
                A.PARTY_ID AS partyId,
                B.DIR_NAME AS partyName,
                A.TYPE AS type
            FROM BFM_PORTAL_MENU A
            JOIN BFM_DIR B ON A.PARTY_ID = B.DIR_ID
            WHERE A.TYPE = '0'
            AND (:portalId IS NULL OR A.PORTAL_ID = :portalId)
            AND (:partyId IS NULL OR A.PARENT_ID = :partyId)
            AND A.STATE = 'A'
            AND B.STATE = 'A'
            ORDER BY A.SEQ
            """, nativeQuery = true)
    List<PortalMenuDirProjection> findByPortalIdAndParentId(@Param("portalId") Long portalId, @Param("partyId") Long partyId);


    // Dengan roleId
    @Query(value = """
            SELECT 
                A.PORTAL_ID AS portalId,
                A.SEQ AS seq,
                A.PARTY_ID AS partyId,
                C.PRIV_ID AS privId,
                C.PRIV_NAME AS privName,
                A.TYPE AS type,
                C.URL AS url,
                C.COMMENTS AS comments
            FROM BFM_PORTAL_MENU A
            JOIN BFM_PRIV C ON A.PARTY_ID = C.PRIV_ID
           -- LEFT JOIN BFM_ROLE_PRIV D ON C.PRIV_ID = D.PRIV_ID
            WHERE A.TYPE = '1'
            AND C.PRIV_TYPE = 'M'
            AND A.PORTAL_ID = :portalId
            --AND (:roleId IS NULL OR D.ROLE_ID = :roleId)
            AND (:partyId IS NULL OR :partyId = 0 AND A.PARENT_ID IS NULL OR A.PARENT_ID = :partyId)
            AND A.STATE = 'A'
            AND C.STATE = 'A'
            """, nativeQuery = true)
    List<PortalPrivProjection> selectMenuListByPartyIdRoleId(
            @Param("portalId") Long portalId,
            @Param("partyId") Long partyId
    );

}
