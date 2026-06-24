package com.sts.sinorita.repository;

import com.sts.sinorita.entity.SortAttrMatch;
import com.sts.sinorita.entity.embeddable.SortAttrMatchId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SortAttrMatchRepository extends JpaRepository<SortAttrMatch, SortAttrMatchId> {

}
