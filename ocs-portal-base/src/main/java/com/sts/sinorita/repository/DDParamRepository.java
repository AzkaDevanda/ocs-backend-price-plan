package com.sts.sinorita.repository;

import com.sts.sinorita.entity.DDParam;
import com.sts.sinorita.projection.accountConfig.QryDDParamProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DDParamRepository extends JpaRepository<DDParam, Integer> {

    @Query(value = """
        SELECT 
            d.PAYMENT_METHOD_ID AS paymentMethodId,
            d.DAYS_BEF_EXTRA AS daysBefExtra,
            d.SP_IBAN AS spIban,
            d.REISSUE_DELAY AS reIssueDelay,
            d.CLOSE_MANDATE_LIMIT AS closeMandateLimit
        FROM DD_PARAM d
        WHERE (d.PAYMENT_METHOD_ID = :paymentMethodId)
        """, nativeQuery = true)
    List<QryDDParamProjection> listDDParam(@Param("paymentMethodId") Integer paymentMethodId);
}
