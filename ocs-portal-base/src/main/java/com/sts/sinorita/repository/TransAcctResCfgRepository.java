package com.sts.sinorita.repository;

import com.sts.sinorita.entity.TransAcctResCfg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransAcctResCfgRepository extends JpaRepository<TransAcctResCfg, Long> {
    @Query(value = "SELECT * FROM trans_acct_res_cfg WHERE acct_res_id = :acctResId", nativeQuery = true)
    TransAcctResCfg selectTransAcctResCfgByAcctResId(@Param("acctResId") Long acctResId);
}
