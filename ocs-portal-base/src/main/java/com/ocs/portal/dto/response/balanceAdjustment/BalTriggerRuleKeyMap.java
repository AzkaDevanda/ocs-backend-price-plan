package com.ocs.portal.dto.response.balanceAdjustment;

import java.util.List;
import java.util.Map;

import com.ocs.portal.dto.BalTriggerParam;

import lombok.Data;

@Data
public class BalTriggerRuleKeyMap {
  private Map<String, List<Long[]>> trigger;
  private Map<String, List<BalTriggerParam>> thresholdMap;
}
  