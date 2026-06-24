package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmUserRoleHis;
import com.sts.sinorita.svc.role.dto.request.roles.UserRoleHisDto;
import com.sts.sinorita.svc.role.repository.custom.UserRoleHisCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleHisRepository extends JpaRepository<BfmUserRoleHis, Integer>, UserRoleHisCustomRepository {
}
