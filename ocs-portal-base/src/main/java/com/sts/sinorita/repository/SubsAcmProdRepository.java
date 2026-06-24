package com.sts.sinorita.repository;

import com.sts.sinorita.entity.SubsAcmProd;
import com.sts.sinorita.entity.SubsAcmProdId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SubsAcmProdRepository extends JpaRepository<SubsAcmProd, SubsAcmProdId> {

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO SUBS_ACM_PROD (SUBS_ID, RESOURCE_ID, PROD_ID, VALUE) VALUES (:subsId, :resourceId, :prodId, :value)", nativeQuery = true)
  void insertSubsAcmProd (@Param("subsId") Long subsId, @Param("resourceId") Long resourceId, @Param("prodId") Long prodId, @Param("value") Long value);

  @Transactional
  @Modifying
  @Query(value = "UPDATE SUBS_ACM_PROD SET VALUE = VALUE + :value WHERE SUBS_ID = :subsId AND RESOURCE_ID = :resourceId AND PROD_ID = :prodId", nativeQuery = true)
  int updateSubsAcmProd (@Param("value") Long value, @Param("subsId") Long subsId, @Param("resourceId") Long resourceId, @Param("prodId") Long prodId);
}
