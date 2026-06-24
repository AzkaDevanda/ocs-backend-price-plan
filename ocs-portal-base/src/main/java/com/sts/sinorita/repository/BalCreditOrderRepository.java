package com.sts.sinorita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sts.sinorita.entity.BalCreditOrder;

@Repository
public interface BalCreditOrderRepository extends JpaRepository<BalCreditOrder, Long> {
}
