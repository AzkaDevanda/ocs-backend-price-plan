package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.SecurityRuleUm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BfmSecurityRuleUmRepository extends JpaRepository<SecurityRuleUm,Integer> {

    @Query(value = "select s from SecurityRuleUm s where s.ruleId = :ruleId")
    public Optional<SecurityRuleUm> selectSecurityRuleByRuleId(@Param("ruleId")Long ruleId);
}
