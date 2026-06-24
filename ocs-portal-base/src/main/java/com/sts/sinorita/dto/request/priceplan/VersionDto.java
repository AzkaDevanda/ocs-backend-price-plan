package com.sts.sinorita.dto.request.priceplan;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VersionDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate effDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expDate;

    private String sourceFrom;

    private Integer oldPricePlanVerId;

    private String prefix;

    private String postfix;

    private String isCopyOfferAttr;
}
