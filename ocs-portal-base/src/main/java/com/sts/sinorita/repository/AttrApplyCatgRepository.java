package com.sts.sinorita.repository;

import com.sts.sinorita.entity.AttrApplyCatg;
import com.sts.sinorita.entity.AttrApplyCatgId;
import com.sts.sinorita.projection.offer.commonoffer.AttrApplyCatgProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AttrApplyCatgRepository extends JpaRepository<AttrApplyCatg, AttrApplyCatgId> {
    @Query(value = """
        SELECT ATTR_ID AS attrApplyCatgId,
               ATTR_CATG AS attrApplyCatgName,
               SP_ID AS spId
        FROM ATTR_APPLY_CATG
        WHERE ATTR_ID = :attrApplyCatgId
          AND NVL(SP_ID, 0) = 0
        """, nativeQuery = true)
    List<AttrApplyCatgProjection> findAttrApplyCatg(@Param("attrApplyCatgId") Integer attrApplyCatgId);
}
