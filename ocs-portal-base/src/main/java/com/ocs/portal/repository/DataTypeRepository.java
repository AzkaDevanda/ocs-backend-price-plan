package com.ocs.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.DataType;

@Repository
public interface DataTypeRepository extends JpaRepository<DataType, Character> {
}
