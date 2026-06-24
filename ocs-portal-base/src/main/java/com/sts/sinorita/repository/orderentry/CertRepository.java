package com.sts.sinorita.repository.orderentry;

import com.sts.sinorita.entity.CertEntity;
import com.sts.sinorita.entity.CustEntity;
import com.sts.sinorita.projection.orderentry.CertProjection;
import com.sts.sinorita.projection.orderentry.CustomerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertRepository extends JpaRepository<CertEntity,Integer> {

//    AND ct.CERT_TYPE_ID = :CERT_TYPE_ID
//    AND (ct.CUST_TYPE IS NULL OR ct.CUST_TYPE = :CUST_TYPE)
//    AND ct.CERT_TYPE_NAME = :CERT_TYPE_NAME
    @Query(value = """
            SELECT
                ct.CERT_TYPE_ID        AS certTypeId,
                ct.CERT_TYPE_NAME      AS certTypeName,
                ct.COMMENTS            AS comments,
                ct.NBR_MASK            AS nbrMask,
                ct.MAX_NBR_LENGTH      AS maxNbrLength,
                ct.MIN_NBR_LENGTH      AS minNbrLength,
                ct.CUST_TYPE           AS custType,
                ct.SP_ID               AS spId,
                ct.NBR_EXAMPLE         AS nbrExample,
                cust.CUST_TYPE_NAME    AS custTypeName,
                ct.CERT_TYPE_CODE      AS certTypeCode
            FROM
                CERT_TYPE ct
            LEFT JOIN
                CUST_TYPE cust
                ON ct.CUST_TYPE = cust.CUST_TYPE
            WHERE
                1 = 1 
                AND NVL(ct.SP_ID, 0) = :SP_ID
            ORDER BY
                ct.CERT_TYPE_NAME;
            """, nativeQuery = true)
    public List<CertProjection> getListCert(@Param("SP_ID")Integer SP_ID);


}
