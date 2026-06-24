package com.sts.sinorita.repository;

import com.sts.sinorita.dto.response.offerver.ServeTypeList;
import com.sts.sinorita.entity.AllServType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AllServeTypeRepository extends JpaRepository<AllServType, Integer> {
    @Query("SELECT new com.sts.sinorita.dto.response.offerver.ServeTypeList(" +
            "a.servType, a.servTypeName, a.networkType, a.catgType, " +
            "a.paidFlag, a.stdCode, b.networkTypeName) " +
            "FROM AllServType a JOIN NetworkType b ON a.networkType = b.networkType " +
            "WHERE a.catgType = :catgType " +
            "ORDER BY a.servTypeName")
    public List<ServeTypeList> getServeTypeList(@Param("catgType") Character catgType);


}
