package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmUserPriv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BfmUserPrivsRepository extends JpaRepository<BfmUserPriv,Integer> {
}
