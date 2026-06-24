package com.sts.sinorita.svc.role.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sts.sinorita.entity.BfmPortalMenu;
import com.sts.sinorita.entity.embeddable.BfmPortalMenuId;
import com.sts.sinorita.svc.role.projection.QryPortalListByMenuIdProjection;

@Repository
public interface BfmPortalMenuRepository extends JpaRepository<BfmPortalMenu, BfmPortalMenuId> {
  List<BfmPortalMenu> findByIdPortalId(Long portalId);
  List<BfmPortalMenu> findByIdPortalIdAndState(Long portalId, String state);

  Optional<BfmPortalMenu> findByIdPortalIdAndIdPartyIdAndIdSeqAndState(Long portalId, Long partyId, Long seq, String state);

  List<BfmPortalMenu> findAllByParentId(long parentId);
  List<BfmPortalMenu> findAllByParentIdAndState(long parentId, String state);

  // =====> Native Query
  @Query(value = "SELECT BP.PORTAL_NAME AS portalName, BP.PORTAL_ID AS portalId, PM.SEQ AS seq, PM.PARTY_ID AS partyId, PM.PARENT_ID AS parentId, PM.TYPE AS type FROM BFM_PORTAL_MENU PM JOIN BFM_PORTAL BP ON PM.PORTAL_ID = BP.PORTAL_ID WHERE PM.PARTY_ID = :partyId AND BP.STATE = 'A' AND PM.STATE = 'A' AND (:type IS NULL OR PM.TYPE = :type) AND (:spId IS NULL OR NVL(BP.SP_ID, 0) = :spId) AND ( :isTypeMenu IS NULL OR :isTypeMenu <> 1 OR PM.PARENT_ID IS NULL)", nativeQuery = true)
  Page<QryPortalListByMenuIdProjection> qryPortalListByMenuId(Pageable pageable, @Param("partyId") Long partyId, @Param("type") String type, @Param("spId") Long spId, @Param("isTypeMenu") Integer isTypeMenu);

  @Modifying
  @Transactional
  @Query(value = "UPDATE POT.BFM_PORTAL_MENU SET SEQ = :newSeq WHERE PORTAL_ID = :portalId  AND PARTY_ID = :partyId  AND SEQ = :oldSeq AND STATE = :state", nativeQuery = true)
  int updateSeqNative(@Param("portalId") Long portalId, @Param("partyId") Long partyId, @Param("oldSeq") Long oldSeq, @Param("newSeq") Long newSeq, @Param("state") String state);
  
  @Query("SELECT COUNT(pm) FROM BfmPortalMenu pm WHERE pm.type = '1' AND pm.state = 'A' AND pm.parentId = :dirId AND pm.id.partyId IN :menuIds AND (:spId IS NULL OR COALESCE(pm.spId,0) = :spId)")
  Long countDirMenuUsedInPortal(@Param("dirId") Long dirId, @Param("menuIds") List<Long> menuIds, @Param("spId") Long spId);
}
