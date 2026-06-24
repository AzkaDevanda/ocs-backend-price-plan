package com.sts.sinorita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.SubsUppInst;

@Repository
public interface SubsUppInstRepository extends JpaRepository<SubsUppInst, Long> {
  @Query(value = """
          SELECT DISTINCT A.PRICE_PLAN_ID
          FROM SUBS_UPP_INST A
          WHERE A.SUBS_ID = :subsId
            AND A.STATE = 'A'
            AND A.EFF_DATE <= SYSDATE
            AND (A.EXP_DATE IS NULL OR A.EXP_DATE > SYSDATE)
      """, nativeQuery = true)
  List<Long> qryUppPricePlanBySubsId(@Param("subsId") Long subsId);
}
