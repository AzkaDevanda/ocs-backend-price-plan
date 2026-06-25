package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmPortlet;
import com.ocs.portal.svc.role.projection.PortletProjection;
import com.ocs.portal.svc.role.projection.PortletTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BfmPortletsRepository  extends JpaRepository<BfmPortlet,Long> {

    @Query(value = """
                SELECT 
                    TYPE_ID AS typeId,
                    TYPE_NAME AS typeName,
                    COMMENTS AS comments,
                    STATE AS state,
                    STATE_DATE AS stateDate
                FROM BFM_PORTLET_TYPE
                ORDER BY TYPE_ID
            """, nativeQuery = true)
    List<PortletTypeProjection> queryPortletTypeList();


    @Query(
            value = """
            SELECT 
                A.PORTLET_ID AS portletId,
                A.PORTLET_NAME AS portletName,
                A.TYPE_ID AS typeId,
                A.DEFAULT_TITLE AS defaultTitle,
                A.SHOW_HEADER AS showHeader,
                A.SETTABLE AS settable,
                A.COLLAPSABLE AS collapsable,
                A.REFRESHABLE AS refreshable,
                A.MAXABLE AS maxable,
                A.CLOSABLE AS closable,
                A.ICON AS icon,
                A.DEFAULT_PARAM AS defaultParam,
                A.STATE AS state,
                A.STATE_DATE AS stateDate,
                C.PRIV_ID AS privId,
                C.URL AS url
            FROM POT.BFM_PORTLETS A, BFM_PRIV C
            LEFT JOIN BFM_ROLE_PRIV D ON 
                C.PRIV_ID = D.PRIV_ID 
                AND D.ROLE_ID = :roleId
            WHERE A.PORTLET_ID = C.PRIV_ID
              AND C.PRIV_TYPE = 'P'
              AND A.TYPE_ID = :typeId
              AND C.STATE = 'A'
        """,
            nativeQuery = true
    )
    List<PortletProjection> findPortletsByRoleIdAndTypeId(
            @Param("roleId") Long roleId,
            @Param("typeId") Long typeId
    );

    @Query(value = """
        SELECT 
            A.PORTLET_ID        AS portletId,
            B.PORTAL_ID         AS portalId,
            A.PORTLET_NAME      AS portletName,
            A.TYPE_ID           AS typeId,
            A.DEFAULT_TITLE     AS defaultTitle,
            A.SHOW_HEADER       AS showHeader,
            A.SETTABLE          AS settable,
            A.COLLAPSABLE       AS collapsable,
            A.REFRESHABLE       AS refreshable,
            A.MAXABLE           AS maxable,
            A.CLOSABLE          AS closable,
            A.STATE             AS state,
            A.STATE_DATE        AS stateDate,
            A.ICON              AS icon,
            A.DEFAULT_PARAM     AS defaultParam,
            C.PRIV_ID           AS privId,
            C.URL               AS url
        FROM 
            POT.BFM_PORTLETS A
        JOIN 
            BFM_PORTLET_SCOPE B ON A.PORTLET_ID = B.PORTLET_ID
        JOIN 
            BFM_PRIV C ON A.PORTLET_ID = C.PRIV_ID
        LEFT JOIN 
            BFM_ROLE_PRIV D ON C.PRIV_ID = D.PRIV_ID AND D.ROLE_ID = :roleId
        WHERE 
            C.PRIV_TYPE = 'P'
            AND B.PORTAL_ID = :portalId
            AND C.STATE = 'A'
        """, nativeQuery = true)
    List<PortletProjection> findPortletsByRoleAndPortal(@Param("roleId") Long roleId, @Param("portalId") Long portalId);


}
