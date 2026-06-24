package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BalAcctItemType;
import com.sts.sinorita.entity.BalAcctItemTypeId;
import com.sts.sinorita.projection.balanceAdjustment.SelectBalAcctItemTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalAcctItemTypeRepository extends JpaRepository<BalAcctItemType, BalAcctItemTypeId> {

  @Query("SELECT b FROM BalAcctItemType b WHERE b.id.balId IN :balIds ORDER BY b.id.balId")
  List<BalAcctItemType> findByBalIdInOrderByBalId (@Param("balIds") List<Long> balIds);

  @Query(value = """
        SELECT 
            BAL_ID           AS balId,
            ACCT_ITEM_TYPE_ID AS acctItemTypeId,
            SP_ID            AS spId
        FROM BAL_ACCT_ITEM_TYPE
        WHERE BAL_ID = :balId 
          AND ACCT_ITEM_TYPE_ID = :acctItemTypeId
    """, nativeQuery = true)
  Optional<SelectBalAcctItemTypeProjection> selectBalAcctItemType (
    @Param("balId") Long balId,
    @Param("acctItemTypeId") Long acctItemTypeId
  );

  @Transactional
  @Modifying
  @Query(
    value = """
          INSERT INTO BAL_ACCT_ITEM_TYPE
          (BAL_ID, ACCT_ITEM_TYPE_ID, SP_ID)
          VALUES (:balId, :acctItemTypeId, :spId)
      """,
    nativeQuery = true
  )
  void balAcctItemTypeBatchStore (
    @Param("balId") Long balId,
    @Param("acctItemTypeId") Long acctItemTypeId,
    @Param("spId") Long spId
  );


}
