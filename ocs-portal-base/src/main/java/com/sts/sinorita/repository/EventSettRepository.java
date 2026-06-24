package com.sts.sinorita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sts.sinorita.entity.EventSett;
import com.sts.sinorita.entity.embeddable.EventSettPK;

public interface EventSettRepository extends JpaRepository <EventSett, EventSettPK> {

}
