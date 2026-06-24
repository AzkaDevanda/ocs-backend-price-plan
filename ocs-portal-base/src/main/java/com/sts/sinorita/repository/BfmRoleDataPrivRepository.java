package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BfmRoleDataPriv;
import com.sts.sinorita.entity.BfmRoleDataPrivId;
import com.sts.sinorita.projection.balanceAdjustment.QryRoleDataPrivValueUnderStaffJobProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BfmRoleDataPrivRepository extends JpaRepository<BfmRoleDataPriv, BfmRoleDataPrivId> {
  @Query(value = """
          SELECT DISTINCT A.DATA_PRIV_ID AS dataPrivId, A.ROLE_ID AS roleId, A.PRIV_VALUE AS privValue, A.SP_ID AS spId, A.OWNED_TYPE AS ownedType, B.NAME AS roleName, B.ROLE_CODE AS roleCode FROM BFM_ROLE_DATA_PRIV A JOIN BFM_ROLE B ON A.ROLE_ID = B.ID JOIN BFM_JOB_ROLE C ON A.ROLE_ID = C.ROLE_ID JOIN BFM_STAFF_JOB D ON C.JOB_ID = D.JOB_ID WHERE D.STAFF_JOB_ID = :staffJobId AND A.DATA_PRIV_ID = :dataPrivId AND (:spId IS NULL OR NVL(A.SP_ID, 0) = :spId OR NVL(B.SP_ID, 0) = :spId OR NVL(C.SP_ID, 0) = :spId OR NVL(D.SP_ID, 0) = :spId) AND (:appId IS NULL OR B.APP_ID = :appId)
          """, nativeQuery = true)
  List<QryRoleDataPrivValueUnderStaffJobProjection> qryRoleDataPrivValueUnderStaffJob (@Param("staffJobId") String staffJobId, @Param("dataPrivId") Long dataPrivId);
}
