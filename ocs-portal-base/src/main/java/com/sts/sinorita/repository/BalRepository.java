package com.sts.sinorita.repository;

import com.sts.sinorita.entity.Bal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalRepository extends JpaRepository<Bal, Long> {
}
