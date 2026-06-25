package com.ocs.portal.repository;

import com.ocs.portal.entity.CdrTemplate;
import com.ocs.portal.projection.balanceAdjustment.SelectCdrTemplateProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CdrTemplateRepository extends JpaRepository<CdrTemplate, Integer> {
  @Query(
    value = """
          SELECT
              CDR_TEMPLATE_ID   AS cdrTemplateId,
              CDR_TEMPLATE_NAME AS cdrTemplateName,
              CDR_CONTENT       AS cdrContent,
              COMMENTS          AS comments
          FROM CDR_TEMPLATE
          WHERE CDR_TEMPLATE_ID = :cdrTemplateId
      """,
    nativeQuery = true
  )
  Optional<SelectCdrTemplateProjection> selectCdrTemplate (@Param("cdrTemplateId") Long cdrTemplateId);
}

