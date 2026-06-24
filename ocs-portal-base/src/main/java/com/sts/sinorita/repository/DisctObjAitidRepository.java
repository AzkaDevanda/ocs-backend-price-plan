package com.sts.sinorita.repository;

import com.sts.sinorita.entity.DisctObjAitid;
import com.sts.sinorita.entity.DisctObjAitidId;
import com.sts.sinorita.projection.pricePlan.discount.QryDisctObjInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DisctObjAitidRepository extends JpaRepository<DisctObjAitid, DisctObjAitidId> {
    @Query(value = """
    SELECT T.DISCT_OBJ_ID     AS disctObjId,
           T.ACCT_ITEM_TYPE_ID AS acctItemTypeId,
           T.PRIORITY          AS priority,
           T.SP_ID             AS spId,
           A.ACCT_ITEM_TYPE_NAME AS acctItemTypeName
      FROM DISCT_OBJ_AITID T, ACCT_ITEM_TYPE A
     WHERE T.DISCT_OBJ_ID = :disctObjId
       AND T.ACCT_ITEM_TYPE_ID = A.ACCT_ITEM_TYPE_ID
       AND NVL(T.SP_ID,0) = :spId
     ORDER BY T.PRIORITY
    """, nativeQuery = true)
    List<QryDisctObjInfoProjection> QryDisctObjInfo(
            @Param("disctObjId") Integer disctObjId,
            @Param("spId") Integer spId
    );

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM DISCT_OBJ_AITID WHERE DISCT_OBJ_ID = :disctObjId
        """, nativeQuery = true)
    void deleteDisctObjAitid(@Param("disctObjId") Integer disctObjId);


}
