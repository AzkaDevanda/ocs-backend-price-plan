package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Acct;
import com.sts.sinorita.projection.accountConfig.AcctBankInfoProjection;
import com.sts.sinorita.projection.acct.QryAcctInfoProjection;
import com.sts.sinorita.projection.acct.SelectAcctDtoByAcctIdProjection;

import jakarta.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcctRepository extends JpaRepository<Acct, Long> {

//    @Query(value = "SELECT STATE FROM ACCT WHERE BANK_ID = :bankId", nativeQuery = true)
//    List<String> findStatesByBankId(@Param("bankId") Integer bankId);

  @Query(value = """
    SELECT A.ACCT_ID, A.CUST_ID, A.ACCT_NBR, A.ACCT_NAME, A.BILLING_CYCLE_TYPE_ID, A.BILL_FORMAT_ID, A.POSTPAID, A.STD_ADDR_ID, A.BILL_ADDRESS, A.PAYMENT_TYPE, A.PAYMENT_METHOD_ID, A.BANK_ID, A.BANK_ACCT_NBR, A.BANK_ACCT_NAME, A.CREATED_DATE, A.UPDATE_DATE, A.STATE, A.STATE_DATE, B.BILLING_CYCLE_TYPE_NAME, A.DEFAULT_FLAG, E.PAYMENT_TYPE_NAME, F.BANK_NAME, G.CUST_NAME, I.CERT_NBR, J.CERT_TYPE_NAME, G.ADDRESS, 'N' AS IS_PROJECT, A.ROUTING_ID, K.PAYMENT_METHOD_NAME, A.PAYMENT_COMMENTS, A.BANK_CARD_TYPE, A.IS_LOCK, A.SP_ID, A.ALLOW_MOD_STATE_DATE, G.CUST_TYPE FROM ACCT A JOIN BILLING_CYCLE_TYPE B ON A.BILLING_CYCLE_TYPE_ID = B.BILLING_CYCLE_TYPE_ID JOIN PAYMENT_TYPE E ON A.PAYMENT_TYPE = E.PAYMENT_TYPE LEFT JOIN BANK F ON A.BANK_ID = F.BANK_ID JOIN CUST G ON A.CUST_ID = G.CUST_ID LEFT JOIN CERT I ON G.CERT_ID = I.CERT_ID LEFT JOIN CERT_TYPE J ON I.CERT_TYPE_ID = J.CERT_TYPE_ID LEFT JOIN PAYMENT_METHOD K ON A.PAYMENT_METHOD_ID = K.PAYMENT_METHOD_ID WHERE A.STATE = 'A' AND (:acctId IS NULL OR A.ACCT_ID = :acctId) AND (:acctNbr IS NULL OR A.ACCT_NBR = :acctNbr) AND ( :accNbr IS NULL OR :spId IS NULL OR A.ACCT_ID IN ( SELECT S.ACCT_ID FROM SUBS S JOIN PROD B ON S.SUBS_ID = B.PROD_ID JOIN PROD_STATE PS ON B.PROD_STATE = PS.PROD_STATE WHERE PS.PROD_STD_STATE NOT IN ('B') AND S.ACC_NBR = :accNbr AND NVL(S.SP_ID, 0) = :spId ) ) AND ( :custName IS NULL OR A.CUST_ID IN ( SELECT C.CUST_ID FROM CUST C WHERE UPPER(C.CUST_NAME) LIKE UPPER(:custName || '%') AND C.STATE = 'A' ) ) AND (:custId IS NULL OR A.CUST_ID = :custId) AND (:certNbrParam IS NULL OR I.CERT_NBR = :certNbrParam) AND ( :telNbr IS NULL OR :prefix IS NULL OR A.ACCT_ID IN ( SELECT S.ACCT_ID FROM SUBS S JOIN PROD B ON S.SUBS_ID = B.PROD_ID JOIN PROD_STATE PS ON B.PROD_STATE = PS.PROD_STATE WHERE PS.PROD_STD_STATE NOT IN ('B') AND S.ACC_NBR = :telNbr AND S.PREFIX = :prefix ) ) AND (:curAcctId IS NULL OR A.ACCT_ID <> :curAcctId) AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId) AND ( :isLock IS NULL OR A.IS_LOCK <> :isLock OR A.IS_LOCK IS NULL ) AND (:billingCycleTypeId IS NULL OR A.BILLING_CYCLE_TYPE_ID = :billingCycleTypeId) AND (:routingId IS NULL OR A.ROUTING_ID = :routingId) AND (:postpaid IS NULL OR A.POSTPAID = :postpaid) AND (:custType IS NULL OR G.CUST_TYPE = :custType) AND (:custCode IS NULL OR G.CUST_CODE = :custCode)
    """, countQuery = """
    SELECT COUNT(*) FROM ACCT A JOIN BILLING_CYCLE_TYPE B ON A.BILLING_CYCLE_TYPE_ID = B.BILLING_CYCLE_TYPE_ID JOIN PAYMENT_TYPE E ON A.PAYMENT_TYPE = E.PAYMENT_TYPE LEFT JOIN BANK F ON A.BANK_ID = F.BANK_ID JOIN CUST G ON A.CUST_ID = G.CUST_ID LEFT JOIN CERT I ON G.CERT_ID = I.CERT_ID LEFT JOIN CERT_TYPE J ON I.CERT_TYPE_ID = J.CERT_TYPE_ID LEFT JOIN PAYMENT_METHOD K ON A.PAYMENT_METHOD_ID = K.PAYMENT_METHOD_ID WHERE A.STATE = 'A' AND (:acctId IS NULL OR A.ACCT_ID = :acctId) AND (:acctNbr IS NULL OR A.ACCT_NBR = :acctNbr) AND ( :accNbr IS NULL OR :spId IS NULL OR A.ACCT_ID IN ( SELECT S.ACCT_ID FROM SUBS S JOIN PROD B ON S.SUBS_ID = B.PROD_ID JOIN PROD_STATE PS ON B.PROD_STATE = PS.PROD_STATE WHERE PS.PROD_STD_STATE NOT IN ('B') AND S.ACC_NBR = :accNbr AND NVL(S.SP_ID, 0) = :spId ) ) AND ( :custName IS NULL OR A.CUST_ID IN ( SELECT C.CUST_ID FROM CUST C WHERE UPPER(C.CUST_NAME) LIKE UPPER(:custName || '%') AND C.STATE = 'A' ) ) AND (:custId IS NULL OR A.CUST_ID = :custId) AND (:certNbrParam IS NULL OR I.CERT_NBR = :certNbrParam) AND ( :telNbr IS NULL OR :prefix IS NULL OR A.ACCT_ID IN ( SELECT S.ACCT_ID FROM SUBS S JOIN PROD B ON S.SUBS_ID = B.PROD_ID JOIN PROD_STATE PS ON B.PROD_STATE = PS.PROD_STATE WHERE PS.PROD_STD_STATE NOT IN ('B') AND S.ACC_NBR = :telNbr AND S.PREFIX = :prefix ) ) AND (:curAcctId IS NULL OR A.ACCT_ID <> :curAcctId) AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId) AND (:isLock IS NULL OR A.IS_LOCK <> :isLock OR A.IS_LOCK IS NULL) AND (:billingCycleTypeId IS NULL OR A.BILLING_CYCLE_TYPE_ID = :billingCycleTypeId) AND (:routingId IS NULL OR A.ROUTING_ID = :routingId) AND (:postpaid IS NULL OR A.POSTPAID = :postpaid) AND (:custType IS NULL OR G.CUST_TYPE = :custType) AND (:custCode IS NULL OR G.CUST_CODE = :custCode)
    """, nativeQuery = true)
  Page<QryAcctInfoProjection> qryAcctInfo (Pageable pageable, @Param("acctId") Long acctId, @Param("acctNbr") String acctNbr, @Param("accNbr") String accNbr, @Param("custName") String custName, @Param("custId") Long custId, @Param("certNbrParam") String certNbr, @Param("telNbr") String telNbr, @Param("prefix") String prefix, @Param("curAcctId") Long curAcctId, @Param("spId") Long spId, @Param("isLock") String isLock, @Param("billingCycleTypeId") Long billingCycleTypeId, @Param("routingId") String routingId, @Param("postpaid") Character postpaid, @Param("custType") String custType, @Param("custCode") String custCode);

  @Query(value = """
    SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS isAcct, CASE WHEN SUM(CASE WHEN STATE = 'A' THEN 1 ELSE 0 END) > 0 THEN 1 ELSE 0 END AS isAAcct, CASE WHEN SUM(CASE WHEN STATE = 'X' THEN 1 ELSE 0 END) > 0 THEN 1 ELSE 0 END AS isXAcct FROM ACCT WHERE BANK_ID = :bankId
    """, nativeQuery = true)
  AcctBankInfoProjection qryAcctInfoByBankId (@Param("bankId") Integer bankId);

  @Query("""
    SELECT a.state FROM Acct a WHERE a.bankId = :bankId
    """)
  List<Character> findStatesByBankId (@Param("bankId") Integer bankId);

  @Query(value = """
    SELECT A.ACCT_ID FROM ACCT A WHERE A.ACCT_NBR = :acctNbr
    """, nativeQuery = true)
  Long selectAcctIdByAcctNbr (@Param("acctNbr") String acctNbr);

  @Query(value = """
    SELECT A.ACCT_ID, A.STD_ADDR_ID, A.BILL_ADDRESS, A.CUST_ID, A.ACCT_NBR, A.BILLING_CYCLE_TYPE_ID, A.PAYMENT_TYPE, A.BANK_ID, A.BANK_ACCT_NBR, A.BANK_ACCT_NAME, A.CREATED_DATE, A.UPDATE_DATE, A.STATE, A.STATE_DATE, A.POSTPAID, A.ROUTING_ID, A.BILL_FORMAT_ID, A.SP_ID, A.DEFAULT_FLAG, A.PAYMENT_METHOD_ID, A.IS_LOCK, A.PARTY_TYPE, A.PARTY_CODE, A.BANK_ACCT_EXP_DATE, A.BANK_CARD_TYPE, A.NEED_UPLOAD, A.PAYMENT_COMMENTS, A.ACCT_NAME, B.CUST_NAME, A.BILL_FLAG FROM ACCT A JOIN CUST B ON A.CUST_ID = B.CUST_ID WHERE A.ACCT_ID = :acctId
    """, nativeQuery = true)
  Acct findAcctByAcctId (@Param("acctId") Long acctId);

  // versi lock (FOR UPDATE NOWAIT)
  @Query(value = """
    SELECT A.ACCT_ID, A.STD_ADDR_ID, A.BILL_ADDRESS, A.CUST_ID, A.ACCT_NBR, A.BILLING_CYCLE_TYPE_ID, A.PAYMENT_TYPE, A.BANK_ID, A.BANK_ACCT_NBR, A.BANK_ACCT_NAME, A.CREATED_DATE, A.UPDATE_DATE, A.STATE, A.STATE_DATE, A.POSTPAID, A.ROUTING_ID, A.BILL_FORMAT_ID, A.SP_ID, A.DEFAULT_FLAG, A.PAYMENT_METHOD_ID, A.IS_LOCK, A.PARTY_TYPE, A.PARTY_CODE, A.BANK_ACCT_EXP_DATE, A.BANK_CARD_TYPE, A.NEED_UPLOAD, A.PAYMENT_COMMENTS, A.ACCT_NAME, B.CUST_NAME, A.BILL_FLAG FROM ACCT A JOIN CUST B ON A.CUST_ID = B.CUST_ID WHERE A.ACCT_ID = :acctId FOR UPDATE NOWAIT
    """, nativeQuery = true)
  Acct findAcctByAcctIdForUpdate (@Param("acctId") Long acctId);

  // versi biasa (tanpa lock)
  @Query(value = """
    SELECT A.ACCT_ID, A.ACCT_NAME, A.STD_ADDR_ID, A.BILL_ADDRESS, A.CUST_ID, A.ACCT_NBR, A.BILLING_CYCLE_TYPE_ID, A.PAYMENT_TYPE, A.BANK_ID, A.BANK_ACCT_NBR, A.BANK_ACCT_NAME, A.CREATED_DATE, A.UPDATE_DATE, A.STATE, A.STATE_DATE, A.POSTPAID, A.ROUTING_ID, A.BILL_FORMAT_ID, A.SP_ID, A.DEFAULT_FLAG, A.PAYMENT_METHOD_ID, A.IS_LOCK, A.PARTY_TYPE, A.PARTY_CODE, A.BANK_ACCT_EXP_DATE, A.BANK_CARD_TYPE, A.NEED_UPLOAD, A.PAYMENT_COMMENTS, B.CUST_NAME, A.BILL_FLAG FROM ACCT A JOIN CUST B ON A.CUST_ID = B.CUST_ID WHERE A.ACCT_NBR = :acctNbr
    """, nativeQuery = true)
  Acct findAcctByAcctNbr (@Param("acctNbr") String acctNbr);

  // versi lock (FOR UPDATE NOWAIT)
  @Query(value = """
    SELECT A.ACCT_ID, A.ACCT_NAME, A.STD_ADDR_ID, A.BILL_ADDRESS, A.CUST_ID, A.ACCT_NBR, A.BILLING_CYCLE_TYPE_ID, A.PAYMENT_TYPE, A.BANK_ID, A.BANK_ACCT_NBR, A.BANK_ACCT_NAME, A.CREATED_DATE, A.UPDATE_DATE, A.STATE, A.STATE_DATE, A.POSTPAID, A.ROUTING_ID, A.BILL_FORMAT_ID, A.SP_ID, A.DEFAULT_FLAG, A.PAYMENT_METHOD_ID, A.IS_LOCK, A.PARTY_TYPE, A.PARTY_CODE, A.BANK_ACCT_EXP_DATE, A.BANK_CARD_TYPE, A.NEED_UPLOAD, A.PAYMENT_COMMENTS, B.CUST_NAME, A.BILL_FLAG FROM ACCT A JOIN CUST B ON A.CUST_ID = B.CUST_ID WHERE A.ACCT_NBR = :acctNbr FOR UPDATE NOWAIT
    """, nativeQuery = true)
  Acct findAcctByAcctNbrForUpdate (@Param("acctNbr") String acctNbr);

  @Query(value = """
        SELECT
            ACCT_ID               AS acctId,
            CUST_ID               AS custId,
            ACCT_NBR              AS acctNbr,
            BILLING_CYCLE_TYPE_ID AS billingCycleTypeId,
            PAYMENT_TYPE          AS paymentType,
            BANK_ID               AS bankId,
            BANK_ACCT_NBR         AS bankAcctNbr,
            BANK_ACCT_NAME        AS bankAcctName,
            CREATED_DATE          AS createdDate,
            UPDATE_DATE           AS updateDate,
            STATE                 AS state,
            STATE_DATE            AS stateDate,
            POSTPAID              AS postpaid,
            ROUTING_ID            AS routingId,
            BILL_FORMAT_ID        AS billFormatId,
            SP_ID                 AS spId
        FROM ACCT
        WHERE ACCT_ID = :acctId
        """, nativeQuery = true)
    Optional<SelectAcctDtoByAcctIdProjection> selectAcctDtoByAcctIdLockFlagFalse(@Param("acctId") Long acctId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = """
        SELECT
            ACCT_ID               AS acctId,
            CUST_ID               AS custId,
            ACCT_NBR              AS acctNbr,
            BILLING_CYCLE_TYPE_ID AS billingCycleTypeId,
            PAYMENT_TYPE          AS paymentType,
            BANK_ID               AS bankId,
            BANK_ACCT_NBR         AS bankAcctNbr,
            BANK_ACCT_NAME        AS bankAcctName,
            CREATED_DATE          AS createdDate,
            UPDATE_DATE           AS updateDate,
            STATE                 AS state,
            STATE_DATE            AS stateDate,
            POSTPAID              AS postpaid,
            ROUTING_ID            AS routingId,
            BILL_FORMAT_ID        AS billFormatId,
            SP_ID                 AS spId
        FROM ACCT
        WHERE ACCT_ID = :acctId
        FOR UPDATE NOWAIT
        """, nativeQuery = true)
    Optional<SelectAcctDtoByAcctIdProjection> selectAcctDtoByAcctIdLockFlagTrue(@Param("acctId") Long acctId);

}
