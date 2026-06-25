package com.ocs.portal.auth.repository;

import com.ocs.portal.auth.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserLogin,Integer> {

    @Query("SELECT r FROM UserLogin r WHERE r.username = :username")
    Optional<UserLogin> findByUserName(@Param("username")String username);

}
