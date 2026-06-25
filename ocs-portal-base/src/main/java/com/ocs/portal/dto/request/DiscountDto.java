package com.ocs.portal.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDto {
    private String discountType; // Expression / Tabular
    private String discountName;
    private boolean promotion; // true jika Yes, false jika No
    private String resultAccountItem; // contoh: Bonus Off-net Flash Monthly
    private String remarks;
    private String scriptTemplate;

}
