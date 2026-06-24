package com.sts.sinorita.svc.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.pot.BfmPortalChannel;

@Repository
public interface BfmPortalChannelRepository extends JpaRepository<BfmPortalChannel, Long> {

}
