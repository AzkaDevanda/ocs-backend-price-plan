package com.ocs.portal.svc.role.auth.repository;

import com.ocs.portal.entity.BfmUser;
import com.ocs.portal.svc.role.auth.entity.UserLogin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserLogin,Integer> {

    @Query("SELECT r FROM UserLogin r WHERE r.username = :username ")
    Optional<UserLogin> findByUserName(@Param("username")String username);

    @Modifying
    @Transactional
    @Query(value = "update POT.BFM_USER set IS_LOCKED = :isLocked , LOGIN_FAIL_DATE =:loginFailDate, LOGIN_FAIL=:loginFail where USER_ID = :userId", nativeQuery = true)
    int updateStateUser(@Param("userId")Long userId,@Param("isLocked")String isLocked, @Param("loginFailDate")LocalDateTime loginFailDate,  @Param("loginFail")Integer loginFail);

    @Modifying
    @Transactional
    @Query(value = "update POT.BFM_USER set LAST_LOGIN_DATE = :lastLogin where USER_ID = :userId", nativeQuery = true)
    int updateLastLogin(@Param("userId")Long userId,@Param("lastLogin") LocalDateTime lastLogin);



    @Query("select u from BfmUser u where 1=1 and  (:state is null or u.state = :state)")
    List<BfmUser>selectUsersListAll(@Param("state")String state);

}
