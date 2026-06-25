package com.ocs.portal.svc.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.pot.BfmPortal;

@Repository
public interface BfmPortalRepository extends JpaRepository<BfmPortal, Long> {
}
