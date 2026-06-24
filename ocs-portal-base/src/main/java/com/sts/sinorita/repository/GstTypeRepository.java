package com.sts.sinorita.repository;

import com.sts.sinorita.entity.GstType;
import com.sts.sinorita.projection.acct.GstTypeProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GstTypeRepository extends JpaRepository<GstType,Character> {
    @Query(value = """
    SELECT 
        A.GST_TYPE AS gstType,
        A.GST_TYPE_NAME AS gstTypeName,
        A.COMMENTS AS comments
    FROM GST_TYPE A
    ORDER BY A.GST_TYPE_NAME
    """, nativeQuery = true)
    List<GstTypeProject> findAllGstType();
}
