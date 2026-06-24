package com.sts.sinorita.svc.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.pot.BfmPortal;

@Repository
public interface BfmPortalRepository extends JpaRepository<BfmPortal, Long> {
}
