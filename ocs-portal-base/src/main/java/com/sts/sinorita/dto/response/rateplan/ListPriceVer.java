package com.sts.sinorita.dto.response.rateplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListPriceVer {

    private Integer priceVerId;
    private LocalDate effDate;
    private LocalDate expDate;

}
