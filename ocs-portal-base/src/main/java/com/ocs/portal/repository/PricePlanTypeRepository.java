package com.ocs.portal.repository;


import com.ocs.portal.dto.request.PricePlanTypeDto;
import com.ocs.portal.entity.PricePlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricePlanTypeRepository extends JpaRepository<PricePlanType, Character> {
  @Query("SELECT ppt.id, ppt.pricePlanTypeName, pp.applyLevel " +
    "FROM PricePlan pp " +
    "JOIN SubsPricePlan spp ON pp.id = spp.id " +
    "JOIN PricePlanType ppt ON spp.pricePlanType = ppt.id " +
    "where pp.applyLevel = 'S' " +
    "GROUP BY ppt.id, ppt.pricePlanTypeName, pp.applyLevel")
  List<Object[]> fetchPricePlanDataS ();

  @Query("SELECT ppt.id, ppt.pricePlanTypeName " +
    "FROM PricePlanType ppt  " +
    "GROUP BY ppt.id, ppt.pricePlanTypeName")
  List<Object[]> fetchPricePlanDataSType ();

//    @Query("SELECT request.dto.com.sts.portal.base.PricePlanTypeDto(ppt.id, ppt.pricePlanTypeName, pp.applyLevel)" +
//            "FROM PricePlan pp " +
//            "JOIN AcctPricePlan spp ON pp.id = spp.id " +
//            "JOIN PricePlanType ppt ON spp.pricePlanType = ppt.id " +
//            "WHERE pp.applyLevel = 'A' AND ppt.id IN (:param1,:param2)")
//    List<PricePlanTypeDto> fetchPricePlanDataA(@Param("param1")Character param1, @Param("param2")Character param2);

  @Query("SELECT new com.sts.sinorita.dto.request.PricePlanTypeDto(ppt.id, ppt.pricePlanTypeName) FROM PricePlanType ppt where ppt.id IN (:param1,:param2)")
//    @Query(value = "SELECT * FROM PRICE_PLAN_TYPE p WHERE p.PRICE_PLAN_TYPE IN ('2', '3')", nativeQuery = true)
//    List<PricePlanType> findDefaultAndIndividual();
  List<PricePlanTypeDto> findDefaultAndIndividual (@Param("param1") Character param1, @Param("param2") Character param2);
}
