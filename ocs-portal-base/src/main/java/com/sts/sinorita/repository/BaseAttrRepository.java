package com.sts.sinorita.repository;

import java.util.List;
import java.util.Optional;

import com.sts.sinorita.projection.accountConfig.QryAttrDetailProjection;
import com.sts.sinorita.projection.accountConfig.QryBaseAttrListByPKProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.BaseAttr;
import com.sts.sinorita.projection.baseattr.AttrDetailProjection;

@Repository
public interface BaseAttrRepository extends JpaRepository<BaseAttr, Integer> {
  @Query(value = """
      SELECT
        C.BASE_ATTR_ID AS baseAttrId,
        C.INPUT_TYPE AS inputType,
        C.NULLABLE AS nullable,
        C.COMMENTS AS comments,
        C.DEFAULT_VALUE AS defaultValue,
        C.VALUE_SCRIPT AS valueScript,
        E.TEXT_ATTR_ID AS textAttrId,
        E.DATA_TYPE AS dataType,
        E.MASK AS mask,
        E.RULE_SCRIPT AS ruleScript,
        E.EDITABLE AS editable,
        E.EXCEPTION_MESSAGE AS exceptionMessage,
        E.MIN_VALUE AS minValue,
        E.MAX_VALUE AS maxValue
      FROM BASE_ATTR C, ATTR A, TEXT_ATTR E
      WHERE C.BASE_ATTR_ID = E.TEXT_ATTR_ID
        AND C.BASE_ATTR_ID = A.ATTR_ID
        AND C.BASE_ATTR_ID = :BASE_ATTR_ID
        AND (A.ATTR_CODE IS NOT NULL OR (A.ATTR_CODE IS NULL AND NVL(A.SP_ID,0) = :SP_ID))
      """, nativeQuery = true)
  Optional<AttrDetailProjection> findAttrDetail(@Param("BASE_ATTR_ID") Integer baseAttrId, @Param("SP_ID") Integer spId);

//  @Query(value = """
//        SELECT A.BASE_ATTR_ID AS attrId,
//               B.ATTR_TYPE AS attrType,
//               B.ATTR_NAME AS attrName,
//               B.ATTR_CODE AS attrCode,
//               B.CSR_VISIBLE AS csrVisible,
//               A.INPUT_TYPE AS inputType,
//               A.NULLABLE AS nullable,
//               A.COMMENTS AS comments,
//               B.SP_ID AS spId,
//               B.ATTR_CODE AS attrCode,
//               A.DEFAULT_VALUE AS defaultValue,
//               A.VALUE_SCRIPT AS valueScript,
//               C.INPUT_TYPE_NAME AS inputTypeName,
//               D.DATA_TYPE AS dataType,
//               D.MASK AS mask,
//               D.RULE_SCRIPT AS ruleScript,
//               D.EDITABLE AS editable,
//               D.EXCEPTION_MESSAGE AS exceptionMessage,
//               D.MIN_VALUE AS minValue,
//               D.MAX_VALUE AS maxValue,
//               E.DATA_TYPE_NAME AS dataTypeName
//          FROM BASE_ATTR A
//          JOIN ATTR B ON A.BASE_ATTR_ID = B.ATTR_ID
//          JOIN INPUT_TYPE C ON A.INPUT_TYPE = C.INPUT_TYPE
//          LEFT JOIN TEXT_ATTR D ON A.BASE_ATTR_ID = D.TEXT_ATTR_ID
//          LEFT JOIN DATA_TYPE E ON D.DATA_TYPE = E.DATA_TYPE
//         WHERE A.BASE_ATTR_ID IN (:baseAttrIds)
//         ORDER BY A.BASE_ATTR_ID
//        """, nativeQuery = true)
//  List<QryBaseAttrListByPKProjection> QryBaseAttrListByPK(@Param("baseAttrIds") Integer baseAttrIds);


  @Query(value = """
    SELECT 
        C.BASE_ATTR_ID AS baseAttrId,
        C.INPUT_TYPE AS inputType,
        C.NULLABLE AS nullable,
        C.COMMENTS AS comments,
        C.DEFAULT_VALUE AS defaultValue,
        C.VALUE_SCRIPT AS valueScript,
        E.TEXT_ATTR_ID AS textAttrId,
        E.DATA_TYPE AS dataType,
        E.MASK AS mask,
        E.RULE_SCRIPT AS ruleScript,
        E.EDITABLE AS editable,
        E.EXCEPTION_MESSAGE AS exceptionMessage,
        E.MIN_VALUE AS minValue,
        E.MAX_VALUE AS maxValue
    FROM BASE_ATTR C
        LEFT JOIN TEXT_ATTR E ON C.BASE_ATTR_ID = E.TEXT_ATTR_ID
        LEFT JOIN ATTR A ON C.BASE_ATTR_ID = A.ATTR_ID
    WHERE 
        C.BASE_ATTR_ID = :baseAttrId
        AND (A.ATTR_CODE IS NOT NULL OR (A.ATTR_CODE IS NULL AND NVL(A.SP_ID,0) = :spId))
    """, nativeQuery = true)
  List<QryAttrDetailProjection> qryAttrDetail(
          @Param("baseAttrId") Integer baseAttrId,
          @Param("spId") Integer spId
  );

//  @Query(value = """
//    SELECT
//        A.BASE_ATTR_ID AS attrId,
//        B.ATTR_NAME AS attrName,
//        B.ATTR_CODE AS attrCode,
//        B.CSR_VISIBLE AS csrVisible,
//        A.INPUT_TYPE AS inputType,
//        A.NULLABLE AS nullable,
//        A.COMMENTS AS comments,
//        A.DEFAULT_VALUE AS defaultValue,
//        A.VALUE_SCRIPT AS valueScript,
//        C.INPUT_TYPE_NAME AS inputTypeName,
//        D.DATA_TYPE AS dataType,
//        D.MASK AS mask,
//        D.RULE_SCRIPT AS ruleScript,
//        D.EDITABLE AS editable,
//        D.EXCEPTION_MESSAGE AS exceptionMessage,
//        D.MIN_VALUE AS minValue,
//        D.MAX_VALUE AS maxValue,
//        E.DATA_TYPE_NAME AS dataTypeName
//    FROM BASE_ATTR A
//    JOIN ATTR B ON A.BASE_ATTR_ID = B.ATTR_ID
//    JOIN INPUT_TYPE C ON A.INPUT_TYPE = C.INPUT_TYPE
//    LEFT JOIN TEXT_ATTR D ON A.BASE_ATTR_ID = D.TEXT_ATTR_ID
//    LEFT JOIN DATA_TYPE E ON D.DATA_TYPE = E.DATA_TYPE
//    WHERE (:baseAttrIds IS NULL OR A.BASE_ATTR_ID IN (:baseAttrIds))
//    ORDER BY A.BASE_ATTR_ID
//""", nativeQuery = true)
//  List<QryBaseAttrListByPKProjection> qryBaseAttrListByPK(@Param("baseAttrIds") List<Integer> baseAttrIds);

  @Query(value = """
    SELECT 
        A.BASE_ATTR_ID AS attrId,
        B.ATTR_NAME AS attrName,
        B.ATTR_CODE AS attrCode,
        B.CSR_VISIBLE AS csrVisible,
        A.INPUT_TYPE AS inputType,
        A.NULLABLE AS nullable,
        A.COMMENTS AS comments,
        A.DEFAULT_VALUE AS defaultValue
    FROM BASE_ATTR A
    JOIN ATTR B ON A.BASE_ATTR_ID = B.ATTR_ID
    WHERE A.BASE_ATTR_ID IN (:baseAttrIds)
    ORDER BY A.BASE_ATTR_ID
""", nativeQuery = true)
  List<QryBaseAttrListByPKProjection> qryBaseAttrListByPK(@Param("baseAttrIds") List<Integer> baseAttrIds);

}
