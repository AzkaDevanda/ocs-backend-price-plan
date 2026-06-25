package com.ocs.portal.repository;

import com.ocs.portal.entity.SortAttrMatch;
import com.ocs.portal.entity.embeddable.SortAttrMatchId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SortAttrMatchRepository extends JpaRepository<SortAttrMatch, SortAttrMatchId> {

}
