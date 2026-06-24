package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmStaffHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BfmStaffHisRepository extends JpaRepository<BfmStaffHis, Long> {
}
