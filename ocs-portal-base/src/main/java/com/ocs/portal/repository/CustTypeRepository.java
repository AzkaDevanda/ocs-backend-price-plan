package com.ocs.portal.repository;

import com.ocs.portal.entity.CustTypeEntity;
import com.ocs.portal.projection.acct.CustTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustTypeRepository extends JpaRepository<CustTypeEntity,Character> {

    @Query(value = """
        SELECT A.CUST_TYPE         AS custType,
               A.CUST_TYPE_NAME    AS custTypeName,
               A.COMMENTS          AS comments
          FROM CUST_TYPE A
         WHERE (:custType IS NULL OR A.CUST_TYPE = :custType)
        """, nativeQuery = true)
    List<CustTypeProjection> findCusType(@Param("custType") Character custType);
}
