package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.JobRoleHis;
import com.sts.sinorita.svc.role.dto.request.job.JobRoleHisDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRoleHisRepository extends JpaRepository<JobRoleHis, Long> {
}
