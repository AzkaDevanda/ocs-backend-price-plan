package com.ocs.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocs.portal.entity.EventSett;
import com.ocs.portal.entity.embeddable.EventSettPK;

public interface EventSettRepository extends JpaRepository <EventSett, EventSettPK> {

}
