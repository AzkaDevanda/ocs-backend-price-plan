package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.JobRoleHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRoleHisRepository extends JpaRepository<JobRoleHis, Long> {
}
