package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmUser;
import com.ocs.portal.svc.role.projection.UserDtoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BfmUserRepository extends JpaRepository<BfmUser, Integer> {

    @Query(value = """
           SELECT
               A.USER_ID AS userId,
               A.USER_NAME AS userName,
               A.USER_CODE AS userCode,
               A.ADDRESS AS address,
               A.MEMO AS memo,
               A.USER_EFF_DATE AS userEffDate,
               A.USER_EXP_DATE AS userExpDate,
               A.CREATED_DATE AS createdDate,
               A.STATE AS state,
               A.STATE_DATE AS stateDate,
               A.PHONE AS phone,
               A.EMAIL AS email,
               A.IS_LOCKED AS isLocked,
               A.PWD_EXP_DATE AS pwdExpDate,
               A.LOGIN_FAIL AS loginFail,
               A.UNLOCK_DATE AS unlockDate,
               A.OPEN_ID AS openId,
               A.USER_TYPE AS userType,
               A.ALIAS AS alias,
               A.LAST_LOGIN_DATE AS lastLoginDate,
               A.SECURITY_QUESTION_ID AS securityQuestionId,
               A.SECURITY_ANSWER AS securityAnswer,
               A.THUMBNAIL_URI AS thumbnailUri,
               A.EXT_ATTR AS extAttr,
               B.PORTAL_NAME AS portalName,
               B.PORTAL_ID AS portalId,
               A.UPDATE_DATE AS updateDate,
               A.UNIT AS unit
            FROM POT.BFM_USER A
            LEFT JOIN POT.BFM_PORTAL B ON A.PORTAL_ID = B.PORTAL_ID
            WHERE 1 = 1
             AND (:userName IS NULL OR TRIM(:userName) = '' OR UPPER(A.USER_NAME) LIKE '%' || UPPER(TRIM(:userName)) || '%')
             AND (:userCode IS NULL OR TRIM(:userCode) = '' OR UPPER(A.USER_CODE) LIKE '%' || UPPER(TRIM(:userCode)) || '%')
             AND (:state IS NULL OR A.STATE = :state)
             AND (:isLock IS NULL OR A.IS_LOCKED = :isLock)
            ORDER BY A.USER_ID
            """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM POT.BFM_USER A
                    LEFT JOIN POT.BFM_PORTAL B ON A.PORTAL_ID = B.PORTAL_ID
                    WHERE 1=1 
                    AND (:userName IS NULL OR TRIM(:userName) = '' OR UPPER(A.USER_NAME) LIKE CONCAT('%', UPPER(TRIM(:userName)), '%'))
                    AND (:userCode IS NULL OR TRIM(:userCode) = '' OR UPPER(A.USER_CODE) LIKE CONCAT('%', UPPER(TRIM(:userCode)), '%'))
                    AND (:state IS NULL OR A.STATE = :state)
                    AND (:isLock IS NULL OR A.IS_LOCKED = :isLock)
                    ORDER BY A.USER_ID
                    """,
            nativeQuery = true)
    Page<UserDtoProjection> selectUserList(
            @Param("userName") String userName,
            @Param("userCode") String userCode,
            @Param("state")String state,
            @Param("isLock")String isLock,
            Pageable pageable
    );

    @Query(value = """
        select a from BfmUser a where a.userName = :userName
        """)
    public Optional<BfmUser> isSameValueByName(@Param("userName")String userName);

    @Query(value = """
        select a from BfmUser a where a.userCode = :userCode
        """)
    public Optional<BfmUser> isSameValue(@Param("userCode")String userCode);

    @Query(value = """
        select a from BfmUser a where a.email = :email
        """)
    public Optional<BfmUser> chekEmail(@Param("email")String email);

    @Query(value = """
        select a from BfmUser a where a.phone = :phone
        """)
    public Optional<BfmUser> chekPhone(@Param("phone")String phone);

    @Query(value = "select u from BfmUser u where u.userName = :userName and u.userCode = :userCode")
    public Optional<BfmUser> findBfmUser(@Param("userName")String userName, @Param("userCode")String userCode);

    @Query(value = "select u from BfmUser u where u.userId = :id ")
    public Optional<BfmUser> findBfmUserById(@Param("id")Long id);


    @Query(value = "select u from BfmUser u where u.userId = :userId and u.userName = :userName and u.userCode = :userCode")
    public Optional<BfmUser> findBfmUserByUserId(@Param("userId")Long userId, @Param("userName")String userName, @Param("userCode")String userCode);

    @Query("SELECT u FROM BfmUser u WHERE u.state <> 'D' AND u.userId = :userId")
    Optional<BfmUser> selectUserByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query(value = "DELETE FROM POT.BFM_USER WHERE USER_ID = :userId", nativeQuery = true)
    public void deleteUserByUserId(@Param("userId") Integer userId);

}
