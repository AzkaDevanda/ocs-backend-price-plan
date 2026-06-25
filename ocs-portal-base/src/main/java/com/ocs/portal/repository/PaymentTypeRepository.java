package com.ocs.portal.repository;

import com.ocs.portal.entity.PaymentType;
import com.ocs.portal.projection.acct.PaymentTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Character> {
    @Query(value = """
        SELECT DISTINCT A.PAYMENT_TYPE AS paymentType,
                        A.PAYMENT_TYPE_NAME AS paymentTypeName,
                        A.COMMENTS AS comments
        FROM PAYMENT_TYPE A
        """, nativeQuery = true)
    List<PaymentTypeProjection> findByPaymentTypeNative();
}
