package com.sts.sinorita.repository;

import com.sts.sinorita.entity.PaymentMethod;
import com.sts.sinorita.projection.acct.PaymentMethodProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    @Query(value = """
        SELECT DISTINCT 
            T.PAYMENT_METHOD_ID as paymentMethodId,
            T.PAYMENT_METHOD_NAME as paymentMethodName,
            T.COMMENTS as comments,
            T.PAYMENT_TYPE as paymentType,
            B.PAYMENT_TYPE_NAME as paymentTypeName,
            T.SP_ID as spId,
            T.PAYMENT_METHOD_CODE as paymentMethodCode,
            T.SYSTEM_RESERVED as systemReserved,
            T.GROUP_TYPE as groupType    
        FROM PAYMENT_METHOD T
        LEFT JOIN PAYMENT_TYPE B ON T.PAYMENT_TYPE = B.PAYMENT_TYPE
        WHERE (:paymentMethodId IS NULL OR T.PAYMENT_METHOD_ID = :paymentMethodId)
          AND (:paymentType IS NULL OR T.PAYMENT_TYPE = :paymentType)
          AND (
              :spId IS NULL 
              OR T.PAYMENT_METHOD_ID BETWEEN 1 AND 10 
              OR NVL(T.SP_ID, 0) = :spId
          )
        ORDER BY T.PAYMENT_METHOD_ID
        """, nativeQuery = true)
    Page<PaymentMethodProjection> findPaymentMethods(
            @Param("paymentMethodId") Integer paymentMethodId,
            @Param("paymentType") Character paymentType,
            @Param("spId") Integer spId,
            Pageable pageable
    );

    @Query(value = "SELECT COALESCE(MAX(P.PAYMENT_METHOD_ID) + 1, 1) FROM PAYMENT_METHOD P", nativeQuery = true)
    Integer getNextPaymentMethodId();

    boolean existsByPaymentMethodNameIgnoreCase(String paymentMethodName);

    boolean existsByPaymentMethodNameAndSpId(String paymentMethodName, Integer spId);

    boolean existsByPaymentMethodNameAndSpIdAndPaymentMethodIdNot(String paymentMethodName, Integer spId, Integer paymentMethodId);

    @Query("SELECT COUNT(a) FROM PaymentMethod a WHERE a.paymentMethodName = :paymentMethodName AND a.paymentMethodId <> :paymentMethodId")
    int checksSamePaymentMethodName(@Param("paymentMethodName") String paymentMethodName, @Param("paymentMethodId") Integer paymentMethodId);


}
