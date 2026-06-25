package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmRolePrivHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePrivHisRepository extends JpaRepository<BfmRolePrivHis, Long> {


}
