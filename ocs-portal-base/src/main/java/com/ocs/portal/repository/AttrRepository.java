package com.ocs.portal.repository;

import com.ocs.portal.entity.Attr;
import com.ocs.portal.projection.accountConfig.QryAttrListByCatgProjection;
import com.ocs.portal.projection.attr.AttrListByCatgProjection;
import com.ocs.portal.projection.attr.SelectAttrByCodeProjection;
import com.ocs.portal.projection.balanceAdjustment.GetAttrByCodeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttrRepository extends JpaRepository<Attr, Integer> {
  @Query(value = """
        SELECT distinct
          ATTR_ID         AS attrId,
          ATTR_NAME       AS attrName,
          ATTR_TYPE       AS attrType,
          OBJ_ATTR_ID     AS objAttrId,
          ATTR_CODE       AS attrCode,
          CSR_VISIBLE     AS csrVisible,
          INSTANTIATABLE  AS instantiatable,
          CONFIG_VISIBLE  AS configVisible,
          EDITABLE        AS editable
        FROM (SELECT
                A.ATTR_ID,
                A.ATTR_NAME,
                A.ATTR_TYPE,
                A.OBJ_ATTR_ID,
                A.ATTR_CODE,
                A.CSR_VISIBLE,
                A.CONFIG_VISIBLE,
                A.INSTANTIATABLE,
                A.EDITABLE
              FROM ATTR A, ATTR_APPLY_CATG G
              WHERE A.ATTR_ID = G.ATTR_ID
                AND G.ATTR_CATG = :ATTR_CATG

              UNION ALL

              SELECT
                A.ATTR_ID,
                A.ATTR_NAME,
                A.ATTR_TYPE,
                A.OBJ_ATTR_ID,
                A.ATTR_CODE,
                A.CSR_VISIBLE,
                A.CONFIG_VISIBLE,
                A.INSTANTIATABLE,
                A.EDITABLE
              FROM ATTR A WHERE 1 = 1
          )
      """, countQuery = """
        SELECT COUNT(*) FROM (
          SELECT A.ATTR_ID FROM ATTR A
          JOIN ATTR_APPLY_CATG G ON A.ATTR_ID = G.ATTR_ID
          WHERE G.ATTR_CATG = :ATTR_CATG

          UNION

          SELECT A.ATTR_ID FROM ATTR A
        ) x
      """, nativeQuery = true)
  Page<AttrListByCatgProjection> findAttrListByCatg(@Param("ATTR_CATG") Integer attrCatg, Pageable pageable);

  @Query(value = "select a from Attr a where a.attrCode =:attrCode")
  Optional<Attr> findByCode(@Param("attrCode") String attrCode);

  @Query(value = """
          SELECT DISTINCT
              ATTR_ID AS attrId,
              ATTR_NAME AS attrName,
              ATTR_TYPE AS attrType,
              OBJ_ATTR_ID AS objAttrId,
              ATTR_CODE AS attrCode,
              CSR_VISIBLE AS csrVisible,
              INSTANTIATABLE AS instantiatable,
              CONFIG_VISIBLE AS configVisible,
              EDITABLE AS editable
          FROM (
              SELECT
                  A.ATTR_ID,
                  A.ATTR_NAME,
                  A.ATTR_TYPE,
                  A.OBJ_ATTR_ID,
                  A.ATTR_CODE,
                  A.CSR_VISIBLE,
                  A.CONFIG_VISIBLE,
                  A.INSTANTIATABLE,
                  A.EDITABLE
              FROM ATTR A
              JOIN ATTR_APPLY_CATG G ON A.ATTR_ID = G.ATTR_ID
              WHERE (:attrName IS NULL OR UPPER(A.ATTR_NAME) LIKE '%' || UPPER(:attrName) || '%')

              UNION ALL

              SELECT
                  A.ATTR_ID,
                  A.ATTR_NAME,
                  A.ATTR_TYPE,
                  A.OBJ_ATTR_ID,
                  A.ATTR_CODE,
                  A.CSR_VISIBLE,
                  A.CONFIG_VISIBLE,
                  A.INSTANTIATABLE,
                  A.EDITABLE
              FROM ATTR A
              WHERE A.ATTR_ID NOT IN (SELECT ATTR_ID FROM ATTR_APPLY_CATG)
                AND (:attrName IS NULL OR UPPER(A.ATTR_NAME) LIKE '%' || UPPER(:attrName) || '%')
          ) sub
          ORDER BY ATTR_NAME
      """, countQuery = """
          SELECT COUNT(1)
          FROM (
              SELECT A.ATTR_ID
              FROM ATTR A
              JOIN ATTR_APPLY_CATG G ON A.ATTR_ID = G.ATTR_ID
              WHERE (:attrName IS NULL OR UPPER(A.ATTR_NAME) LIKE '%' || UPPER(:attrName) || '%')

              UNION ALL

              SELECT A.ATTR_ID
              FROM ATTR A
              WHERE A.ATTR_ID NOT IN (SELECT ATTR_ID FROM ATTR_APPLY_CATG)
                AND (:attrName IS NULL OR UPPER(A.ATTR_NAME) LIKE '%' || UPPER(:attrName) || '%')
          )
      """, nativeQuery = true)
  Page<QryAttrListByCatgProjection> QryAttrListByCatg(Pageable pageable, @Param("attrName") String attrName);

  @Query(value = """
      SELECT ATTR_ID AS attrId, ATTR_NAME AS attrName, ATTR_TYPE AS attrType, OBJ_ATTR_ID AS objAttrId, ATTR_CODE AS attrCode, CSR_VISIBLE AS csrVisible, INSTANTIATABLE AS instantiatable, SP_ID AS spId, CONFIG_VISIBLE AS configVisible FROM ATTR WHERE ATTR_CODE = :attrCode
      """, nativeQuery = true)
  Optional<GetAttrByCodeProjection> getAttrByCode(@Param("attrCode") String attrCode);

  @Query(value = "SELECT ATTR_ID AS attrId, ATTR_NAME AS attrName, ATTR_TYPE AS attrType, OBJ_ATTR_ID AS objAttrId, ATTR_CODE AS attrCode, CSR_VISIBLE AS csrVisible, INSTANTIATABLE AS instantiatable, SP_ID AS spId, CONFIG_VISIBLE AS configVisible FROM ATTR WHERE ATTR_CODE = :attrCode", nativeQuery = true)
  Optional<SelectAttrByCodeProjection> selectAttrByCode(@Param("attrCode") String attrCode);
}
