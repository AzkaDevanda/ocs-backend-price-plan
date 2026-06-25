package com.ocs.portal.repository;

import com.ocs.portal.entity.DepositType;
import com.ocs.portal.projection.accountConfig.QryDepositType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepositTypeRepository extends JpaRepository<DepositType,Integer> {

    @Query(value = "SELECT NVL(MAX(DEPOSIT_TYPE_ID), 0) + 1 FROM DEPOSIT_TYPE", nativeQuery = true)
    Integer getNextDepositTypeId();

    Boolean existsByNameAndSpId(String name, Integer spId);

    Boolean existsByDepositTypeCodeAndSpId(String depositTypeCode, Integer spId);

    @Query(value = """
        SELECT 
            DEPOSIT_TYPE_ID AS depositTypeId,
            NAME AS name,
            COMMENTS AS comments,
            CHARGE AS charge,
            SP_ID AS spId,
            DEPOSIT_TYPE_CODE AS depositTypeCode,
            REFUNDABLE AS refundable,
            TRANS_CREDIT AS transCredit,
            CHECK_DURATION AS checkDuration
        FROM DEPOSIT_TYPE
        WHERE NVL(SP_ID, 0) = :spId
        """, nativeQuery = true)
    Page<QryDepositType> qryDepositType(Pageable pageable, @Param("spId") Integer spId);

    @Query(value = """
        SELECT COUNT(1) FROM DEPOSIT_TYPE 
        WHERE NAME = :name AND SP_ID = :spId 
        AND DEPOSIT_TYPE_ID <> :depositTypeId
    """, nativeQuery = true)
    int countByNameAndSpIdExcludingId(@Param("name") String name,
                                      @Param("spId") Integer spId,
                                      @Param("depositTypeId") Integer depositTypeId);

    @Query(value = """
        SELECT COUNT(1) FROM DEPOSIT_TYPE 
        WHERE DEPOSIT_TYPE_CODE = :code AND SP_ID = :spId 
        AND DEPOSIT_TYPE_ID <> :depositTypeId
    """, nativeQuery = true)
    int countByCodeAndSpIdExcludingId(@Param("code") String code,
                                      @Param("spId") Integer spId,
                                          @Param("depositTypeId") Integer depositTypeId);
}
