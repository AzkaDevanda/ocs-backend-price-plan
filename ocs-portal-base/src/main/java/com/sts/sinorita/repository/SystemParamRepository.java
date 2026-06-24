package com.sts.sinorita.repository;

import com.sts.sinorita.entity.SystemParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemParamRepository extends JpaRepository<SystemParam, Long> {
  @Query(value = """
          SELECT CURRENT_VALUE FROM SYSTEM_PARAM WHERE MASK = :mask
          """, nativeQuery = true)
  String selectSystemParam (@Param("mask") String mask);

}
