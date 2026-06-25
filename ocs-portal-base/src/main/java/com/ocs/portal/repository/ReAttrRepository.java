package com.ocs.portal.repository;

import com.ocs.portal.dto.request.ReAttrDto;
import com.ocs.portal.dto.request.ReAttrListDto;
import com.ocs.portal.entity.ReAttr;
import com.ocs.portal.projection.pricePlan.QryReAttrByReAttrTypeProjection;
import com.ocs.portal.projection.trigger.ReAttrProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReAttrRepository extends JpaRepository<ReAttr, Integer> {
    @Query("SELECT new com.ocs.portal.dto.request.ReAttrDto(r.id, r.reAttrName) FROM ReAttr r ORDER BY r.reAttrName ASC")
    List<ReAttrDto> findByReAttrName();

    @Query("SELECT new com.ocs.portal.dto.request.ReAttrListDto(a.id, a.reAttrName) " +
            "FROM ReAttr a LEFT JOIN DefReAttr b ON a.id = b.reAttr " +
            "WHERE a.measurable = 'Y' " +
            "ORDER BY a.reAttrName")
    List<ReAttrListDto> findReAttrList();

    @Query("""
            SELECT
            	A.id AS reAttrId,
            	A.reAttrName AS reAttrName,
            	A.comments AS comments
            FROM
            	ReAttr A
            WHERE
            	(A.reType = :RE_TYPE
            		OR A.reType IS NULL)
            	AND (:SP_ID IS NULL OR COALESCE(A.spId, 0) = :SP_ID
            		OR -1 = :SP_ID)
                AND (:RE_ATTR_NAME IS NULL OR UPPER(A.reAttrName) LIKE CONCAT('%', UPPER(:RE_ATTR_NAME), '%'))
            ORDER BY
            	A.reAttrName
            """)
    List<QryReAttrByReAttrTypeProjection> qryReAttrByReAttrType(@Param("RE_TYPE") Character reType, @Param("RE_ATTR_NAME") String reAttrName, @Param("SP_ID") Integer spId);


    @Query(value = """
        SELECT A.RE_ATTR AS reAttr,
               A.RE_TYPE AS reType,
               A.RE_ATTR_NAME AS reAttrName,
               A.COMMENTS AS comments,
               A.MEASURABLE AS measurable,
               A.RE_ATTR_SRC_TYPE AS reAttrSrcType
          FROM RE_ATTR A
         WHERE 1 = 1
           AND (:reAttrSrcType IS NULL OR A.RE_ATTR_SRC_TYPE = :reAttrSrcType)
           AND (NVL(A.SP_ID, 0) = :spId OR -1 = :spId)
         ORDER BY A.RE_ATTR_NAME
        """, nativeQuery = true)
    List<ReAttrProjection> qryReAttrList(@Param("reAttrSrcType") String reAttrSrcType,
                                         @Param("spId") Long spId);


}
