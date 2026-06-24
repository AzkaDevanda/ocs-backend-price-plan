package com.sts.sinorita.dto.response.re;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUsageEventByOfferIdResponse {
    private Integer reId;
    private Integer offerVerId;
    private String reName;
}
