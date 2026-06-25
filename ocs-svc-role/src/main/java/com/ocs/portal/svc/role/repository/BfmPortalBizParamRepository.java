package com.ocs.portal.svc.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.pot.BfmPortalBizParam;

@Repository
public interface BfmPortalBizParamRepository extends JpaRepository<BfmPortalBizParam, Long> {

}
