package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmMenu;
import com.ocs.portal.svc.role.projection.ComponentPrivProjection;
import com.ocs.portal.svc.role.projection.MenuProjection;
import com.ocs.portal.svc.role.projection.QryMenuListByDirIdProjection;
import com.ocs.portal.svc.role.projection.QryNoDirMenuListProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BfmMenuRepository extends JpaRepository<BfmMenu, Long> {
  @Query(value = "SELECT B.PRIV_ID AS privId, B.PRIV_NAME AS privName, B.URL AS url, B.COMMENTS AS comments FROM BFM_MENU A, BFM_MENU_DIR D, BFM_PRIV B INNER JOIN BFM_ROLE_PRIV C ON C.PRIV_ID = B.PRIV_ID AND(:roleId IS NULL OR C.ROLE_ID = :roleId) WHERE A.MENU_ID = B.PRIV_ID AND B.PRIV_TYPE = 'M' AND D.MENU_ID = B.PRIV_ID AND(:dirId IS NULL OR D.DIR_ID = :dirId) AND B.STATE = 'A' AND A.STATE = 'A' AND D.STATE = 'A'", nativeQuery = true)
  List<MenuProjection> selectMenuListByDirId(@Param("dirId") Long dirId, @Param("roleId") Long roleId);

  @Query(value = "SELECT B.PRIV_NAME AS privName, A.PRIV_ID AS privId, A.MENU_ID AS menuId, A.OBJ_ID AS objId, B.COMMENTS AS comments FROM BFM_COMPONENT_PRIV A, BFM_PRIV B LEFT JOIN BFM_ROLE_PRIV D ON D.PRIV_ID = B.PRIV_ID AND D.ROLE_ID = :roleId WHERE A.PRIV_ID = B.PRIV_ID AND B.PRIV_TYPE = 'C' AND B.STATE = 'A' AND( (:menuId IS NOT NULL AND A.MENU_ID = :menuId) OR (:menuId IS NULL AND A.MENU_ID IS NULL))", nativeQuery = true)
  List<ComponentPrivProjection> selectCompListByMenuIdRoleId(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

  @Query(
    value = "SELECT A.MENU_ID AS menuId, A.MENU_TYPE AS menuType, A.ICON_URL AS iconUrl, A.STATE AS state, A.STATE_DATE AS stateDate, B.PRIV_ID AS privId, B.PRIV_NAME AS privName, B.PRIV_CODE AS privCode, B.URL AS url, B.PRIV_EL AS privEl, B.PRIV_TYPE AS privType, B.PRIV_NAME AS menuName, B.URL AS menuUrl, B.COMMENTS AS comments, B.IS_HOLD AS isHold, B.SP_ID AS spId, B.APP_ID AS appId, B.STATE AS state, B.STATE_DATE AS stateDate, B.CDN_URL AS cdnUrl FROM BFM_MENU A JOIN BFM_PRIV B ON A.MENU_ID = B.PRIV_ID WHERE B.PRIV_TYPE = 'M' AND(:state IS NULL OR (A.STATE = :state AND B.STATE = :state)) AND (:appId IS NULL OR B.APP_ID = :appId) AND (:privName IS NULL OR UPPER(B.PRIV_NAME) LIKE '%' || UPPER(:privName) || '%') AND (:url IS NULL OR UPPER(B.URL) LIKE '%' || UPPER(:url) || '%')",
    countQuery = "SELECT COUNT(*) FROM BFM_MENU A JOIN BFM_PRIV B ON A.MENU_ID = B.PRIV_ID WHERE B.PRIV_TYPE = 'M' AND(:state IS NULL OR (A.STATE = :state AND B.STATE = :state)) AND (:appId IS NULL OR B.APP_ID = :appId) AND (:privName IS NULL OR UPPER(B.PRIV_NAME) LIKE '%' || UPPER(:privName) || '%') AND (:url IS NULL OR UPPER(B.URL) LIKE '%' || UPPER(:url) || '%')",
    nativeQuery = true)
  Page<QryNoDirMenuListProjection> qryAllMenuList(Pageable pageable, @Param("state") String state, @Param("appId") Long appId, @Param("privName") String privName, @Param("url") String url);

  @Query(
    value = "SELECT A.MENU_ID AS menuId, A.ICON_URL AS iconUrl, A.STATE AS state, A.STATE_DATE AS stateDate, B.PRIV_NAME AS menuName, B.URL AS menuUrl, B.COMMENTS AS comments, B.IS_HOLD AS isHold, B.SP_ID AS spId, B.APP_ID AS appId FROM BFM_MENU A JOIN BFM_PRIV B ON A.MENU_ID = B.PRIV_ID WHERE B.PRIV_TYPE = 'M' AND(:state IS NULL OR (A.STATE = :state AND B.STATE = :state)) AND (:appId IS NULL OR B.APP_ID = :appId) AND (:privName IS NULL OR UPPER(B.PRIV_NAME) LIKE '%' || UPPER(:privName) || '%') AND (:url IS NULL OR UPPER(B.URL) LIKE '%' || UPPER(:url) || '%') AND NOT EXISTS ( SELECT 1 FROM BFM_MENU_DIR C WHERE C.MENU_ID = A.MENU_ID AND (:state IS NULL OR C.STATE = :state))",
    countQuery = "SELECT COUNT(*) FROM BFM_MENU A JOIN BFM_PRIV B ON A.MENU_ID = B.PRIV_ID WHERE B.PRIV_TYPE = 'M' AND(:state IS NULL OR (A.STATE = :state AND B.STATE = :state)) AND (:appId IS NULL OR B.APP_ID = :appId) AND (:privName IS NULL OR UPPER(B.PRIV_NAME) LIKE '%' || UPPER(:privName) || '%') AND (:url IS NULL OR UPPER(B.URL) LIKE '%' || UPPER(:url) || '%') AND NOT EXISTS ( SELECT 1 FROM BFM_MENU_DIR C WHERE C.MENU_ID = A.MENU_ID AND (:state IS NULL OR C.STATE = :state))",
    nativeQuery = true)
  Page<QryNoDirMenuListProjection> qryNoDirMenuList(Pageable pageable, @Param("state") String state, @Param("appId") Long appId, @Param("privName") String privName, @Param("url") String url);

  @Query(value = "SELECT A.MENU_ID AS menuId, A.ICON_URL AS iconUrl, A.STATE AS state, A.STATE_DATE AS stateDate, B.PRIV_NAME AS menuName, B.URL AS menuUrl, B.COMMENTS AS comments, B.IS_HOLD AS isHold, B.SP_ID AS spId, B.APP_ID AS appId FROM BFM_MENU A JOIN BFM_PRIV B ON A.MENU_ID = B.PRIV_ID JOIN BFM_MENU_DIR C ON C.MENU_ID = A.MENU_ID WHERE 1=1 AND(:privType IS NULL OR B.PRIV_TYPE = :privType) AND (:dirId IS NULL OR C.DIR_ID = :dirId) AND (:state IS NULL OR A.STATE = :state) AND (:menuState IS NULL OR C.STATE = :menuState) AND (:privState IS NULL OR B.STATE = :privState) ORDER BY A.MENU_ID", nativeQuery = true)
  List<QryMenuListByDirIdProjection> qryMenuListByDirId(@Param("privType") String privType, @Param("dirId") Long dirId, @Param("state") String state, @Param("menuState") String menuState, @Param("privState") String privState);
}
