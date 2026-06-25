package com.ocs.portal.repository;

import com.ocs.portal.entity.SimpleParamDefine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleParamDefineRepository extends JpaRepository<SimpleParamDefine, Integer> {

}
