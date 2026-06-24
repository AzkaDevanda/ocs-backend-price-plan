package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmParams;
import com.sts.sinorita.svc.role.projection.ParamsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BfmParamsRepository extends JpaRepository<BfmParams,Integer> {

    @Query(value = """
            SELECT 
                PARAM as param, 
                PARAM_NAME as paramName, 
                CURRENT_VALUE as currentValue, 
                COMMENTS as comments, 
                MASK as mask
            FROM STS.BFM_PARAMS 
            WHERE MASK = :mask 
                    FETCH FIRST 1 ROWS ONLY 
            """, nativeQuery = true)
    Optional<ParamsProjection> querySysParamByMask(@Param("mask") String mask);


}

