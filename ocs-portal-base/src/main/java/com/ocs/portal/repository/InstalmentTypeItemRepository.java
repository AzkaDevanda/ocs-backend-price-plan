package com.ocs.portal.repository;

import java.util.List;

import com.ocs.portal.entity.InstalmentTypeItem;
import com.ocs.portal.entity.InstalmentTypeItemId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ocs.portal.projection.accountConfig.QryInstalmentTypeItemByIntalmentTypeIdProjection;

@Repository
public interface InstalmentTypeItemRepository extends JpaRepository<InstalmentTypeItem, InstalmentTypeItemId> {
  boolean existsByIdAcctItemTypeId(Integer acctItemTypeId);

  @Query(value = """
      SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
      FROM INSTALMENT_TYPE_ITEM
      WHERE ACCT_ITEM_TYPE_ID = :acctItemTypeId
        AND INSTALMENT_TYPE_ID = :instalmentTypeId
      """, nativeQuery = true)
  Integer existsByAcctItemTypeIdAndInstalmentTypeId(@Param("acctItemTypeId") Integer acctItemTypeId,
      @Param("instalmentTypeId") Long instalmentTypeId);

  @Modifying
  @Transactional
  @Query("DELETE FROM InstalmentTypeItem iti WHERE iti.id.instalmentTypeId = :instalmentTypeId")
  void deleteByInstalmentTypeId(@Param("instalmentTypeId") Long instalmentTypeId);

  @Query(value = "SELECT i.ACCT_ITEM_TYPE_ID AS acctItemTypeId FROM INSTALMENT_TYPE_ITEM i WHERE i.INSTALMENT_TYPE_ID = :instalmentTypeId", nativeQuery = true)
  List<QryInstalmentTypeItemByIntalmentTypeIdProjection> findByInstalmentTypeId(
      @Param("instalmentTypeId") Long instalmentTypeId);

  @Modifying
  @Query("DELETE FROM InstalmentTypeItem iti WHERE iti.id.instalmentTypeId = :instalmentTypeId AND iti.id.acctItemTypeId = :acctItemTypeId")
  void deleteByInstalmentTypeIdAndAcctItemTypeId(
      @Param("instalmentTypeId") Long instalmentTypeId,
      @Param("acctItemTypeId") Integer acctItemTypeId);
}
