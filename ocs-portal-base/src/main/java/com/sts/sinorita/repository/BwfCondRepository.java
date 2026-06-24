package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BwfCond;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BwfCondRepository extends JpaRepository<BwfCond, Integer> {

    @Query(value = "select max(cond_group_id) from BWF_COND", nativeQuery = true)
    Integer getMaxBwfCondId();

    @Modifying
    @Transactional
    @Query("DELETE FROM BwfCond b WHERE b.id.condGroupId = :condGroupId")
    void deleteByCondGroupId(@Param("condGroupId") Long condGroupId);


}
