package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmDir;
import com.sts.sinorita.svc.role.projection.DirProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BfmDirRepository extends JpaRepository<BfmDir, Long> {
	List<BfmDir> findByStateOrderByDirId(String state);
	List<BfmDir> findByStateAndParentId(String state, Long parentId);

	// ====> Native Query
	@Query(value = "SELECT A.DIR_ID AS dirId, A.DIR_NAME AS dirName, A.PARENT_ID AS parentId, A.ICON_URL AS iconUrl, A.STATE AS state, A.STATE_DATE AS stateDate FROM BFM_DIR A WHERE A.STATE = 'A' AND( (:parentId IS NULL AND A.PARENT_ID IS NULL) OR (:parentId IS NOT NULL AND A.PARENT_ID = :parentId))", nativeQuery = true)
	List<DirProjection> selectDirByParentId(@Param("parentId") Long parentId);

	@Query(value = "SELECT C.DIR_ID AS dirId, C.PARENT_ID AS parentId, C.DIR_NAME AS dirName, C.DIR_CODE as dirCode, C.ICON_URL as iconUrl FROM BFM_DIR C WHERE C.STATE = 'A' AND(:parentId IS NULL OR C.PARENT_ID = :parentId) ORDER BY C.DIR_ID", nativeQuery = true)
	List<DirProjection> findChildren(@Param("parentId") Long parentId);

	@Query(value = "SELECT * FROM BFM_DIR WHERE STATE = 'A' START WITH DIR_ID = :rootId CONNECT BY PRIOR DIR_ID = PARENT_ID", nativeQuery = true)
	List<BfmDir> findAllDescendantIds(Long rootId);

	@Query("select d from BfmDir d where d.state = 'A' and d.dirId in :ids order by d.dirId")
	List<BfmDir> findAllActiveByIdsOrdered(@Param("ids") List<Long> ids);
}
