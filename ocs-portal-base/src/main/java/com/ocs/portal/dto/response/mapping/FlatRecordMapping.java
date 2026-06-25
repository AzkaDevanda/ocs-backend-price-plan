package com.ocs.portal.dto.response.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public interface FlatRecordMapping {
    Integer getMappingId();
    String getMappingName();
    Integer getPriceVerId();
    LocalDateTime getEffDate();
    LocalDateTime getExpDate();
    Integer getPriceId();
    String getPriceName();
    String getAcctItemTypeName();
    String getValueString();
    Integer getRum();
    String getReAttrName();
}

