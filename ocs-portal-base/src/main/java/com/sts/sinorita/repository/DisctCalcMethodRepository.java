package com.sts.sinorita.repository;

import com.sts.sinorita.entity.DisctCalcMethod;
import com.sts.sinorita.projection.pricePlan.discount.QryDisctCalcMethodProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisctCalcMethodRepository extends JpaRepository<DisctCalcMethod, Character> {

    @Query(value = """
        SELECT T.DISCT_CALC_METHOD as disctCalcMethod, T.DISCT_CALC_METHOD_NAME as disctCalcMethodName, T.COMMENTS as comments FROM DISCT_CALC_METHOD T
        """,nativeQuery = true)
    List<QryDisctCalcMethodProjection> qryDisctCalcMethod();
}
