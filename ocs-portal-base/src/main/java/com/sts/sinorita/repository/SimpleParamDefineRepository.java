package com.sts.sinorita.repository;

import com.sts.sinorita.entity.SimpleParamDefine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleParamDefineRepository extends JpaRepository<SimpleParamDefine, Integer> {

}
