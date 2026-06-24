package com.sts.sinorita.repository;

import com.sts.sinorita.entity.DisctObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DisctObjRepository extends JpaRepository<DisctObj, Integer> {

    @Query(value = "SELECT NVL(GREATEST(MAX(t.DISCT_OBJ_ID) + 1, 1), 1) FROM DISCT_OBJ t", nativeQuery = true)
    Integer getMaxPriority();

    @Modifying
    @Query(value = """
    INSERT INTO DISCT_OBJ 
    (DISCT_OBJ_ID, OBJ_IDENTITY_ID, GST_SEQ, TAB_DP_COND_GRP_ID, DISCT_OBJ_NAME, DISCT_OBJ_TYPE, MEMBER_ALIAS)
    VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)
    """, nativeQuery = true)
    void insertDisctObj(Integer disctObjId,
                        Integer objIdentityId,
                        Integer gstSeq,
                        Integer tabDpCondGrpId,
                        String disctObjName,
                        Character disctObjType,
                        String memberAlias);


    @Query(value = """
        SELECT *
        FROM DISCT_OBJ d
        WHERE d.DISCT_OBJ_ID = :disctObjId
    """,nativeQuery = true)
    Optional<DisctObj> selectDisctObject(@Param("disctObjId") Integer disctObjId);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM DISCT_OBJ WHERE DISCT_OBJ_ID = :disctObjId
        """, nativeQuery = true)
    void deleteDisctObject(@Param("disctObjId") Integer disctObjId);

}
