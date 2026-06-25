package com.ocs.portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.DynReAttr;
import com.ocs.portal.projection.SelectAttrCatgProjection;

@Repository
public interface DynReAttrRepository extends JpaRepository<DynReAttr, Integer> {
  @Query(value = """
      SELECT
          A.ATTR_CATG AS attrCatg,
          C.ATTR_ID   AS attrId
      FROM DYN_RE_ATTR A
      JOIN RE_ATTR B
          ON A.RE_ATTR = B.RE_ATTR
      JOIN ATTR C
          ON A.DYN_ATTR_ID = C.ATTR_ID
      WHERE B.RE_ATTR = :refAttr
      """, nativeQuery = true)
  Optional<SelectAttrCatgProjection> selectAttrCatg(
      @Param("refAttr") String refAttr);

}
