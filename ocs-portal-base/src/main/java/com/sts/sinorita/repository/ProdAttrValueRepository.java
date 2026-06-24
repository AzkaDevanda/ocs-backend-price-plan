package com.sts.sinorita.repository;

import com.sts.sinorita.entity.ProdAttrValue;
import com.sts.sinorita.entity.ProdAttrValueId;
import com.sts.sinorita.projection.balanceAdjustment.QryProdAttrValueByAttrCodeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdAttrValueRepository extends JpaRepository<ProdAttrValue, ProdAttrValueId> {
  @Query(value = """
          SELECT A.PROD_ID AS prodId, A.ATTR_ID AS attrId, A.VALUE AS value, A.EFF_DATE AS effDate, A.EXP_DATE AS expDate, A.UPDATE_DATE AS updateDate, A.NEED_UPLOAD AS needUpload, A.SP_ID AS spId, A.UPLOAD_TYPE AS uploadType FROM PROD_ATTR_VALUE A JOIN ATTR B ON A.ATTR_ID = B.ATTR_ID WHERE A.PROD_ID = :prodId AND B.ATTR_CODE = :attrCode
          """, nativeQuery = true)
  Optional<QryProdAttrValueByAttrCodeProjection> qryProdAttrValueByAttrCode (@Param("prodId") Long prodId, @Param("attrCode") String attrCode);
}
