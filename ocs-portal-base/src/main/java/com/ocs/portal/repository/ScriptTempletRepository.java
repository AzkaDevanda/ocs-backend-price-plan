package com.ocs.portal.repository;

import com.ocs.portal.entity.ScriptTemplet;
import com.ocs.portal.projection.pricePlan.rateplan.QryScriptTemplateProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScriptTempletRepository extends JpaRepository<ScriptTemplet, Integer> {

    @Query(value = "select st.SCRIPT_TEMPLET_ID, st.SCRIPT_TEMPLET_NAME from script_templet st ", nativeQuery = true)
    List<Object[]> getScriptTemplet();

    @Query(value = "SELECT S FROM ScriptTemplet S where S.id = :id")
    public Optional<ScriptTemplet> findScriptTempletByScriptTempletId(@Param("id")Integer id);


    @Query(
            value = """
        SELECT 
            A.SCRIPT_TEMPLET_ID        AS scriptTempletId,
            A.SCRIPT_TEMPLET_NAME      AS scriptTempletName,
            A.COMMENTS                AS comments,
            A.SCRIPT_TEMPLET_GROUP     AS scriptTempletGroup,
            TO_CHAR(A.TEMPLET_CONTENT) AS templetContent,
            A.TEMPLATE_FLAG            AS templateFlag,
            A.SRC_SCRIPT_TEMPLATE_ID   AS srcScriptTemplateId
        FROM SCRIPT_TEMPLET A
        JOIN SCRIPT_TEMPLET_CATALOG_ELEMENT B 
            ON A.SCRIPT_TEMPLET_ID = B.SCRIPT_TEMPLET_ID
        JOIN TARIFF_TEMPLATE_CATALOG C 
            ON B.CATALOG_ID = C.CATALOG_ID
        WHERE 1=1
          AND (:scriptTempletId IS NULL OR A.SCRIPT_TEMPLET_ID = :scriptTempletId)
          AND (:scriptTempletGroup IS NULL OR A.SCRIPT_TEMPLET_GROUP = :scriptTempletGroup)
          AND (:usageType IS NULL OR C.USAGE_TYPE = :usageType)
          AND (:spId IS NULL OR NVL(A.SP_ID,0) = :spId)
          AND (:spId IS NULL OR NVL(B.SP_ID,0) = :spId)
          AND (:spId IS NULL OR NVL(C.SP_ID,0) = :spId)
        """,
            nativeQuery = true)
    List<QryScriptTemplateProjection> QryScriptTemplate(
            @Param("scriptTempletId") Long scriptTempletId,
            @Param("scriptTempletGroup") String scriptTempletGroup,
            @Param("usageType") String usageType,
            @Param("spId") Long spId
    );
}
