package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Prod;
import com.sts.sinorita.projection.balanceAdjustment.FindProdBySubsIdProjection;
import com.sts.sinorita.projection.balanceAdjustment.QryProdByIdProjection;
import com.sts.sinorita.projection.balanceAdjustment.SelectProdBySubsIdProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdRepository extends JpaRepository<Prod, Long> {
  @Query(value = "SELECT T1.PROD_ID, T1.OFFER_ID, T1.COMPLETED_DATE, T1.PROD_STATE, T1.SUBS_PLAN_ID, T1.PACKAGE_FLAG, T1.PARENT_PROD_ID, T1.INDEP_PROD_ID, T1.PROD_STATE_DATE, T1.UPDATE_DATE, T1.CREATED_DATE, T1.STATE, T1.STATE_DATE, T1.BLOCK_REASON, T1.PROD_EXP_DATE, T1.NEED_UPLOAD, T1.AGREEMENT_EXP_DATE, T1.SP_ID, T2.OFFER_CODE, T1.ACTIVE_DATE, T3.OFFER_CODE AS SUBS_PLAN_CODE FROM PROD T1 JOIN OFFER T2 ON T1.OFFER_ID = T2.OFFER_ID JOIN OFFER T3 ON T1.SUBS_PLAN_ID = T3.OFFER_ID WHERE T1.PROD_ID = :subsId", nativeQuery = true)
  Prod selectProdBySubsId(@Param("subsId") Long subsId);

  @Query(value = "SELECT PROD_ID AS prodId, OFFER_ID AS offerId, COMPLETED_DATE AS completedDate, PROD_STATE AS prodState, SUBS_PLAN_ID AS subsPlanId, PACKAGE_FLAG AS packageFlag, PARENT_PROD_ID AS parentProdId, INDEP_PROD_ID AS indepProdId, PROD_STATE_DATE AS prodStateDate, UPDATE_DATE AS updateDate, CREATED_DATE AS createdDate, STATE AS state, STATE_DATE AS stateDate, BLOCK_REASON AS blockReason, PROD_EXP_DATE AS prodExpDate, NEED_UPLOAD AS needUpload, AGREEMENT_EXP_DATE AS agreementExpDate, SP_ID AS spId, AGREEMENT_EFF_DATE AS agreementEffDate, AGREEMENT_LIMIT AS agreementLimit, AGREEMENT_TIME_UNIT AS agreementTimeUnit, ROUTING_ID AS routingId, ACTIVE_DATE AS activeDate, UPLOAD_TYPE AS uploadType FROM PROD WHERE PROD_ID = :prodId", nativeQuery = true)
  Optional<QryProdByIdProjection> qryProdById(@Param("prodId") Long prodId);

  @Query(value = "SELECT T1.PROD_ID AS prodId, T1.OFFER_ID AS offerId, T1.COMPLETED_DATE AS completedDate, T1.PROD_STATE AS prodState, T1.SUBS_PLAN_ID AS subsPlanId, T1.PACKAGE_FLAG AS packageFlag, T1.PARENT_PROD_ID AS parentProdId, T1.INDEP_PROD_ID AS indepProdId, T1.PROD_STATE_DATE AS prodStateDate, T1.UPDATE_DATE AS updateDate, T1.CREATED_DATE AS createdDate, T1.STATE AS state, T1.STATE_DATE AS stateDate, T1.BLOCK_REASON AS blockReason, T1.PROD_EXP_DATE AS prodExpDate, T1.NEED_UPLOAD AS needUpload, T1.AGREEMENT_EXP_DATE AS agreementExpDate, T1.SP_ID AS spId, T2.OFFER_CODE AS offerCode, T1.ACTIVE_DATE AS activeDate, T3.OFFER_CODE AS subsPlanCode FROM PROD T1 JOIN OFFER T2 ON T1.OFFER_ID = T2.OFFER_ID JOIN OFFER T3 ON T1.SUBS_PLAN_ID = T3.OFFER_ID WHERE T1.PROD_ID = :subsId", nativeQuery = true)
  Optional<FindProdBySubsIdProjection> findProdBySubsId(@Param("subsId") Long subsId);

  @Query(value = "SELECT T1.PROD_ID, T1.OFFER_ID, T1.COMPLETED_DATE, T1.PROD_STATE, T1.SUBS_PLAN_ID, T1.PACKAGE_FLAG, T1.PARENT_PROD_ID, T1.INDEP_PROD_ID, T1.PROD_STATE_DATE, T1.UPDATE_DATE, T1.CREATED_DATE, T1.STATE, T1.STATE_DATE, T1.BLOCK_REASON, T1.PROD_EXP_DATE, T1.NEED_UPLOAD, T1.AGREEMENT_EXP_DATE, T1.SP_ID, T2.OFFER_CODE, T1.ACTIVE_DATE, T3.OFFER_CODE AS SUBS_PLAN_CODE FROM PROD T1 JOIN OFFER T2 ON T1.OFFER_ID = T2.OFFER_ID JOIN OFFER T3 ON T1.SUBS_PLAN_ID = T3.OFFER_ID WHERE T1.PROD_ID = :subsId", nativeQuery = true)
  Optional<SelectProdBySubsIdProjection> selectProdDtoBySubsId(@Param("subsId") Long subsId);   

}