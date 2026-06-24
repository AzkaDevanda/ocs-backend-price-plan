package com.sts.sinorita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sts.sinorita.entity.DepositItemPayment;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositItemPaymentRepository extends JpaRepository<DepositItemPayment, Long> {

}
