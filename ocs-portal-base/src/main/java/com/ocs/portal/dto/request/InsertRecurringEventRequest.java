package com.ocs.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertRecurringEventRequest {
  private Integer offerVerId;
  private Integer pricePlanId;
  private List<Character> recurringReType;
//  private List<String> recurringEventName;
}
