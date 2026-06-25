package com.ocs.portal.repository;

import com.ocs.portal.entity.AllServType;
import com.ocs.portal.projection.balanceAdjustment.SelectAllServTypeProjection;
import com.ocs.portal.projection.pricePlan.price.QryServTypeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllServTypeRepository extends JpaRepository<AllServType, Integer> {
        @Query(value = "SELECT ast.SERV_TYPE , ast.SERV_TYPE_NAME, ast.NETWORK_TYPE, nt.NETWORK_TYPE_NAME FROM ALL_SERV_TYPE ast JOIN NETWORK_TYPE nt ON nt.NETWORK_TYPE = ast.NETWORK_TYPE", nativeQuery = true)
        List<Object[]> getServiceType();

        @Query(value = "SELECT A.servType as servType, A.servTypeName as servTypeName, A.networkType as networkType, A.catgType as catgType, A.paidFlag as paidFlag, A.stdCode as stdCode, B.networkTypeName as networkTypeName FROM AllServType A, NetworkType B WHERE A.networkType = B.networkType AND (:servTypeName IS NULL OR A.servTypeName LIKE '%' || :servTypeName || '%') ORDER BY A.servTypeName")
        Page<QryServTypeProjection> qryServType(@Param("servTypeName") String servTypeName, Pageable pageable);

        @Query(value = "SELECT SERV_TYPE AS servType, NETWORK_TYPE AS networkType, SERV_TYPE_NAME AS servTypeName, CATG_TYPE AS catgType, COMMENTS AS comments, PAID_FLAG AS paidFlag, STD_CODE AS stdCode FROM ALL_SERV_TYPE WHERE SERV_TYPE = :servType", nativeQuery = true)
        Optional<SelectAllServTypeProjection> selectAllServType(@Param("servType") Integer servType);
}
