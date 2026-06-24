package com.sts.sinorita.repository;

import java.util.List;
import java.util.Optional;

import com.sts.sinorita.projection.accountConfig.QryAttrValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.AttrValue;
import com.sts.sinorita.projection.attrvalue.AttrValueProjection;

@Repository
public interface AttrValueRepository extends JpaRepository<AttrValue, Integer> {
  @Query(value = """
      SELECT 
        A.BASE_ATTR_ID AS baseAttrId,
        A.ATTR_VALUE_ID AS attrValueId,
        A.VALUE_MARK AS valueMark,
        A.VALUE AS value,
        A.PARENT_ATTR_VALUE_ID AS parentAttrValueId,
        A.PARENT_ATTR_ID AS parentAttrId,
        B.ATTR_NAME AS attrName,
        B.ATTR_CODE AS attrCode
      FROM ATTR_VALUE A, ATTR B
      WHERE A.BASE_ATTR_ID = B.ATTR_ID
        AND A.BASE_ATTR_ID = :BASE_ATTR_ID
        AND B.ATTR_CODE = :ATTR_CODE
        AND A.ATTR_VALUE_ID = :ATTR_VALUE_ID
        AND A.VALUE = :VALUE
        AND A.VALUE_MARK = :VALUE_MARK
        AND TO_NUMBER(NVL(A.VALUE, '0')) >= TO_NUMBER(:OLD_CREDIT_LIMIT_1)
        AND TO_NUMBER(NVL(A.VALUE, '0')) <= TO_NUMBER(:OLD_CREDIT_LIMIT_2)
        AND (B.ATTR_CODE IS NOT NULL OR (B.ATTR_CODE IS NULL AND NVL(B.SP_ID,0) = :SP_ID))
      ORDER BY A.ATTR_VALUE_ID
        """, nativeQuery = true)
  Optional<AttrValueProjection> findAttrValue(@Param("BASE_ATTR_ID") Integer baseAttrId,
      @Param("ATTR_CODE") String attrCode, @Param("ATTR_VALUE_ID") Integer attrValueId, @Param("VALUE") Integer value,
      @Param("VALUE_MARK") String valueMark, @Param("OLD_CREDIT_LIMIT_1") Integer oldCreditLimit1,
      @Param("OLD_CREDIT_LIMIT_2") Integer oldCreditLimit2, @Param("SP_ID") Integer spId);

  @Query(value = """
        SELECT
            A.BASE_ATTR_ID AS baseAttrId,
            A.ATTR_VALUE_ID AS attrValueId,
            A.VALUE_MARK AS valueMark,
            A.VALUE AS value,
            A.PARENT_ATTR_VALUE_ID AS parentAttrValueId,
            A.PARENT_ATTR_ID AS parentAttrId,
            B.ATTR_NAME AS attrName
        FROM ATTR_VALUE A
            JOIN ATTR B ON A.BASE_ATTR_ID = B.ATTR_ID
        WHERE A.BASE_ATTR_ID IN (:baseAttrIds)
        ORDER BY A.ATTR_VALUE_ID
    """, nativeQuery = true)
  List<QryAttrValueProjection> findAttrValueListByBaseAttrIds(@Param("baseAttrIds") Integer baseAttrIds);


  @Query(value = """
    SELECT 
        A.BASE_ATTR_ID AS baseAttrId,
        A.ATTR_VALUE_ID AS attrValueId,
        A.VALUE_MARK AS valueMark,
        A.VALUE AS value,
        A.PARENT_ATTR_VALUE_ID AS parentAttrValueId,
        A.PARENT_ATTR_ID AS parentAttrId,
        B.ATTR_NAME AS attrName
    FROM ATTR_VALUE A
    JOIN ATTR B ON A.BASE_ATTR_ID = B.ATTR_ID
    WHERE (:baseAttrId IS NULL OR A.BASE_ATTR_ID = :baseAttrId)
      AND (B.ATTR_CODE IS NOT NULL OR (B.ATTR_CODE IS NULL AND COALESCE(B.SP_ID, 0) = :spId))
    ORDER BY A.ATTR_VALUE_ID
""", nativeQuery = true)
  List<QryAttrValueProjection> QryAttrValue(
          @Param("baseAttrId") Integer baseAttrId,
          @Param("spId") Integer spId);

}
