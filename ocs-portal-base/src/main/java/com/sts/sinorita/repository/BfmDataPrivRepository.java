package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BfmDataPriv;
import com.sts.sinorita.projection.balanceAdjustment.QryDataPrivByDataPrivCodeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BfmDataPrivRepository extends JpaRepository<BfmDataPriv, Long> {
  @Query(value = """
           SELECT DATA_PRIV_ID AS dataPrivId, DATA_PRIV_NAME AS dataPrivName, COMMENTS AS comments, DATA_TYPE AS dataType, DATA_SRC AS dataSrc, SP_ID AS spId, DATA_PRIV_CODE AS dataPrivCode FROM BFM_DATA_PRIV WHERE DATA_PRIV_CODE = :dataPrivCode
          """, nativeQuery = true)
  Optional<QryDataPrivByDataPrivCodeProjection> qryDataPrivByDataPrivCode (@Param("dataPrivCode") String dataPrivCode);
}
