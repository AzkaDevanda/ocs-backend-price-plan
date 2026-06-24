package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmServLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BfmServLogRepository extends JpaRepository<BfmServLog, Long> {
}
