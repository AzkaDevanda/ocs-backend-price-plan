package com.ocs.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocs.portal.entity.DepositItemPayment;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositItemPaymentRepository extends JpaRepository<DepositItemPayment, Long> {

}
