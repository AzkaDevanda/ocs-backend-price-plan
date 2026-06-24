package com.sts.sinorita.repository;

import com.sts.sinorita.entity.TriggerRule;
import com.sts.sinorita.repository.customRepository.TriggerRuleCustomRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AdvanceRuleTriggerRepository extends JpaRepository<TriggerRule, Integer>, TriggerRuleCustomRepository {

    @Query(value = "SELECT TRIGGER_RULE_ID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
    public BigDecimal getSequenceTriggerRule();

    @Modifying
    @Query(value="DELETE TRIGGER_RULE WHERE TRIGGER_ID=:triggerId AND SEQ=:seq", nativeQuery = true)
    public void deleteByTriggerIdAndSeq(@Param("triggerId") Integer triggerId, @Param("seq") Integer seq);

    @Query(value = "select t from TriggerRule t where t.triggerId =:triggerId and t.seq =:seq ")
    Optional<TriggerRule> findOneByTriggerIdAndSeq(@Param("triggerId") Integer triggerId, @Param("seq") Integer seq);


    @Modifying
    @Query(value = "UPDATE TRIGGER_RULE SET WORKFLOW_ID=:WORKFLOW_ID WHERE TRIGGER_ID=:TRIGGER_ID AND SEQ=:SEQ", nativeQuery = true)
    void updateTriggerRule(@Param("WORKFLOW_ID")Integer WORKFLOW_ID,@Param("TRIGGER_ID")Integer TRIGGER_ID, @Param("SEQ")Integer SEQ);

}
