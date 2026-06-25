package com.ocs.portal.svc.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.pot.BfmPortalChannel;

@Repository
public interface BfmPortalChannelRepository extends JpaRepository<BfmPortalChannel, Long> {

}
