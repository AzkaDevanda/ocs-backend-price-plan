package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmUserPriv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BfmUserPrivsRepository extends JpaRepository<BfmUserPriv,Integer> {
}
