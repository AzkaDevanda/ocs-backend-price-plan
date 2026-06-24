package com.sts.sinorita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.DataType;

@Repository
public interface DataTypeRepository extends JpaRepository<DataType, Character> {
}
