package com.sts.sinorita.repository;

import com.sts.sinorita.entity.RpzMappingSrcValue;
import com.sts.sinorita.projection.pricePlan.QueryEnumProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RpzMappingSrcValueRepository extends JpaRepository<RpzMappingSrcValue, String> {

    @Query("""
                SELECT
                    A.mappingSrcValue AS enumType,
                    A.mappingSrcValue AS enumTypeName
                FROM
                    RpzMappingSrcValue A
            """)
    List<QueryEnumProjection> queryEnum();
}
