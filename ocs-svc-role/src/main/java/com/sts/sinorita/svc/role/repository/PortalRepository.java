package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.Portal;
import com.sts.sinorita.svc.role.dto.request.roles.RolePortalDto;
import com.sts.sinorita.svc.role.projection.DirMenusProjection;
import com.sts.sinorita.svc.role.projection.PortalChannelProjection;
import com.sts.sinorita.svc.role.projection.PortalProjection;
import com.sts.sinorita.svc.role.projection.RolePortalProjection;
import com.sts.sinorita.svc.role.projection.UserPortalProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortalRepository extends JpaRepository<Portal, Integer> {
    @Query(value = """
            SELECT B.PORTAL_ID          AS portalId,
                B.PORTAL_NAME           AS portalName,
                B.ICON_URL              AS iconUrl,
                B.URL                   AS url,
                B.STATE                 AS state,
                B.STATE_DATE            AS stateDate,
                B.EXTRA_URL             AS extraUrl,
                D.CONTACT_CHANNEL_ID    AS contactChannelId,
                B.ALLOW_EXTERNAL_ACCESS AS allowExternalAccess
            	FROM POT.BFM_PORTAL B LEFT JOIN POT.BFM_PORTAL_CHANNEL D ON B.PORTAL_ID = D.PORTAL_ID
            	   WHERE
                     B.STATE = 'A'
                     AND B.PORTAL_ID IN (
                         SELECT C.PORTAL_ID
                         FROM POT.BFM_PORTAL_SP C
                         WHERE (:spId IS NULL OR COALESCE(C.SP_ID, 0) = :spId)
                     )
                 ORDER BY
                     B.PORTAL_ID
            """, nativeQuery = true)
    public List<PortalChannelProjection> selectPortalList(@Param("spId") Integer spId);

    @Query(value = """
            SELECT
               B.PORTAL_ID AS keyId,
               B.PORTAL_ID AS portalId,
               B.PORTAL_NAME AS partyName,
               B.PORTAL_NAME AS portalName,
               B.URL AS url
            FROM
               BFM_ROLE_PORTAL A,
               BFM_PORTAL B
            WHERE
               A.ROLE_ID = :roleId
               AND A.STATE = 'A'
               AND B.STATE = 'A'
               AND A.PORTAL_ID = B.PORTAL_ID
            ORDER BY
               B.PORTAL_ID
            """, nativeQuery = true)
    public List<PortalProjection> selectPortalListByRoleId(@Param("roleId") Long roleId);

    @Modifying
    @Transactional
    @Query(value = """
               DELETE FROM POT.BFM_ROLE_PORTAL WHERE ROLE_ID = :roleId AND PORTAL_ID in (:list)
            """, nativeQuery = true)
    void delRolePortalByRoleId(@Param("roleId") Long roleId, @Param("list") List<Long> list);

    @Query(value = """
                SELECT 
                    B.PORTAL_ID AS portalId,
                    B.PORTAL_NAME AS portalName,
                    B.ICON_URL AS iconUrl,
                    B.URL AS url,
                    B.STATE AS state,
                    B.STATE_DATE AS stateDate,
                    B.EXTRA_URL AS extraUrl
                FROM 
                    BFM_PORTAL B
                WHERE 
                    B.STATE = 'A'
                ORDER BY 
                    B.PORTAL_ID
            """, nativeQuery = true)
    List<PortalProjection> queryPortalList();

    @Query(value = """
            SELECT
                A.USER_ID AS userId,
                A.STATE AS state,
                A.STATE_DATE AS stateDate,
                B.PORTAL_ID AS portalId,
                B.PORTAL_NAME AS portalName,
                B.ICON_URL AS iconUrl,
                B.URL AS url
            FROM BFM_USER_PORTAL A
            JOIN BFM_PORTAL B ON A.PORTAL_ID = B.PORTAL_ID
            WHERE A.USER_ID = :userId
              AND A.STATE = 'A'
              AND B.STATE = 'A'
            ORDER BY B.PORTAL_ID
            """, nativeQuery = true)
    List<UserPortalProjection> selectUserPortalListByUserId(@Param("userId") Long userId);


    @Query(value = """
                SELECT
                    A.ROLE_ID       AS roleId,
                    A.STATE         AS state,
                    A.STATE_DATE    AS stateDate,
                    B.PORTAL_ID     AS portalId,
                    B.PORTAL_NAME   AS portalName,
                    B.ICON_URL      AS iconUrl,
                    B.URL           AS url,
                                R.ROLE_NAME AS roleName
                FROM BFM_ROLE_PORTAL A
                JOIN BFM_PORTAL B ON A.PORTAL_ID = B.PORTAL_ID
                            JOIN BFM_ROLE R ON R.ROLE_ID = A.ROLE_ID
                WHERE A.ROLE_ID IN (
                    SELECT ROLE_ID FROM BFM_USER_ROLE WHERE USER_ID = :userId
                )
                AND A.STATE = 'A'
                AND B.STATE = 'A'
                ORDER BY B.PORTAL_ID
            """, nativeQuery = true)
    List<RolePortalProjection> queryRolePortalListByUser(@Param("userId") Long userId);

    @Query(value = """
            WITH TEMPVIEW AS
                (SELECT A.PORTAL_ID,
                        A.TYPE,
                        A.PARTY_ID,
                        C.PRIV_NAME PARTY_NAME,
                        C.URL,
                        A.PARENT_ID,
                        A.SEQ,
                        C.IS_HOLD
                FROM POT.BFM_PORTAL_MENU A, POT.BFM_PRIV C
                WHERE C.PRIV_ID = A.PARTY_ID
                        AND A.PORTAL_ID = :PORTAL_ID 
                        AND NVL(A.SP_ID, 0) = :SP_ID
                        AND A.STATE = 'A'
                        AND A.TYPE = 1
                UNION ALL
                SELECT A.PORTAL_ID,
                       A.TYPE,
                       A.PARTY_ID,
                       B.DIR_NAME PARTY_NAME,
                       NULL,
                       A.PARENT_ID,
                       A.SEQ,
                       NULL
                FROM POT.BFM_PORTAL_MENU A, POT.BFM_DIR B
                WHERE B.DIR_ID = A.PARTY_ID
                START WITH A.PARENT_ID IS NULL
                       AND A.PORTAL_ID = :PORTAL_ID 
                       AND NVL(A.SP_ID, 0) = :SP_ID
                       AND A.STATE = 'A'
                       AND A.TYPE = 0
                CONNECT BY PRIOR A.PARTY_ID = A.PARENT_ID
                       AND A.PORTAL_ID = :PORTAL_ID 
                       AND NVL(A.SP_ID, 0) = :SP_ID
                       AND A.STATE = 'A'
                       AND A.TYPE = 0)
        SELECT *
        FROM TEMPVIEW
        -- START WITH PARENT_ID IS NULL
        --CONNECT BY PRIOR PARTY_ID = PARENT_ID
        -- ORDER SIBLINGS BY SEQ;
        ORDER BY parent_id,seq
            """, nativeQuery = true)
    List<DirMenusProjection> queryDirMenusList(@Param ("PORTAL_ID") Long PORTAL_ID, @Param("SP_ID") Long SP_ID);

}
