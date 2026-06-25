package com.ocs.portal.dto.response.priceVer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceVersionResponse {
    private Long priceVerId;
    private LocalDate effDate;
    private LocalDate expDate;
    private String date;
    private List<PriceItemResponse> price = new ArrayList<>();
}
