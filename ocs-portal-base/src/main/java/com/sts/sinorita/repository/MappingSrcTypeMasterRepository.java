package com.sts.sinorita.repository;

import com.sts.sinorita.entity.MappingSrcTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MappingSrcTypeMasterRepository extends JpaRepository<MappingSrcTypeMaster, Character> {
}
