package com.sts.sinorita.repository;

import com.sts.sinorita.dto.response.accountconfig.AcctValuesListDto;
import com.sts.sinorita.entity.AcctAttr;
import com.sts.sinorita.projection.accountConfig.QryAcctAttrIncludeChannelNewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AcctAttrRepository extends JpaRepository<AcctAttr, Integer> {
    @Query(value = """
        SELECT A.ATTR_ID AS attrId,
               C.ATTR_NAME AS attrName,
               C.ATTR_TYPE AS attrType,
               C.ATTR_CODE AS attrCode,
               C.CSR_VISIBLE AS csrVisible,
               A.SP_ID AS spId,
               C.OBJ_ATTR_ID AS objAttrId,
               A.ATTR_VALUE AS attrValue,
               A.DISP_ORDER AS dispOrder
        FROM ACCT_ATTR A
        LEFT JOIN ATTR C ON A.ATTR_ID = C.ATTR_ID
        WHERE C.OBJ_ATTR_ID IS NULL
          AND (:spId IS NULL OR NVL(A.SP_ID,0) = :spId)
        ORDER BY A.DISP_ORDER
    """, nativeQuery = true)
    List<QryAcctAttrIncludeChannelNewProjection> findAcctAttr(@Param("spId") Integer spId);

    @Modifying
    @Query("DELETE FROM AcctAttr a WHERE COALESCE(a.spId, 0) = :spId AND a.attrId = :attrId")
    void deleteByAttrIdAndSpId(@Param("attrId") Integer attrId, @Param("spId") Integer spId);


    @Query(value = """
    SELECT 
        A.DISP_ORDER AS dispOrder,
        A.ATTR_VALUE AS attrValue,
        A.SP_ID AS spId,
        C.ATTR_ID AS attrId,
        C.ATTR_NAME AS attrName,
        C.ATTR_TYPE AS attrType,
        C.OBJ_ATTR_ID AS objAttrId,
        C.ATTR_CODE AS attrCode,
        C.CSR_VISIBLE AS csrVisible,
        D.CONTACT_CHANNEL_ID AS contactChannelId
    FROM ACCT_ATTR A
    LEFT JOIN ATTR C ON A.ATTR_ID = C.ATTR_ID
    LEFT JOIN (
        SELECT 
            E.ATTR_ID,
            LISTAGG(E.CONTACT_CHANNEL_ID, ',') 
                WITHIN GROUP (ORDER BY E.ATTR_ID) AS CONTACT_CHANNEL_ID
        FROM ATTR_APPLY_CHANNEL E
        GROUP BY E.ATTR_ID
    ) D ON A.ATTR_ID = D.ATTR_ID
    WHERE C.OBJ_ATTR_ID IS NULL
      AND (:spId IS NULL OR COALESCE(A.SP_ID, 0) = :spId)
    ORDER BY A.DISP_ORDER
""", nativeQuery = true)
    List<QryAcctAttrIncludeChannelNewProjection> qryAcctAttrIncludeChannelNew(@Param("spId") Integer spId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ACCT_ATTR WHERE NVL(SP_ID, 0) = :spId AND ATTR_ID = :attrId", nativeQuery = true)
    void deleteBySpIdAndAttrId(@Param("spId") Integer spId, @Param("attrId") Integer attrId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ACCT_ATTR WHERE NVL(SP_ID, 0) = :spId", nativeQuery = true)
    void deleteBySpId(@Param("spId") Integer spId);

    @Query(value = "SELECT * FROM ACCT_ATTR WHERE NVL(SP_ID, 0) = :spId ORDER BY DISP_ORDER", nativeQuery = true)
    List<AcctAttr> findAllBySpId(@Param("spId") Integer spId);

    @Modifying
    @Transactional
    @Query("UPDATE AcctAttr a SET a.dispOrder = :dispOrder WHERE a.attrId = :attrId")
    int updateDispOrder(@Param("attrId") Integer attrId, @Param("dispOrder") Integer dispOrder);


}
