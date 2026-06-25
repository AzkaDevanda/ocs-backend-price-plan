package com.ocs.portal.repository;


import com.ocs.portal.entity.RePpRecurring;
import com.ocs.portal.entity.RePpRecurringId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RePpRecurringRepository extends JpaRepository<RePpRecurring, RePpRecurringId> {

  @Modifying
  @Transactional
  @Query("DELETE FROM RePpRecurring r WHERE r.id.reId = :reId")
  void deleteByReId (@Param("reId") Integer reId);

  @Query("SELECT r FROM RePpRecurring r WHERE r.id.reId = :reId")
  Optional<RePpRecurring> findByReId (@Param("reId") Integer reId);

  @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM RePpRecurring r WHERE r.id.pricePlanId = :pricePlanId")
  boolean existsByPricePlanId (Integer pricePlanId);

  @Modifying
  @Query("DELETE FROM RePpRecurring r WHERE r.id.pricePlanId = :pricePlanId")
  void deleteByPricePlanId (Integer pricePlanId);

}
