package com.sts.sinorita.svc.role.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sts.sinorita.entity.embeddable.BfmMenuDirId;
import com.sts.sinorita.entity.pot.BfmMenuDir;

@Repository
public interface BfmMenuDirRepository  extends JpaRepository<BfmMenuDir, BfmMenuDirId> {
  @Modifying
  void deleteByIdDirIdIn(List<Long> dirIds);

  // ====> Native Query
  @Modifying
  @Transactional
  @Query("DELETE FROM BfmMenuDir d WHERE d.id.menuId IN :menuIds AND d.id.dirId = :dirId")
  void deleteDirMenu(@Param("dirId") Long dirId, @Param("menuIds") List<Long> menuIds);

  @Query("SELECT COUNT(d) FROM BfmMenuDir d WHERE d.id.dirId = :dirId AND d.id.menuId IN :menuIds")
  int checkExist(@Param("dirId") Long dirId, @Param("menuIds") List<Long> menuIds);
}
