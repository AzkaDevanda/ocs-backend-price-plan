package com.ocs.portal.repository;

import com.ocs.portal.entity.MappingDesTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MappingDesTypeMasterRepository extends JpaRepository<MappingDesTypeMaster, Character> {
}
