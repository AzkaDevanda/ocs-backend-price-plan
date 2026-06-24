package com.sts.sinorita.repository.customRepository;

import com.sts.sinorita.dto.response.trigger.TriggerRuleResponseDto;

import java.util.List;

public interface TriggerRuleCustomRepository {
    List<TriggerRuleResponseDto> findTriggerRules(Integer offerVerId, String order_field, String order_direction, Character state, Integer spId);
}