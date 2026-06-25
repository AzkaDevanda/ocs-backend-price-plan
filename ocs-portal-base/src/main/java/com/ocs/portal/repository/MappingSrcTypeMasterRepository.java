package com.ocs.portal.repository;

import com.ocs.portal.entity.MappingSrcTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MappingSrcTypeMasterRepository extends JpaRepository<MappingSrcTypeMaster, Character> {
}
