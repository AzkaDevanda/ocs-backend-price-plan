package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmUserRoleHis;
import com.ocs.portal.svc.role.repository.custom.UserRoleHisCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleHisRepository extends JpaRepository<BfmUserRoleHis, Integer>, UserRoleHisCustomRepository {
}
