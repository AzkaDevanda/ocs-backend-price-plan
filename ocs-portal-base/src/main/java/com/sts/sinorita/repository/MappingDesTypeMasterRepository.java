package com.sts.sinorita.repository;

import com.sts.sinorita.entity.MappingDesTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MappingDesTypeMasterRepository extends JpaRepository<MappingDesTypeMaster, Character> {
}
