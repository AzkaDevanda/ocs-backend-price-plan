package com.sts.sinorita.repository;

import com.sts.sinorita.entity.RecurringReType;
import com.sts.sinorita.projection.pricePlan.QryRecurringReTypeListByVerIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecurringReTypeRepository extends JpaRepository<RecurringReType, Character> {

  @Query(value = """
    SELECT A.RECURRING_RE_TYPE recurringReType, A.RECURRING_RE_TYPE_NAME recurringReTypeName FROM RECURRING_RE_TYPE A WHERE 1 = 1 AND A.RECURRING_RE_TYPE_NAME NOT IN ( SELECT SUBSTR(C.RE_NAME, 0, LENGTH(A.RECURRING_RE_TYPE_NAME)) FROM RE_PRICE_PLAN B, RE C WHERE 1 = 1 AND B.RE_ID = C.RE_ID AND C.RE_TYPE = '9' AND B.OFFER_VER_ID = :offerVerId) AND (UPPER(A.RECURRING_RE_TYPE_NAME) LIKE UPPER('%' || :reName || '%') OR A.RECURRING_RE_TYPE_NAME IS NULL) ORDER BY A.RECURRING_RE_TYPE_NAME
    """, nativeQuery = true)
  List<QryRecurringReTypeListByVerIdProjection> qryRecurringReTypeListByVerId (@Param("offerVerId") Integer offerVerId, @Param("reName") String reName);

  @Query(
    value = """
          SELECT RECURRING_RE_TYPE_NAME
          FROM RECURRING_RE_TYPE
          WHERE RECURRING_RE_TYPE = :recurringReType
      """,
    nativeQuery = true
  )
  String findNameByRecurringReType (
    @Param("recurringReType") Character recurringReType
  );

  @Modifying
  @Transactional
  @Query(value = """
    		UPDATE RE R
    		SET R.RE_NAME = (
    				SELECT SUBSTR(RRT.RECURRING_RE_TYPE_NAME || ' ( ' || :newPricePlanName, 0, 250) || ' )'
    				FROM RECURRING_RE_TYPE RRT, RE_PP_RECURRING RPP
    				WHERE RRT.RECURRING_RE_TYPE = RPP.RECURRING_RE_TYPE
    						AND RPP.PRICE_PLAN_ID = :pricePlanId
    						AND RPP.RE_ID = R.RE_ID
    		)
    		WHERE RE_ID = (
    				SELECT RPP.RE_ID
    				FROM RECURRING_RE_TYPE RRT, RE_PP_RECURRING RPP
    				WHERE RRT.RECURRING_RE_TYPE = RPP.RECURRING_RE_TYPE
    						AND RPP.PRICE_PLAN_ID = :pricePlanId
    						AND RPP.RE_ID = R.RE_ID
    		)
    """, nativeQuery = true)
  void updateReName (@Param("newPricePlanName") String newPricePlanName, @Param("pricePlanId") Integer pricePlanId);
}
