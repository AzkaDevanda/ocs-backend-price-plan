package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Subs;
import com.sts.sinorita.projection.balanceAdjustment.SelectSubsProjection;
import com.sts.sinorita.projection.subs.SelectSubsByAcctIdProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubsRepository extends JpaRepository<Subs, Long> {
  @Query(value = """
      SELECT SUBS_ID, PREFIX, ACC_NBR, CUST_ID, USER_ID, ACCT_ID, AGENT_ID, AREA_ID, UPDATE_DATE, PPS_CREDIT_LIMIT, ROUTING_ID, DEF_LANG_ID, SP_ID, PPS_PWD FROM SUBS WHERE SUBS_ID = :subsId
      """, nativeQuery = true)
  Subs selectSubsBySubsId(@Param("subsId") Long subsId);

  @Query(value = """
      SELECT SUBS_ID, PREFIX, ACC_NBR, CUST_ID, USER_ID, ACCT_ID, AGENT_ID, AREA_ID, UPDATE_DATE, PPS_CREDIT_LIMIT, ROUTING_ID, DEF_LANG_ID, SP_ID, PPS_PWD FROM SUBS WHERE ACC_NBR = :accNbr AND (:prefix IS NULL OR PREFIX = :prefix) AND (:spId IS NULL OR NVL(SP_ID, 0) = :spId) ORDER BY UPDATE_DATE DESC FETCH FIRST 1 ROWS ONLY
      """, nativeQuery = true)
  Subs selectSubsByAccNbrAndSpId(@Param("prefix") String prefix, @Param("accNbr") String accNbr,
      @Param("spId") Long spId);

  @Query(value = """
      SELECT A.SUBS_ID, A.PREFIX, A.ACC_NBR, A.CUST_ID, A.USER_ID, A.ACCT_ID, A.AGENT_ID, A.AREA_ID, A.UPDATE_DATE, A.ROUTING_ID, A.DEF_LANG_ID, A.PPS_PWD, A.SP_ID, B.CREATED_DATE AS PROD_CREATED_DATE, B.COMPLETED_DATE AS PROD_COMPLETED_DATE, B.PROD_STATE AS PROD_STATE, B.STATE_DATE AS PROD_STATE_DATE, B.UPDATE_DATE AS PROD_UPDATE_DATE, B.PROD_STATE_DATE AS PROD_PROD_STATE_DATE, B.BLOCK_REASON AS PROD_BLOCK_REASON, B.PROD_EXP_DATE AS PROD_PROD_EXP_DATE, B.INDEP_PROD_ID AS PROD_INDEP_PROD_ID, B.OFFER_ID AS PROD_OFFER_ID FROM STS.SUBS A JOIN STS.PROD B ON A.SUBS_ID = B.PROD_ID WHERE B.STATE = 'A' AND A.ACCT_ID = :acctId ORDER BY B.COMPLETED_DATE DESC
      """, nativeQuery = true)
  List<Object[]> findSubsWithProdByAcctId(@Param("acctId") Long acctId);

  @Query(value = """
      SELECT A.PRICE_PLAN_ID, A.EFF_DATE, A.EXP_DATE FROM SUBS_UPP_INST A JOIN SUBS_PRICE_PLAN B ON A.PRICE_PLAN_ID = B.PRICE_PLAN_ID JOIN OFFER C ON A.PRICE_PLAN_ID = C.OFFER_ID JOIN PRICE_PLAN D ON A.PRICE_PLAN_ID = D.PRICE_PLAN_ID WHERE A.EFF_DATE <= SYSDATE AND (A.EXP_DATE IS NULL OR A.EXP_DATE > SYSDATE) AND B.PRICE_PLAN_TYPE = '2' AND A.SUBS_ID = :subsId
      """, nativeQuery = true)
  List<Object[]> selectSubsDefaultPricePlan(@Param("subsId") Long subsId);

  @Query(value = "SELECT SUBS_ID, PREFIX, ACC_NBR, CUST_ID, USER_ID, AGENT_ID, ORG_ID, AREA_ID, UPDATE_DATE, ROUTING_ID, DEF_LANG_ID, PPS_PWD, COMMENTS, SP_ID, ACCT_ID, PPS_CREDIT_LIMIT, NEED_UPLOAD, CREATE_STAFF_ID, SECOND_NBR FROM SUBS WHERE SUBS_ID = :subsId", nativeQuery = true)
  Optional<SelectSubsProjection> selectSubs(@Param("subsId") Long subsId);

  @Query(value = "SELECT * FROM ( SELECT A.SUBS_ID AS subsId, A.PREFIX AS prefix, A.ACC_NBR AS accNbr, A.CUST_ID AS custId, A.USER_ID AS userId, A.AGENT_ID AS agentId, A.ORG_ID AS orgId, A.AREA_ID AS areaId, A.UPDATE_DATE AS updateDate, A.ROUTING_ID AS routingId, A.DEF_LANG_ID AS defLangId, A.PPS_PWD AS ppsPwd, A.COMMENTS AS comments, A.SP_ID AS spId, A.ACCT_ID AS acctId, A.PPS_CREDIT_LIMIT AS ppsCreditLimit, A.NEED_UPLOAD AS needUpload FROM SUBS A WHERE A.PREFIX = :prefix AND A.ACC_NBR = :accNbr ORDER BY A.SUBS_ID DESC ) WHERE ROWNUM = 1", nativeQuery = true)
  Optional<SelectSubsProjection> selectLastSubs(@Param("prefix") String prefix, @Param("accNbr") String accNbr);

  // @Query(value = """
  // SELECT
  // SUBS_ID AS subsId,
  // PREFIX AS prefix,
  // ACC_NBR AS accNbr,
  // CUST_ID AS custId,
  // USER_ID AS userId,
  // ACCT_ID AS acctId,
  // AGENT_ID AS agentId,
  // AREA_ID AS areaId,
  // UPDATE_DATE AS updateDate,
  // PPS_CREDIT_LIMIT AS ppsCreditLimit,
  // ROUTING_ID AS routingId,
  // DEF_LANG_ID AS defLangId,
  // SP_ID AS spId,
  // PPS_PWD AS ppsPwd
  // FROM SUBS
  // WHERE SUBS_ID = :subsId
  // """, nativeQuery = true)
  // Optional<SubsProjection> findSubsProjectionBySubsId(@Param("subsId") Long
  // subsId);

  @Query(value = "SELECT A.SUBS_ID, A.PREFIX, A.ACC_NBR, A.CUST_ID, A.USER_ID, A.ACCT_ID, A.AGENT_ID, A.AREA_ID, A.UPDATE_DATE, A.ROUTING_ID, A.DEF_LANG_ID, A.PPS_PWD, A.SP_ID, B.CREATED_DATE, B.COMPLETED_DATE, B.PROD_STATE, B.STATE_DATE, B.UPDATE_DATE AS PROD_UPDATE_DATE, B.PROD_STATE_DATE, B.BLOCK_REASON, B.PROD_EXP_DATE, B.INDEP_PROD_ID FROM SUBS A JOIN PROD B ON A.SUBS_ID = B.PROD_ID WHERE B.PROD_STATE NOT IN ('B', 'F') AND B.STATE = 'A' AND A.ACCT_ID = :acctId ORDER BY B.COMPLETED_DATE DESC", nativeQuery = true)
  List<SelectSubsByAcctIdProjection> selectSubsByAcctId(
      @Param("acctId") Long acctId);

}
