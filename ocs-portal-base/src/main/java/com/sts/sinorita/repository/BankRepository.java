package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Bank;
import com.sts.sinorita.projection.accountConfig.QryBankProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Integer> {
    boolean existsByBankNameAndSpId(String bankName, Integer spId);

    boolean existsByBankCodeAndSpId(String bankCode, Integer spId);

    @Query(value = "SELECT COALESCE(MAX(B.BANK_ID) + 1, 1) FROM BANK B", nativeQuery = true)
    Integer getNextBankId();

    boolean existsByBankNameAndSpIdAndBankIdNot(String bankName, Integer spId, Integer bankId);

    boolean existsByBankCodeAndSpIdAndBankIdNot(String bankCode, Integer spId, Integer bankId);

    @Modifying
    @Query(value = "UPDATE BANK SET STATE = :state, STATE_DATE = :stateDate WHERE BANK_ID = :bankId", nativeQuery = true)
    void updateBankState(@Param("bankId") Integer bankId,
                         @Param("state") Character state,
                         @Param("stateDate") LocalDate stateDate);

//    @Query(value = """
//        SELECT COUNT(*)
//        FROM BANK A
//        WHERE A.STATE = :state
//          AND A.PARENT_ID = :bankId
//        """, nativeQuery = true)
//    Boolean countChildBank(@Param("state") Character state, @Param("bankId") Integer bankId);

    @Query(value = """
        SELECT CASE\s
                 WHEN COUNT(*) = 0 THEN 0
                 WHEN SUM(CASE WHEN STATE = 'A' THEN 1 ELSE 0 END) = 0 THEN 1
                 ELSE 0\s
               END AS RESULT
        FROM BANK\s
        CONNECT BY PARENT_ID = PRIOR BANK_ID\s
        START WITH PARENT_ID = :bankId
    """, nativeQuery = true)
    Integer hasAllXChildBank(@Param("bankId") Integer bankId);

    @Modifying
    @Query(value = "UPDATE SEPA_BANK SET SEPA_STATE = 'C' WHERE BANK_ID = :bankId", nativeQuery = true)
    void updateSepaBankStateC(@Param("bankId") Integer bankId);

    @Query(value = """
        SELECT COUNT(*) 
        FROM BANK A
        WHERE A.STATE = :state
          AND A.PARENT_ID = :bankId
        """, nativeQuery = true)
    Integer countChildBank(@Param("bankId") Integer bankId, @Param("state") char state);


    @Query(value = """
        SELECT 
            A.BANK_ID AS bankId,
            A.PARENT_ID AS parentId,
            A.BANK_NAME AS bankName,
            A.COMMENTS AS comments,
            A.BANK_CODE AS bankCode,
            A.STATE AS state,
            A.STATE_DATE AS stateDate,
            A.SP_ID AS spId,
            A.DIRECT_DEBIT_FLAG AS directDebitFlag,
            B.BIC AS bic,
            B.IBAN_FORMAT AS ibanFormat,
            (SELECT MAX(1) FROM BANK C WHERE C.PARENT_ID = A.BANK_ID) AS child
        FROM BANK A
        LEFT JOIN SEPA_BANK B ON A.BANK_ID = B.BANK_ID
        WHERE 
            A.STATE = 'A'
            AND (:bankId IS NULL OR A.BANK_ID = :bankId)
            AND (:onlyParent IS NULL OR :onlyParent <> '1' OR EXISTS (SELECT 1 FROM BANK C WHERE C.PARENT_ID = A.BANK_ID))
            AND (:bankCode IS NULL OR A.BANK_CODE = :bankCode)
            AND (:parentId IS NULL OR A.PARENT_ID = :parentId)
            AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId OR A.SP_ID IS NULL)
            AND (:bankName IS NULL OR A.BANK_NAME LIKE '%' || :bankName || '%')
            AND (:directFlag IS NULL OR ('1' = :directFlag AND (A.DIRECT_DEBIT_FLAG IS NULL OR A.DIRECT_DEBIT_FLAG = 'Y')))
            AND (:parentFlag IS NULL OR ('2' = :parentFlag AND A.PARENT_ID IS NULL))
            AND (:countryCode IS NULL OR COUNTRY_CODE = :countryCode)
        ORDER BY A.BANK_NAME
        """, nativeQuery = true)
    List<QryBankProjection> qryBank(
            @Param("bankId") Integer bankId,
            @Param("onlyParent") String onlyParent,
            @Param("bankCode") String bankCode,
            @Param("parentId") Integer parentId,
            @Param("spId") Integer spId,
            @Param("bankName") String bankName,
            @Param("directFlag") String directFlag,
            @Param("parentFlag") String parentFlag,
            @Param("countryCode") String countryCode
    );
}
