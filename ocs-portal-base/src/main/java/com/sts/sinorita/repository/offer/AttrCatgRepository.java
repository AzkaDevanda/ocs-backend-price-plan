package com.sts.sinorita.repository.offer;

import com.sts.sinorita.entity.AttrCatg;
import com.sts.sinorita.projection.offer.commonoffer.AttrCatgProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AttrCatgRepository extends JpaRepository<AttrCatg, Integer> {
  @Query("SELECT " +
      "a.attrCatg AS attrCatg, " +
      "a.attrCatgName AS attrCatgName, " +
      "a.comments AS comments " +
      "FROM AttrCatg a " +
      "ORDER BY a.attrCatgName")
  List<AttrCatgProjection> findAllAttrCatg();
}
