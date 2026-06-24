package com.sts.sinorita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.AsynCall;

@Repository
public interface AsynCallRepository extends JpaRepository<AsynCall, Long> {

}
