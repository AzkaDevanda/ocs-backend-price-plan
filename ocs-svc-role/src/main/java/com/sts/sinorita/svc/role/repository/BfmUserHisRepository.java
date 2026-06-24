package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmUserHis;
import com.sts.sinorita.svc.role.projection.UserHisProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BfmUserHisRepository extends JpaRepository<BfmUserHis, Long> {
    @Query(value = """
              SELECT
                u.REC_ID          AS recId,
                u.USER_ID         AS userId,
                u.USER_NAME       AS userName,
                u.USER_CODE       AS userCode,
                u.ADDRESS         AS address,
                u.MEMO            AS memo,
                u.USER_EFF_DATE   AS userEffDate,
                u.USER_EXP_DATE   AS userExpDate,
                u.CREATED_DATE    AS createdDate,
                u.STATE           AS state,
                u.STATE_DATE      AS stateDate,
                u.IS_LOCKED       AS isLocked,
                u.PWD_EXP_DATE    AS pwdExpDate,
                u.LOGIN_FAIL      AS loginFail,
                u.UNLOCK_DATE     AS unlockDate,
                u.REC_USER_ID     AS recUserId,
                u.REC_CREATE_DATE AS recCreateDate,
                u.IP_ADDRESS      AS ipAddress,
                u.COMMENTS        AS comments,
                bu.USER_NAME AS recUserName,
                          bu.USER_CODE AS recUserCode
            FROM BFM_USER_HIS u
            JOIN POT.BFM_USER bu ON bu.user_id = u.REC_USER_ID
            WHERE (:startDate IS NULL OR u.REC_CREATE_DATE >= :startDate)
              AND (:endDate IS NULL OR u.REC_CREATE_DATE <= :endDate)
              AND (:userName IS NULL OR UPPER(u.USER_NAME) LIKE UPPER('%' || :userName || '%'))
            """,
            nativeQuery = true)
    Page<UserHisProjection> selectUserHistoryList(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate,
                                                  @Param("orderFields") String orderFields,
                                                  @Param("userName") String userName, Pageable pageable);

}
