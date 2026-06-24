package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmSecurityRule;
import com.sts.sinorita.svc.role.projection.SecurityRuleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BfmSecurityRuleRepository extends JpaRepository<BfmSecurityRule, Long> {

    @Query(value = """
        SELECT
            LEVEL_ID AS levelId,
            LEVEL_CODE AS levelCode,
            USER_CODE_COMPOSITION AS userCodeComposition,
            USER_CODE_MIN_LENGTH AS userCodeMinLength,
            USER_CODE_MAX_LENGTH AS userCodeMaxLength,
            PWD_MIN_LENGTH AS pwdMinLength,
            PWD_COMPOSITION AS pwdComposition,
            PWD_HIS_NUM AS pwdHisNum,
            PWD_EXC_DAYS AS pwdExcDays,
            PWD_RULES AS pwdRules,
            PWD_KEYBOARD_NUM AS pwdKeyboardNum,
            PWD_DICT_NUM AS pwdDictNum,
            USER_EXC_DAYS AS userExcDays
        FROM BFM_SECURITY_RULE
        WHERE LEVEL_CODE = :levelCode
        """, nativeQuery = true)
    Optional<SecurityRuleProjection> querySecurityRule(@Param("levelCode") String levelCode);

}
