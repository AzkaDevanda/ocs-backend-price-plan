package com.ocs.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocs.portal.entity.InputType;

@Repository
public interface InputTypeRepository extends JpaRepository<InputType, Character> {

}