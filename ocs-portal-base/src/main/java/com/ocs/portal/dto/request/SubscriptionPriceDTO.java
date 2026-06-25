package com.ocs.portal.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SubscriptionPriceDTO {

    // Header section
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private String priceName;
    private BigDecimal price;
    private int calculateUnit;
    private String calculateUnitType; // contoh: "Occurrence"
    private String remarks;

    private String resultAccountItemType; // contoh: "Main balance"
    private String discountAccountItemType;
    private BigDecimal creditLimit;

    // Expression Price tab - Script Template
    private String scriptTemplate;
}
