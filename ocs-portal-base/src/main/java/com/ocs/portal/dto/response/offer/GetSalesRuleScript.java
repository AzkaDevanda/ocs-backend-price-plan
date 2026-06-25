package com.ocs.portal.dto.response.offer;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({ "offerId", "salesRuleScript" })
public interface GetSalesRuleScript {
    Integer getOfferId();
    String getSalesRuleScript();
}
