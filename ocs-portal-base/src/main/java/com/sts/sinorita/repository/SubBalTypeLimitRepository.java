package com.sts.sinorita.repository;

import com.sts.sinorita.entity.SubBalTypeLimit;
import com.sts.sinorita.entity.SubBalTypeLimitId;
import com.sts.sinorita.projection.trigger.QrySubBalTypeLimitProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubBalTypeLimitRepository extends JpaRepository<SubBalTypeLimit, SubBalTypeLimitId> {

    @Query(value = """
            SELECT
            	A.SUB_BAL_TYPE_ID as subBalTypeId,
            	A.ACCT_ITEM_TYPE_ID as acctItemTypeId,
            	B.ACCT_ITEM_TYPE_NAME as acctItemTypeName
            FROM
            	SUB_BAL_TYPE_LIMIT A,
            	ACCT_ITEM_TYPE B
            WHERE
            	A.ACCT_ITEM_TYPE_ID = B.ACCT_ITEM_TYPE_ID
            	AND (:SUB_BAL_TYPE_ID IS NULL OR A.SUB_BAL_TYPE_ID = :SUB_BAL_TYPE_ID)
            	AND (:SP_ID IS NULL OR NVL(B.SP_ID, 0)= :SP_ID)
            """,nativeQuery = true)
    List<QrySubBalTypeLimitProjection> qrySubBalTypeLimit(@Param("SUB_BAL_TYPE_ID") Integer subBalTypeId,
                                                          @Param("SP_ID") Integer spId);


    @Modifying
    @Query(value = "DELETE FROM SUB_BAL_TYPE_LIMIT WHERE SUB_BAL_TYPE_ID = :subBalTypeId",nativeQuery = true)
    void deleteSubBalTypeId(@Param("subBalTypeId") Integer subBalTypeId);

    @Query(value = "SELECT S FROM SubBalTypeLimit S WHERE S.id.subBalTypeId = :subBalTypeId")
    Optional<SubBalTypeLimit>  findBySubBalTypeId(@Param("subBalTypeId") Integer subBalTypeId);

    @Query(value = "SELECT S FROM SubBalTypeLimit S WHERE S.id.subBalTypeId = :subBalTypeId")
    List<SubBalTypeLimit>  findBySubBalTypeIds(@Param("subBalTypeId") Integer subBalTypeId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SUB_BAL_TYPE_LIMIT WHERE SUB_BAL_TYPE_ID = :subBalTypeId", nativeQuery = true)
    void deleteBySubBalTypeId(@Param("subBalTypeId") Integer subBalTypeId);
}
