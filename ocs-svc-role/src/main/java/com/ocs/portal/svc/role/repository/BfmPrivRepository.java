package com.ocs.portal.svc.role.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.pot.BfmPriv;
import com.ocs.portal.svc.role.projection.DirMenuProjection;

@Repository
public interface BfmPrivRepository extends JpaRepository<BfmPriv, Long> {
  @Query(value = "  SELECT B.MENU_ID AS menuId, B.DIR_ID AS dirId, A.PRIV_NAME AS privName, A.PRIV_CODE AS privCode, A.URL AS url, C.ICON_URL AS iconUrl, A.IS_HOLD AS isHold FROM BFM_PRIV A JOIN BFM_MENU_DIR B ON A.PRIV_ID = B.MENU_ID LEFT JOIN BFM_MENU C ON C.MENU_ID= B.MENU_ID WHERE A.STATE = 'A' AND(:dirId IS NULL OR B.DIR_ID = :dirId) ORDER BY B.MENU_ID", nativeQuery = true)
  List<DirMenuProjection> findMenuByDirId(@Param("dirId") Long dirId);

  @Query(
    value = "SELECT DISTINCT * FROM BFM_PRIV WHERE PRIV_ID IN( SELECT MENU_ID FROM BFM_MENU_DIR WHERE STATE = 'A') AND STATE = 'A' AND PRIV_TYPE = 'M' AND (:url IS NULL OR UPPER(URL) LIKE '%'||UPPER(:url)||'%') AND (:privName IS NULL OR UPPER(PRIV_NAME) LIKE '%'||UPPER(:privName)||'%')", 
    countQuery = "SELECT COUNT(*) FROM( SELECT DISTINCT PRIV_ID FROM BFM_PRIV WHERE PRIV_ID IN (SELECT MENU_ID FROM BFM_MENU_DIR WHERE STATE = 'A') AND STATE = 'A' AND PRIV_TYPE = 'M' AND (:url IS NULL OR UPPER(URL) LIKE '%' || UPPER(:url) || '%') AND (:privName IS NULL OR UPPER(PRIV_NAME) LIKE '%' || UPPER(:privName) || '%'))",
    nativeQuery = true)
  Page<BfmPriv> qryMenuList(Pageable pageable, @Param("url") String url, @Param("privName") String privName);

  @Query(value = "SELECT B.MENU_ID AS menuId, B.DIR_ID AS dirId, A.PRIV_NAME AS privName, A.URL AS url, A.IS_HOLD AS isHold, A.IS_AUTHORIZED AS isAuthorized FROM BFM_PRIV A JOIN BFM_MENU_DIR B ON A.PRIV_ID = B.MENU_ID WHERE A.STATE = 'A' AND(:dirIds IS NULL OR :dirIds IS EMPTY OR B.DIR_ID IN (:dirIds)) ORDER BY A.PRIV_ID", nativeQuery = true)
  List<DirMenuProjection> selectDirMenuByParentIdCascade(@Param("dirIds") List<Long> dirIds);
}
