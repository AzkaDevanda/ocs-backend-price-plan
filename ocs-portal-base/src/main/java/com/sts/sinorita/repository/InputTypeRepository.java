package com.sts.sinorita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sts.sinorita.entity.InputType;

@Repository
public interface InputTypeRepository extends JpaRepository<InputType, Character> {

}