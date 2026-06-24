package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmUserPortal;
import com.sts.sinorita.svc.role.projection.PortalProjection;
import com.sts.sinorita.svc.role.projection.PortletProjection;
import com.sts.sinorita.svc.role.projection.ProdComponentPrivProjection;
import com.sts.sinorita.svc.role.projection.UserPrivProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BfmUserPortalRepository extends JpaRepository<BfmUserPortal, Long> {

    @Modifying
    @Query(value = "DELETE FROM POT.BFM_USER_PORTAL WHERE USER_ID = :userId", nativeQuery = true)
    void deleteUserPortalByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE BFM_USER
            SET PORTAL_ID = :portalId,
                UPDATE_DATE = :updateDate
            WHERE USER_ID = :userId
            """, nativeQuery = true)
    void updatePortalIdByUserId(@Param("portalId") Long portalId,
                                @Param("userId") Long userId,
                                @Param("updateDate") LocalDateTime updateDate);


    @Query(value = """
            SELECT
                        C.keyId,
                        C.portalId,
                        C.portalName,
                        C.iconUrl,
                        C.partyName,
                        C.extraUrl,
                        C.url
                    FROM (
                        SELECT
                            B.portal_id     AS keyId,
                            B.portal_id     AS portalId,
                            B.portal_name   AS portalName,
                            B.icon_url      AS iconUrl,
                            B.portal_name   AS partyName,
                            B.extra_url     AS extraUrl,
                            B.url           AS url
                        FROM bfm_user_portal A
                        JOIN bfm_portal B ON A.portal_id = B.portal_id AND B.state = 'A'
                        WHERE A.user_id = :userId AND A.state = 'A'
            
                        UNION
            
                        SELECT
                            E.portal_id     AS keyId,
                            E.portal_id     AS portalId,
                            E.portal_name   AS portalName,
                            E.icon_url      AS iconUrl,
                            E.portal_name   AS partyName,
                            E.extra_url     AS extraUrl,
                            E.url           AS url
                        FROM bfm_role_portal D
                        JOIN bfm_portal E ON D.portal_id = E.portal_id AND E.state = 'A'
                        WHERE D.role_id IN (
                            SELECT role_id
                            FROM bfm_user_role
                            WHERE user_id = :userId
                              AND (:spId IS NULL OR sp_id = :spId)
                        ) AND D.state = 'A'
                    ) C
                    ORDER BY C.portalId
            
            """, nativeQuery = true)
    List<PortalProjection> selectPortalListByUserIdAndSpId(@Param("userId") Long userId,
                                                           @Param("spId") Long spId);

    @Query(value = """
                SELECT
                A.PORTLET_ID         AS portletId,
                A.PORTLET_NAME       AS portletName,
                A.TYPE_ID            AS typeId,
                A.DEFAULT_TITLE      AS defaultTitle,
                A.SHOW_HEADER        AS showHeader,
                A.SETTABLE           AS settable,
                A.COLLAPSABLE        AS collapsable,
                A.REFRESHABLE        AS refreshable,
                A.MAXABLE            AS maxable,
                A.ICON               AS icon,
                A.DEFAULT_PARAM      AS defaultParam,
                A.CLOSABLE           AS closable,
                A.STATE              AS state,
                A.STATE_DATE         AS stateDate,
                C.PRIV_ID            AS privId,
                C.URL                AS url,
                A.DEFAULT_WIDTH      AS defaultWidth,
                A.DEFAULT_HEIGHT     AS defaultHeight,
                A.VIEW_TYPE          AS viewType,
                A.IS_DRAWABLE        AS isDrawable
            FROM
                POT.BFM_PORTLETS A
            JOIN
                POT.BFM_PRIV C ON A.PORTLET_ID = C.PRIV_ID AND C.PRIV_TYPE = 'P'
            JOIN
                POT.BFM_USER_PRIV D ON C.PRIV_ID = D.PRIV_ID
            WHERE
                D.USER_ID = :userId
              AND C.STATE = 'A'
            
            """, nativeQuery = true)
    List<PortletProjection> queryUserOwnedPortletList(@Param("userId") Long userId);


    @Query(value = """
                SELECT
                A.USER_ID        AS userId,
                A.PRIV_ID        AS privId,
                B.PRIV_TYPE      AS privType,
                B.PRIV_NAME      AS privName,
                B.COMMENTS       AS comments,
                B.URL            AS url,
                D.USER_NAME      AS userName
            FROM
                POT.BFM_USER_PRIV A
            JOIN
                POT.BFM_PRIV B ON A.PRIV_ID = B.PRIV_ID
            JOIN
                POT.BFM_USER D ON A.USER_ID = D.USER_ID
            WHERE
                A.USER_ID = :userId
                AND B.STATE = 'A'
                -- Optional condition for privType
                -- (can be added dynamically if using Spring JPA Specification or SpEL)
                AND (:privType IS NULL OR B.PRIV_TYPE = :privType)
            ORDER BY
                B.PRIV_ID
            
            """, nativeQuery = true)
    public List<UserPrivProjection>selectPrivListByUserId( @Param("userId") Long userId,
                                                            @Param("privType") String privType);


    @Query(value = """

            SELECT
            A.PRIV_ID       AS privId,
            B.MENU_ID       AS menuId,
            B.OBJ_ID        AS objId,
            C.PRIV_NAME     AS menuName,
            D.PRIV_NAME     AS componentName,
            A.PRIV_LEVEL    AS privLevel
        FROM
            BFM_USER_PRIV A
        JOIN
            BFM_COMPONENT_PRIV B ON A.PRIV_ID = B.PRIV_ID
        JOIN
            BFM_PRIV C ON B.MENU_ID = C.PRIV_ID AND C.PRIV_TYPE = 'M'
        JOIN
            BFM_PRIV D ON A.PRIV_ID = D.PRIV_ID AND D.PRIV_TYPE = 'C'
        WHERE
            A.USER_ID = :userId    
        """, nativeQuery = true)
    public List<ProdComponentPrivProjection> selectUserComponentPrivList (@Param("userId") Long userId);


}
