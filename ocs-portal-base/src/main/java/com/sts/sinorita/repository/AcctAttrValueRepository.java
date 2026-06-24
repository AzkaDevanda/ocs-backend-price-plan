package com.sts.sinorita.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.AcctAttrValue;
import com.sts.sinorita.entity.embeddable.AcctAttrValueId;
import com.sts.sinorita.projection.acct.SelectAcctAttrValueProjection;

@Repository
public interface AcctAttrValueRepository extends JpaRepository<AcctAttrValue, AcctAttrValueId> {

  @Query(value = """
      SELECT
          A.ACCT_ID      AS acctId,
          A.ATTR_ID      AS attrId,
          A.ATTR_VALUE   AS attrValue,
          A.EFF_DATE     AS effDate,
          A.EXP_DATE     AS expDate,
          A.UPDATE_DATE  AS updateDate,
          A.NEED_UPLOAD  AS needUpload,
          A.SP_ID        AS spId
      FROM ACCT_ATTR_VALUE A
      WHERE A.ACCT_ID = :acctId
        AND A.ATTR_ID = :attrId
      """, nativeQuery = true)
  Optional<SelectAcctAttrValueProjection> selectAcctAttrValue(
      @Param("acctId") Long acctId,
      @Param("attrId") Long attrId);

}
