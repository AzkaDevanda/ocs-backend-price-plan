package com.sts.sinorita.repository;

import com.sts.sinorita.entity.BfmOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BfmOrgRepository extends JpaRepository<BfmOrg, Long> {


}
