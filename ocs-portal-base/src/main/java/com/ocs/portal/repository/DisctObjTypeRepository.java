package com.ocs.portal.repository;

import com.ocs.portal.entity.DisctObjType;
import com.ocs.portal.projection.pricePlan.discount.QryDisctObjTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisctObjTypeRepository extends JpaRepository<DisctObjType, Character> {
    @Query(value = """
        SELECT A.DISCT_OBJ_TYPE as disctObjType, A.DISCT_OBJ_TYPE_NAME as disctObjTypeName FROM DISCT_OBJ_TYPE A
        """, nativeQuery = true)
    List<QryDisctObjTypeProjection> qryDisctObjType();
}
