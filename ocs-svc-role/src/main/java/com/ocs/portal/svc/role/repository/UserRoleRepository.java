package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmUserRole;
import com.ocs.portal.svc.role.projection.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<BfmUserRole, Integer> {

    @Query(value = """
                SELECT 
                    USER_ID          AS userId,
                    ROLE_ID          AS roleId,
                    SP_ID            AS spId,
                    ID               AS id,
                    USER_ROLE_TIMES  AS userRoleTimes,
                    STAFF_ROLE_TIMES AS staffRoleTimes,
                    CREATED_DATE     AS createdDate,
                    UPDATE_DATE      AS updateDate
                FROM BFM_USER_ROLE
                WHERE USER_ID = :userId AND ROLE_ID = :roleId
                AND (:spId IS NULL OR NVL(SP_ID, 0) = :spId)
            """, nativeQuery = true)
    Optional<UserRoleProjection> selectUserRoleByUserIdAndRoleId(@Param("userId") Long userId,
                                                                 @Param("roleId") Long roleId, @Param("spId") Long spId);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE BFM_USER_ROLE
                    SET USER_ROLE_TIMES= :userRoleTimes,
                        STAFF_ROLE_TIMES= :staffRoleTimes
                    WHERE USER_ID = :userId AND ROLE_ID = :roleId
            """, nativeQuery = true)
    int updateRoleTimes(@Param("userRoleTimes") Long userRoleTimes,
                        @Param("staffRoleTimes") Long staffRoleTimes,
                        @Param("userId") Long userId,
                        @Param("roleId") Long roleId);

    @Transactional
    @Modifying
    @Query(value = """  
            DELETE FROM BFM_USER_ROLE
                 WHERE ROLE_ID = :roleId
                 AND USER_ID = :userId
                 AND (:spId IS NULL OR NVL(SP_ID, 0) = :spId)
            """, nativeQuery = true)
    public int deleteUserRoles(@Param("roleId") Long roleId,
                               @Param("userId") Long userId,
                               @Param("spId") Long spId);


    @Query(value = """
            SELECT
                A.USER_ID AS userId,
                A.USER_NAME AS userName,
                A.USER_CODE AS userCode,
                A.PWD AS pwd,
                A.ADDRESS AS address,
                A.MEMO AS memo,
                A.USER_EFF_DATE AS userEffDate,
                A.USER_EXP_DATE AS userExpDate,
                A.CREATED_DATE AS createdDate,
                A.STATE AS state,
                A.STATE_DATE AS stateDate,
                A.IS_LOCKED AS isLocked,
                A.PWD_EXP_DATE AS pwdExpDate,
                A.LOGIN_FAIL AS loginFail,
                A.PORTAL_ID AS portalId,
                A.UNLOCK_DATE AS unlockDate,
                B.ROLE_ID AS roleId,
                A.UPDATE_DATE AS updateDate
            FROM POT.BFM_USER A
            JOIN BFM_USER_ROLE B ON A.USER_ID = B.USER_ID
            WHERE B.ROLE_ID = :roleId
            AND A.STATE <> 'D'
            AND B.USER_ROLE_TIMES IS NULL
            AND (:spId IS NULL OR :spId = 0 OR B.SP_ID = :spId)
            AND (:userName IS NULL OR TRIM(:userName) = '' OR UPPER(A.USER_NAME) LIKE CONCAT('%', UPPER(TRIM(:userName)), '%'))
            AND (:userCode IS NULL OR TRIM(:userCode) = '' OR UPPER(A.USER_CODE) LIKE CONCAT('%', UPPER(TRIM(:userCode)), '%'))
            """,
            countQuery = """
                        SELECT COUNT(*)
                    FROM POT.BFM_USER A
                    JOIN BFM_USER_ROLE B ON A.USER_ID = B.USER_ID
                    WHERE B.ROLE_ID = :roleId
                    AND A.STATE <> 'D'
                    AND B.USER_ROLE_TIMES IS NULL
                    AND (:spId IS NULL OR :spId = 0 OR B.SP_ID = :spId)
                    AND (:userName IS NULL OR TRIM(:userName) = '' OR UPPER(A.USER_NAME) LIKE CONCAT('%', UPPER(TRIM(:userName)), '%'))
                    AND (:userCode IS NULL OR TRIM(:userCode) = '' OR UPPER(A.USER_CODE) LIKE CONCAT('%', UPPER(TRIM(:userCode)), '%'))
                    """,
            nativeQuery = true)
    Page<UsersRoleProjection> selectUserListByRoleIdAndFilter(
            @Param("roleId") Long roleId,
            @Param("spId") Long spId,
            @Param("userName") String userName,
            @Param("userCode") String userCode, Pageable pageable);

    @Query(value = """
            SELECT
            A.USER_ID AS userId,
            A.USER_NAME AS userName,
            A.USER_CODE AS userCode,
            A.PWD AS pwd,
            A.ADDRESS AS address,
            A.MEMO AS memo,
            A.USER_EFF_DATE AS userEffDate,
            A.USER_EXP_DATE AS userExpDate,
            A.CREATED_DATE AS createdDate,
            A.STATE AS state,
            A.STATE_DATE AS stateDate,
            A.IS_LOCKED AS isLocked,
            A.PWD_EXP_DATE AS pwdExpDate,
            A.LOGIN_FAIL AS loginFail,
            A.PORTAL_ID AS portalId,
            A.UNLOCK_DATE AS unlockDate,
            B.ROLE_ID AS roleId,
            A.UPDATE_DATE AS updateDate,
            R.ROLE_NAME AS roleName
            FROM POT.BFM_USER A
            JOIN POT.BFM_USER_ROLE B ON A.USER_ID = B.USER_ID
            JOIN POT.BFM_ROLE R ON R.ROLE_ID = B.ROLE_ID
            WHERE B.USER_ID = :userId
            AND A.STATE <> 'D'
            AND B.USER_ROLE_TIMES IS NULL
            AND (:spId IS NULL OR :spId = 0 OR B.SP_ID = :spId)
            """, nativeQuery = true)
    List<UsersRoleProjection> selectRoleByUserId(@Param("userId") Long userId,@Param("spId") Long spId);


    @Query(value = """
            SELECT
            A.USER_ID AS userId,
            A.USER_NAME AS userName,
            A.USER_CODE AS userCode,
            A.EMAIL AS email, 
            A.PHONE AS phone,
            A.PWD AS pwd,
            A.ADDRESS AS address,
            A.MEMO AS memo,
            A.USER_EFF_DATE AS userEffDate,
            A.USER_EXP_DATE AS userExpDate,
            A.CREATED_DATE AS createdDate,
            A.STATE AS state,
            A.STATE_DATE AS stateDate,
            A.IS_LOCKED AS isLocked,
            A.PWD_EXP_DATE AS pwdExpDate,
            A.LOGIN_FAIL AS loginFail,
            A.PORTAL_ID AS portalId,
            A.UNLOCK_DATE AS unlockDate,
            A.UPDATE_DATE AS updateDate,
            LISTAGG(R.ROLE_NAME, ', ') WITHIN GROUP (ORDER BY R.ROLE_NAME) AS roleNameList
            FROM POT.BFM_USER A
            LEFT JOIN POT.BFM_USER_ROLE B ON A.USER_ID = B.USER_ID
            LEFT JOIN POT.BFM_ROLE R ON R.ROLE_ID = B.ROLE_ID
            WHERE
            A.STATE <> 'D'
            AND B.USER_ROLE_TIMES IS NULL
            AND (:spId IS NULL OR :spId = 0 OR B.SP_ID = :spId)
            GROUP BY
            A.USER_ID,
            A.USER_NAME,
            A.USER_CODE,
            A.EMAIL,
            A.PHONE,
            A.PWD,
            A.ADDRESS,
            A.MEMO,
            A.USER_EFF_DATE,
            A.USER_EXP_DATE,
            A.CREATED_DATE,
            A.STATE,
            A.STATE_DATE,
            A.IS_LOCKED,
            A.PWD_EXP_DATE,
            A.LOGIN_FAIL,
            A.PORTAL_ID,
            A.UNLOCK_DATE,
            A.UPDATE_DATE;
            """, nativeQuery = true)
    List<UsersRoleProjection> selectAllBySPID(@Param("spId") Long spId);

    @Query(value = """
           SELECT
            A.USER_ID AS userId,
            A.USER_NAME AS userName,
            A.USER_CODE AS userCode,
            A.EMAIL AS email,
            A.PHONE AS phone,
            A.PWD AS pwd,
            A.ADDRESS AS address,
            A.MEMO AS memo,
            A.USER_EFF_DATE AS userEffDate,
            A.USER_EXP_DATE AS userExpDate,
            A.CREATED_DATE AS createdDate,
            A.STATE AS state,
            A.STATE_DATE AS stateDate,
            A.IS_LOCKED AS isLocked,
            A.PWD_EXP_DATE AS pwdExpDate,
            A.LOGIN_FAIL AS loginFail,
            A.PORTAL_ID AS portalId,
            A.UNLOCK_DATE AS unlockDate,
            A.UPDATE_DATE AS updateDate,
            LISTAGG(R.ROLE_NAME, ', ') WITHIN GROUP (ORDER BY R.ROLE_NAME) AS roleNameList
            FROM POT.BFM_USER A
            LEFT JOIN POT.BFM_USER_ROLE B ON A.USER_ID = B.USER_ID
            LEFT JOIN POT.BFM_ROLE R ON R.ROLE_ID = B.ROLE_ID
            WHERE
            A.STATE <> 'D'
            AND B.USER_ROLE_TIMES IS NULL
            AND R.ROLE_ID = :roleID
            AND (:spId IS NULL OR :spId = 0 OR B.SP_ID = :spId)
            GROUP BY
            A.USER_ID,
            A.USER_NAME,
            A.USER_CODE,
            A.EMAIL,
            A.PHONE,
            A.PWD,
            A.ADDRESS,
            A.MEMO,
            A.USER_EFF_DATE,
            A.USER_EXP_DATE,
            A.CREATED_DATE,
            A.STATE,
            A.STATE_DATE,
            A.IS_LOCKED,
            A.PWD_EXP_DATE,
            A.LOGIN_FAIL,
            A.PORTAL_ID,
            A.UNLOCK_DATE,
            A.UPDATE_DATE;
            """, nativeQuery = true)
    List<UsersRoleProjection> selectAllByRoleID(@Param("roleID")Long roleID,@Param("spId") Long spId);

    @Query(value = """
            SELECT 
                A.USER_ID AS userId,
                A.USER_NAME AS userName,
                A.USER_CODE AS userCode,
                A.PWD AS pwd,
                A.ADDRESS AS address,
                A.MEMO AS memo,
                A.USER_EFF_DATE AS userEffDate,
                A.USER_EXP_DATE AS userExpDate,
                A.CREATED_DATE AS createdDate,
                A.STATE AS state,
                A.STATE_DATE AS stateDate,
                A.IS_LOCKED AS isLocked,
                A.PWD_EXP_DATE AS pwdExpDate,
                A.LOGIN_FAIL AS loginFail,
                A.PORTAL_ID AS portalId,
                A.UNLOCK_DATE AS unlockDate,
                A.UPDATE_DATE AS updateDate
            FROM POT.BFM_USER A
            WHERE 
                (SELECT COUNT(1) FROM BFM_USER_ROLE B 
                 WHERE A.USER_ID = B.USER_ID 
                   AND B.ROLE_ID = :roleId 
                   AND B.USER_ROLE_TIMES IS NULL) = 0
                AND A.STATE <> 'D'
                AND (:userName IS NULL OR TRIM(:userName) = '' OR UPPER(A.USER_NAME) LIKE CONCAT('%', UPPER(TRIM(:userName)), '%'))
                AND (:userCode IS NULL OR TRIM(:userCode) = '' OR UPPER(A.USER_CODE) LIKE CONCAT('%', UPPER(TRIM(:userCode)), '%'))
            """,
            countQuery = """
                        SELECT COUNT(1)
                        FROM POT.BFM_USER A
                        WHERE 
                            (SELECT COUNT(1) FROM BFM_USER_ROLE B 
                             WHERE A.USER_ID = B.USER_ID 
                               AND B.ROLE_ID = :roleId 
                               AND B.USER_ROLE_TIMES IS NULL) = 0
                            AND A.STATE <> 'D'
                            AND (:userName IS NULL OR TRIM(:userName) = '' OR UPPER(A.USER_NAME) LIKE CONCAT('%', UPPER(TRIM(:userName)), '%'))
                            AND (:userCode IS NULL OR TRIM(:userCode) = '' OR UPPER(A.USER_CODE) LIKE CONCAT('%', UPPER(TRIM(:userCode)), '%'))
                    """,
            nativeQuery = true)
    Page<UserInfoProjection> selectUnGrantUserListByRoleIdAndFilter(
            @Param("roleId") Long roleId,
            @Param("userName") String userName,
            @Param("userCode") String userCode,
            Pageable pageable
    );

    @Query(value = """
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
                    A.STATE AS state,
                    A.ICON AS icon,
                    A.DEFAULT_PARAM AS defaultParam,
                    A.STATE_DATE AS stateDate,
                    C.PRIV_ID AS privId,
                    C.URL AS url,
                    A.DEFAULT_WIDTH AS defaultWidth,
                    A.DEFAULT_HEIGHT AS defaultHeight,
                    A.VIEW_TYPE AS viewType,
                    A.IS_DRAWABLE AS isDrawable
                FROM POT.BFM_PORTLETS A
                JOIN BFM_PRIV C ON A.PORTLET_ID = C.PRIV_ID
                JOIN BFM_ROLE_PRIV D ON C.PRIV_ID = D.PRIV_ID
                WHERE C.PRIV_TYPE = 'P'
                  AND C.STATE = 'A'
                  AND D.ROLE_ID = :roleId
            """, nativeQuery = true)
    List<PortletProjection> selectRoleOwnedPortletList(@Param("roleId") Long roleId);


    @Query(
            value = """
                    SELECT 
                        A.USER_ID AS userId,
                        A.ROLE_ID AS roleId,
                        B.ROLE_NAME AS roleName,
                        B.COMMENTS AS comments,
                        B.ROLE_CODE AS roleCode,
                        B.IS_LOCKED AS isLocked,
                        A.USER_ROLE_TIMES AS userRoleTimes,
                        A.STAFF_ROLE_TIMES AS staffRoleTimes,
                        A.SP_ID AS spId,
                        A.CREATED_DATE AS createdDate,
                        A.UPDATE_DATE AS updateDate
                    FROM POT.BFM_USER_ROLE A
                    JOIN POT.BFM_ROLE B ON A.ROLE_ID = B.ROLE_ID
                    WHERE A.USER_ROLE_TIMES IS NULL
                    AND A.USER_ID = :userId
                    AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId)
                    ORDER BY B.ROLE_ID
                    """,
            nativeQuery = true
    )
    List<UsersRoleProjection> selectRoleListByUserIdWithoutJob(@Param("userId") Long userId, @Param("spId") Long spId);


    @Query(value = """
            SELECT\s
                        A.USER_ID AS userId,
                        A.ROLE_ID AS roleId,
                        B.ROLE_NAME AS roleName,
                        B.COMMENTS AS comments,
                        B.ROLE_CODE AS roleCode,
                        B.IS_LOCKED AS isLocked,
                        A.USER_ROLE_TIMES AS userRoleTimes,
                        A.STAFF_ROLE_TIMES AS staffRoleTimes,
                        A.SP_ID AS spId,
                        A.CREATED_DATE AS createdDate,
                        A.UPDATE_DATE AS updateDate
                    FROM BFM_USER_ROLE A
                    JOIN BFM_ROLE B ON A.ROLE_ID = B.ROLE_ID
                    WHERE A.USER_ID = :userId
                    AND (:spId IS NULL OR A.SP_ID = :spId)
                    ORDER BY B.ROLE_ID
            """, nativeQuery = true)
    List<UserRoleExtProjection> selectRoleListByUserId(@Param("userId") Long userId, @Param("spId") Long spId);
}
