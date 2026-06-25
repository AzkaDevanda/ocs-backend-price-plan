package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmServLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BfmServLogRepository extends JpaRepository<BfmServLog, Long> {
}
