package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmRolePriv;
import com.ocs.portal.svc.role.projection.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface RolePrivsRepository extends JpaRepository<BfmRolePriv, Long> {

    @Query(value = """
                    SELECT
                    ROLE_ID         AS roleId,
                    PRIV_ID         AS privId,
                    SP_ID           AS spId,
                    PRIV_LEVEL      AS privLevel,
                    IS_AUTHORIZED   AS isAuthorized,
                    AUTO_OPEN_MENU  AS autoOpenMenu,
                    CREATED_DATE    AS createdDate,
                    UPDATE_DATE     AS updateDate
                FROM
                    POT.BFM_ROLE_PRIV
                WHERE
                ROLE_ID = :roleId
               AND ( :empty = 1 OR PRIV_ID IN (:privIds))
            """, nativeQuery = true)
    public List<RolePrivProjection> selectRolePrivsByRoleIdAndPrivList(@Param("roleId") Long roleId, @Param("privIds") List<Long> privIds, @Param("empty")Integer empty);
//                    AND (:privIds IS NULL OR PRIV_ID IN (:privIds))

    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM POT.BFM_ROLE_PRIV WHERE ROLE_ID = :roleId
            AND (:empty = 1 OR PRIV_ID in (:list)) 
            """, nativeQuery = true)
    public void delRolePrivs(@Param("roleId") Long roleId, List<Long> list, @Param("empty")Integer empty);


    @Query(value = """
                SELECT
                A.PRIV_ID         AS privId,
                A.PRIV_TYPE       AS privType,
                A.PRIV_NAME       AS privName,
                A.COMMENTS        AS comments,
                B.PRIV_LEVEL      AS privLevel,
                A.URL             AS url,
                A.STATE           AS state,
                A.STATE_DATE      AS stateDate,
                A.PRIV_CODE       AS privCode,
                A.PRIV_EL         AS privEl,
                B.AUTO_OPEN_MENU  AS autoOpenMenu,
                B.STS_ADD AS addStatus,
                B.STS_DELETE AS deleteStatus,
                B.STS_READ AS readStatus,
                B.STS_EDIT AS editStatus
            FROM
                POT.BFM_PRIV A,
                POT.BFM_ROLE_PRIV B
            WHERE
                A.PRIV_ID = B.PRIV_ID
                AND B.ROLE_ID = :roleId
                AND A.STATE = 'A'
              AND (:privType IS NULL OR A.PRIV_TYPE = :privType)
            ORDER BY
                A.PRIV_ID
            """, nativeQuery = true)
    public List<RolePrivMenuProjection> selectMenuPrivListByRoleId(@Param("roleId") Long roleId, @Param("privType") Character privType);

    @Query(value = """
            SELECT
                        A.PRIV_ID         AS privId,
                        A.PRIV_TYPE       AS privType,
                        A.PRIV_NAME       AS privName,
                        A.COMMENTS        AS comments,
                        B.PRIV_LEVEL      AS privLevel,
                        A.URL             AS url,
                        A.STATE           AS state,
                        A.STATE_DATE      AS stateDate,
                        A.PRIV_CODE       AS privCode,
                        A.PRIV_EL         AS privEl,
                        B.AUTO_OPEN_MENU  AS autoOpenMenu,
                        B.STS_ADD AS addStatus,
                        B.STS_DELETE AS deleteStatus,
                        B.STS_READ AS readStatus,
                        B.STS_EDIT AS editStatus,
                        M.ICON_URL AS iconUrl
                        FROM
                        POT.BFM_PRIV A JOIN
                        POT.BFM_ROLE_PRIV B ON A.PRIV_ID = B.PRIV_ID JOIN
                        POT.BFM_MENU M ON M.MENU_ID = A.PRIV_ID
                        WHERE 1=1
                        AND B.ROLE_ID IN (:roleId)
                        AND A.STATE = 'A'
                        AND (:privType IS NULL OR A.PRIV_TYPE = :privType)
                        ORDER BY
                        A.PRIV_ID
            """,nativeQuery = true)
    public List<RolePrivMenuProjection> selectMenuByRoleId(@Param("roleId")List<Long>roleId, @Param("privType") Character privType);



    @Transactional
    @Modifying
    @Query(value = "UPDATE POT.BFM_ROLE_PRIV SET AUTO_OPEN_MENU = :autoOpenMenu WHERE ROLE_ID = :roleId AND PRIV_ID = :privId", nativeQuery = true)
    void modifyRoleAutoOpen(@Param("autoOpenMenu") String autoOpenMenu,
                            @Param("roleId") Long roleId,
                            @Param("privId") Long privId);

    @Query(value = "select b from BfmRolePriv b where b.id.roleId = :roleId and b.id.privId = :privId ")
    Optional<BfmRolePriv> selectRolePriv(@Param("roleId") Long roleId, @Param("privId") Long privId);

    @Query(value = """
                SELECT 
                    PRIV_ID AS privId,
                    ROLE_ID AS roleId,
                    PRIV_LEVEL AS privLevel
                FROM POT.BFM_ROLE_PRIV
                WHERE ROLE_ID IN (:roleIds)
            """, nativeQuery = true)
    List<ProdRolePrivProjection> queryRolePrivByRoleIds(@Param("roleIds") List<Long> roleIds);

    @Query(value = """
                SELECT ROLE_ID 
                FROM POT.BFM_USER_ROLE
                WHERE USER_ID = :userId
                  AND USER_ROLE_TIMES IS NULL
                  AND (:spId IS NULL OR COALESCE(SP_ID, 0) = :spId)
            """, nativeQuery = true)
    List<Long> queryRoleIdsByUserId(
            @Param("userId") Long userId,
            @Param("spId") Long spId
    );

    @Query(value = """
                SELECT ROLE_ID
                FROM POT.BFM_JOB_ROLE
                WHERE JOB_ID = :jobId
                  AND STATE = 'A'
                  AND (:spId IS NULL OR COALESCE(SP_ID, 0) = :spId)
            """, nativeQuery = true)
    ArrayList<Long> queryRoleIdsByJobId(
            @Param("jobId") Long jobId,
            @Param("spId") Long spId
    );


    @Query(value = """
            SELECT
                A.USER_ID as userId,
                A.PRIV_ID as privId,
                A.PRIV_LEVEL as privLevel,
                B.PRIV_TYPE as privType,
                B.PRIV_NAME as privName,
                B.COMMENTS as comments,
                B.URL as url,
                D.USER_NAME as userName
            FROM
                POT.BFM_USER_PRIV A
            JOIN POT.BFM_PRIV B ON A.PRIV_ID = B.PRIV_ID
            JOIN POT.BFM_USER D ON A.USER_ID = D.USER_ID
            WHERE
                A.USER_ID = :userId
                AND (:privType IS NULL OR B.PRIV_TYPE = :privType)
                AND B.STATE = 'A'
            ORDER BY
                B.PRIV_ID
            """, nativeQuery = true)
    List<ProdUserPrivProjection> queryPrivListByUserId(@Param("userId") Long userId, @Param("privType") String privType);


    @Query(value = """
                SELECT
                    A.PRIV_ID AS privId,
                    A.MENU_ID AS menuId,
                    A.OBJ_ID AS objId,
                    A.EFFECT_TYPE AS effectType,
                    B.PRIV_NAME AS privName,
                    B.COMMENTS AS comments,
                    B.URL AS url,
                    B.PRIV_EL AS privEl
                FROM
                    POT.BFM_COMPONENT_PRIV A
                JOIN
                    POT.BFM_PRIV B ON A.PRIV_ID = B.PRIV_ID
                WHERE
                    (:menuId IS NULL OR A.MENU_ID = :menuId)
                    AND (:privCode IS NULL OR B.PRIV_CODE = :privCode)
                    AND B.STATE = 'A'
                ORDER BY
                    A.PRIV_ID
            """, nativeQuery = true)
    List<ProdComponentPrivProjection> selectComponentListByCondition(
            @Param("menuId") Long menuId,
            @Param("privCode") String privCode);


    @Query(value = """
            SELECT A.PRIV_ID AS privId,
                   B.MENU_ID AS menuId,
                   B.OBJ_ID AS objId,
                   C.PRIV_NAME AS menuName,
                   D.PRIV_NAME AS privName,
                   CASE
                       WHEN A.PRIV_LEVEL IS NOT NULL THEN A.PRIV_LEVEL
                       ELSE '0'
                   END AS privLevel
            FROM BFM_ROLE_PRIV A
            JOIN BFM_COMPONENT_PRIV B ON A.PRIV_ID = B.PRIV_ID
            JOIN BFM_PRIV C ON B.MENU_ID = C.PRIV_ID AND C.PRIV_TYPE = 'M'
            JOIN BFM_PRIV D ON A.PRIV_ID = D.PRIV_ID AND D.PRIV_TYPE = 'C'
            WHERE A.ROLE_ID = :roleId
            
            UNION
            
            SELECT A.PRIV_ID AS privId,
                   B.MENU_ID AS menuId,
                   B.OBJ_ID AS objId,
                   NULL AS menuName,
                   D.PRIV_NAME AS privName,
                   CASE
                       WHEN A.PRIV_LEVEL IS NOT NULL THEN A.PRIV_LEVEL
                       ELSE '0'
                   END AS privLevel
            FROM BFM_ROLE_PRIV A
            JOIN BFM_COMPONENT_PRIV B ON A.PRIV_ID = B.PRIV_ID
            JOIN BFM_PRIV D ON A.PRIV_ID = D.PRIV_ID AND D.PRIV_TYPE = 'C'
            WHERE B.MENU_ID IS NULL
              AND A.ROLE_ID = :roleId
            """, nativeQuery = true)
    List<ProdComponentPrivProjection> selectRoleComponentPrivList(@Param("roleId") Long roleId);


    @Query(value = """
            SELECT B.PRIV_NAME AS privName,
                   A.PRIV_ID AS privId,
                   A.MENU_ID AS menuId,
                   A.OBJ_ID AS objId,
                   B.COMMENTS AS comments
            FROM BFM_COMPONENT_PRIV A,
                 BFM_PRIV B
            LEFT JOIN BFM_ROLE_PRIV D ON D.PRIV_ID = B.PRIV_ID AND D.ROLE_ID = :roleId
            WHERE A.PRIV_ID = B.PRIV_ID
              AND B.PRIV_TYPE = 'C'
              AND B.STATE = 'A'
              AND (
                  (:menuId IS NOT NULL AND A.MENU_ID = :menuId)
                  OR (:menuId IS NULL AND A.MENU_ID IS NULL)
              )
            """, nativeQuery = true)
    List<ProdComponentPrivProjection> selectCompListByMenuIdRoleId(
            @Param("menuId") Long menuId,
            @Param("roleId") Long roleId
    );

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE BFM_ROLE_PRIV 
                SET PRIV_LEVEL = CASE
                    WHEN :privLevel IS NULL THEN NULL
                    ELSE :privLevel
                END
            WHERE ROLE_ID = :roleId AND PRIV_ID = :privId
            """, nativeQuery = true)
    int modifyRolePrivLevel(@Param("roleId") Long roleId,
                            @Param("privId") Long privId,
                            @Param("privLevel") String privLevel);

    @Query(value = """
            SELECT 
                A.PRIV_ID AS privId, 
                A.MENU_ID AS menuId, 
                A.OBJ_ID AS objId, 
                A.EFFECT_TYPE AS effectType
            FROM BFM_COMPONENT_PRIV A
            WHERE A.PRIV_ID = :privId
            """, nativeQuery = true)
    Optional<ProdComponentPrivProjection> queryComponentPrivByPrivId(@Param("privId") Long privId);

}
