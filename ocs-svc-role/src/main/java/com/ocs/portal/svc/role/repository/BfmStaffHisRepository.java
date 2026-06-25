package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmStaffHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BfmStaffHisRepository extends JpaRepository<BfmStaffHis, Long> {
}
